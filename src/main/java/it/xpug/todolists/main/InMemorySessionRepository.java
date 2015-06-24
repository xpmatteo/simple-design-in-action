package it.xpug.todolists.main;

import java.util.*;

public class InMemorySessionRepository {

	private Random random;
	private Map<String, TodoListSession> sessions = new HashMap<String, TodoListSession>();

	public InMemorySessionRepository(Random random) {
		this.random = random;
    }

	public InMemorySessionRepository() {
		this(new Random());
    }

	public String newSessionId() {
		return Long.toHexString(Math.abs(random.nextLong()));
    }

	public void add(TodoListSession session) {
		sessions.put(session.getId(), session);
    }

	public TodoListSession get(String sessionId) {
	    return sessions.get(sessionId);
    }
}
