package it.xpug.todolists.main;

import static java.lang.Integer.*;
import static java.util.Collections.*;
import static javax.servlet.http.HttpServletResponse.*;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import javax.servlet.http.*;

import org.json.*;

public class TodoListsResource extends Resource {
	
	private List<String> todoLists;
	private HttpServletResponse response;
	private HttpServletRequest request;
	private Matcher matcher;

	public TodoListsResource(HttpServletRequest request, HttpServletResponse response, List<String> todoLists) {
		this.request = request;
		this.response = response;
		this.todoLists = todoLists;
    }

	@Override
	public void service() throws IOException {
		if (isPost() && parameterIsMissing("name")) {
			respondWith(SC_BAD_REQUEST, "Parameter 'name' is required");
			return;
		}
		if (isPost()) {
			todoLists.add(request.getParameter("name"));
			response.sendRedirect(request.getRequestURI());
			return;
		}
		
		if (uriMatches("/todolists/(\\d+)")) {
			respondWithTodoList(valueOf(getUriParameter(1)));
			return;
		}
		
		respondWithAllTodoLists();
	}

	private void respondWithAllTodoLists() throws IOException {
	    JSONObject json = new JSONObject();
		json.put("myLists", emptyList());
		for (int i=0; i < todoLists.size(); i++) {
			String uri = String.format("/todolists/%d", i);
			String name = todoLists.get(i);
			JSONObject list = new JSONObject().put("name", name).put("uri", uri);
			json.append("myLists", list);
		}
		render(json);
    }

	private void respondWithTodoList(Integer id) throws IOException {
	    if (id >= todoLists.size()) {
	    	System.out.println("NON TROVAI");
	    	notFound();
	    	return;
	    }
	    
	    render(new JSONObject()
	    	.put("name", todoLists.get(id))
	    	.put("items", emptyList()));
    }

	private void respondWith(int status, String message) throws IOException {
	    response.setStatus(status);
	    render(new JSONObject()
	    	.put("message", message)
	    	.put("status", status));
    }

	private void notFound() throws IOException {
		respondWith(SC_NOT_FOUND, "Not found");
    }

	private String getUriParameter(int position) {
	    return matcher.group(position);
    }

	private boolean uriMatches(String regex) {
		Pattern pattern = Pattern.compile(regex);
		matcher = pattern.matcher(request.getRequestURI());
		return matcher.matches();
    }

	private void render(JSONObject json) throws IOException {
	    response.setContentType("application/json");
	    response.getWriter().write(json.toString(2));
    }

	private boolean parameterIsMissing(String parameterName) {
	    return null == request.getParameter(parameterName) || request.getParameter(parameterName).isEmpty();
    }

	private boolean isPost() {
	    return "POST".equals(request.getMethod());
    }

}
