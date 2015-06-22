package it.xpug.todolists.main;

import java.io.*;

import javax.servlet.http.*;

public class TodoItemsResource extends Resource {

	private TodoListRepository todoLists;

	public TodoItemsResource(HttpServletRequest request, HttpServletResponse response, TodoListRepository todoLists) {
	    super(request, response);
		this.todoLists = todoLists;
    }

	@Override
	public void service() throws IOException {
		if (uriMatches("/todolists/(\\d+)/items")) {
			int todoListId = getUriParameterAsInt(1);
			TodoList todoList = todoLists.get(todoListId);
			todoList.addItem(new TodoItem(request.getParameter("text")));
			todoLists.update(todoList);
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
