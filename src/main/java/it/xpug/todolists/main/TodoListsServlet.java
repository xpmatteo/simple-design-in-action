package it.xpug.todolists.main;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class TodoListsServlet extends HttpServlet {

	private List<String> todoLists;

	public TodoListsServlet(List<String> todoLists) {
		this.todoLists = todoLists;
    }

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Serving: " + request.getMethod() + " " + request.getRequestURI());

		Resource resource = getResource(request, response);
		resource.service();
	}

	private Resource getResource(HttpServletRequest request, HttpServletResponse response) {
		if ("/".equals(request.getRequestURI())) {
			return new HelloWorldResource(response);
		}
		if (request.getRequestURI().startsWith("/todolists")) {
			return new TodoListsResource(request, response, todoLists);
		}
	    return new Notfound(response);
    }

}
