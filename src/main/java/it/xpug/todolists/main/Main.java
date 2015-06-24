package it.xpug.todolists.main;


import static java.lang.Integer.*;
import it.xpug.toolkit.db.*;
import it.xpug.toolkit.web.*;


public class Main {
	private static final String DATABASE_URL = "postgres://todolists:secret@localhost:5432/todolists_development";

	public static void main(String[] args) {
		String port = System.getenv("PORT");
		if (port == null || port.isEmpty()) {
			port = "8080";
		}

		InMemorySessionRepository sessionRepository = new InMemorySessionRepository();

		DatabaseConfiguration configuration = new DatabaseConfiguration(DATABASE_URL);
		Database database = new Database(configuration);
		TodoListRepository todoLists = new DatabaseTodoListRepository(database);

		ReusableJettyApp app = new ReusableJettyApp(new TodoListsServlet(todoLists, sessionRepository));

		app.start(valueOf(port), "src/main/webapp");
	}
}
