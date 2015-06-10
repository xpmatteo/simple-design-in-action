package it.xpug.todolists.main;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.json.*;

public class TodoItemsResource extends Resource {

	private List<TodoList> todoLists;

	public TodoItemsResource(HttpServletRequest request, HttpServletResponse response, List<TodoList> todoLists) {
	    super(request, response);
		this.todoLists = todoLists;
    }

	@Override
	public void service() throws IOException {
		if (uriMatches("/todolists/(\\d+)/items")) {
			int todoListId = Integer.parseInt(getUriParameter(1));
			TodoList todoList = todoLists.get(todoListId);
			todoList.addItem(request.getParameter("text"));
			response.sendRedirect("/todolists/" + todoListId);
		}

	}

}
