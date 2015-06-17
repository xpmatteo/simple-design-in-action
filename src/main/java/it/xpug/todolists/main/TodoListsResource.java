package it.xpug.todolists.main;

import static java.lang.Integer.*;
import static java.lang.String.*;
import static java.util.Collections.*;
import static javax.servlet.http.HttpServletResponse.*;

import java.io.*;

import javax.servlet.http.*;

import org.json.*;

public class TodoListsResource extends Resource {

	private TodoListRepository todoLists;

	public TodoListsResource(HttpServletRequest request, HttpServletResponse response, TodoListRepository todoLists) {
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
			int newTodoListId = todoLists.add(new TodoList(request.getParameter("name")));
			response.sendRedirect("/todolists/" + newTodoListId);
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
		for (TodoList todoList : todoLists.getAll()) {
			json.append("myLists", new JSONObject()
				.put("name", todoList.getName())
				.put("uri", format("/todolists/%d", todoList.getId()))
			);
        }
		render(json);
    }

	private void respondWithTodoList(Integer todoListId) throws IOException {
		TodoList todoList = todoLists.get(todoListId);
	    if (null == todoList) {
	    	notFound();
	    	return;
	    }
		JSONObject json = todoList.toJson();
		JSONArray items = (JSONArray) json.get("items");
		for (int itemId=0; itemId<items.length(); itemId++) {
			JSONObject todoItem = (JSONObject) items.get(itemId);
			todoItem.put("uri", format("/todolists/%d/items/%d", todoListId, itemId));
		}
		render(json);
    }
}
