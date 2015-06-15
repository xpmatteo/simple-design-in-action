package it.xpug.todolists.main;

import static java.lang.Integer.*;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

public class TodoItemsResource extends Resource {

	private List<TodoList> todoLists;

	public TodoItemsResource(HttpServletRequest request, HttpServletResponse response, List<TodoList> todoLists) {
	    super(request, response);
		this.todoLists = todoLists;
    }

	@Override
	public void service() throws IOException {
		if (uriMatches("/todolists/(\\d+)/items")) {
			int todoListId = getUriParameterAsInt(1);
			TodoList todoList = todoLists.get(todoListId);
			todoList.addItem(new TodoItem(request.getParameter("text")));
			response.sendRedirect("/todolists/" + todoListId);
			return;
		}

		if (uriMatches("/todolists/(\\d+)/items/(\\d+)")) {
			int todoListId = getUriParameterAsInt(1);
			TodoList todoList = todoLists.get(todoListId);
			todoList.checkItem(getUriParameterAsInt(2), Boolean.valueOf(request.getParameter("checked")));
			response.sendRedirect("/todolists/" + todoListId);
			return;
		}
	}
}
