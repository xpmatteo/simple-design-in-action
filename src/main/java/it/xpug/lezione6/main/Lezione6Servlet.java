package it.xpug.lezione6.main;

import static java.util.Collections.*;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class Lezione6Servlet extends HttpServlet {
	
	static List<String> todoLists = synchronizedList(new ArrayList<String>());

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
		if ("/todolists".equals(request.getRequestURI())) {
			return new TodoListsResource(request, response, todoLists);
		}
	    return new FourOhFour(response);
    }

}
