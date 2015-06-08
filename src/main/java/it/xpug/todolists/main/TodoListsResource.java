package it.xpug.todolists.main;

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

	public TodoListsResource(HttpServletRequest request, HttpServletResponse response, List<String> todoLists) {
		this.request = request;
		this.response = response;
		this.todoLists = todoLists;
    }

	@Override
	public void service() throws IOException {
		if (isPost() && parameterIsMissing("name")) {
			response.setStatus(SC_BAD_REQUEST);
			render(new JSONObject()
				.put("message", "Parameter 'name' is required")
				.put("status", SC_BAD_REQUEST));
			return;
		}
		if (isPost()) {
			todoLists.add(request.getParameter("name"));
			response.sendRedirect(request.getRequestURI());
			return;
		}
		
		Pattern pattern = Pattern.compile("/todolists/(\\d+)");
		Matcher matcher = pattern.matcher(request.getRequestURI());
		if (matcher.matches()) {
			System.out.println("SI HO RICOMPILATO");
			String idAsString = matcher.group(1);
			Integer id = Integer.valueOf(idAsString);

			JSONObject json = new JSONObject();
			json.put("name", todoLists.get(id));
			json.put("items", emptyList());
			render(json);
			return;
		}
		
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
