package it.xpug.todolists.main;

import static java.lang.Integer.*;
import static java.lang.String.*;
import static java.util.Collections.*;
import static javax.servlet.http.HttpServletResponse.*;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.json.*;

public class TodoListsResource extends Resource {
	
	private List<String> todoLists;
	
	public TodoListsResource(HttpServletRequest request, HttpServletResponse response, List<String> todoLists) {
		super(request, response);
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
			json.append("myLists", new JSONObject()
				.put("name", todoLists.get(i))
				.put("uri", format("/todolists/%d", i))
			);
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
}
