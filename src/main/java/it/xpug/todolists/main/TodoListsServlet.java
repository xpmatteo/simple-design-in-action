package it.xpug.todolists.main;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class TodoListsServlet extends HttpServlet {

	private List<TodoList> todoLists;

	public TodoListsServlet(List<TodoList> todoLists) {
		this.todoLists = todoLists;
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Serving: " + request.getMethod() + " " + request.getRequestURI());

		Resource resource = getResource(request, response);
		resource.service();
	}

	private Resource getResource(HttpServletRequest request, HttpServletResponse response) {
		if (request.getRequestURI().matches("/todolists/\\d+/items(/\\d+)?")) {
			return new TodoItemsResource(request, response, todoLists);
		}
		if (request.getRequestURI().matches("/todolists(/\\d+)?")) {
			return new TodoListsResource(request, response, todoLists);
		}
		if ("/".equals(request.getRequestURI())) {
			return new HelloWorldResource(response);
		}
	    return new Notfound(response);
    }

}
