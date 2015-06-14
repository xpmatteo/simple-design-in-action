package it.xpug.todolists.main;


import static java.lang.Integer.*;
import static java.util.Collections.*;

import java.util.*;

import it.xpug.toolkit.web.*;


public class Main {
	public static void main(String[] args) {
		String port = System.getenv("PORT");
		if (port == null || port.isEmpty()) {
			port = "8080";
		}

		List<TodoList> todoLists = synchronizedList(new ArrayList<TodoList>());
		ReusableJettyApp app = new ReusableJettyApp(new TodoListsServlet(todoLists));
		app.start(valueOf(port), "src/main/webapp");
	}
}
