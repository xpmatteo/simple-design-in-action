package it.xpug.todolists.main;

import static javax.servlet.http.HttpServletResponse.*;

import java.io.*;
import java.util.*;

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
		
		JSONObject json = new JSONObject();
		json.put("myLists", todoLists);
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
