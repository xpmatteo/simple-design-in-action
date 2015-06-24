package it.xpug.todolists.main;

public class TodoListSession {

	private String id;

	public TodoListSession(String id, User user) {
		this.id = id;
	}

	public String getId() {
	    return id;
    }

}
