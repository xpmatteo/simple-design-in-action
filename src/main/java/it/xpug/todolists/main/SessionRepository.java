package it.xpug.todolists.main;

public interface SessionRepository {

	String newSessionId();

	void add(TodoListSession session);

	TodoListSession get(String sessionId);

}
