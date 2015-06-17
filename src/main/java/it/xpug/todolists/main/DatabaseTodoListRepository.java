package it.xpug.todolists.main;

import it.xpug.toolkit.db.*;

import java.util.*;

public class DatabaseTodoListRepository implements TodoListRepository {

	private Database database;

	public DatabaseTodoListRepository(Database database) {
		this.database = database;
	}

	@Override
	public TodoList get(int todoListId) {
		return null;
	}

	@Override
	public int add(TodoList todoList) {
		return 0;
	}

	@Override
	public List<TodoList> getAll() {
		return null;
	}

	@Override
	public void clear() {
	}

	@Override
	public long size() {
		return (long) database.selectOneValue("select count(*) from todo_lists");
	}

}
