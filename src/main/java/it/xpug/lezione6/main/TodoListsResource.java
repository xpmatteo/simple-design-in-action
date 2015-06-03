package it.xpug.lezione6.main;

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
		if ("POST".equals(request.getMethod()) && null == request.getParameter("name")) {
			// ... return 400 Bad Request: parameter 'name' required
			return;
		}
		if ("POST".equals(request.getMethod())) {
			todoLists.add(request.getParameter("name"));
			response.sendRedirect(request.getRequestURI());
			return;
		}
		
		JSONObject json = new JSONObject();
		json.put("myLists", todoLists);
		response.setContentType("application/json");
		response.getWriter().write(json.toString(2));
	}

}
