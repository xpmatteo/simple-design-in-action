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

	private List<TodoList> todoLists;

	public TodoListsResource(HttpServletRequest request, HttpServletResponse response, List<TodoList> todoLists) {
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
			synchronized (todoLists) {
				todoLists.add(new TodoList(request.getParameter("name")));
				response.sendRedirect("/todolists/" + (todoLists.size()-1));
            }
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

	private void respondWithTodoList(Integer todoListId) throws IOException {
	    if (todoListId >= todoLists.size()) {
	    	notFound();
	    	return;
	    }
		JSONObject json = todoLists.get(todoListId).toJson();
		JSONArray items = (JSONArray) json.get("items");
		for (int itemId=0; itemId<items.length(); itemId++) {
			JSONObject todoItem = (JSONObject) items.get(itemId);
			todoItem.put("uri", format("/todolists/%d/items/%d", todoListId, itemId));
		}
		render(json);
    }
}
