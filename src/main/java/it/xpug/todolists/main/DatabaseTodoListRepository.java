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
		ListOfRows rows = database.select("select * from todo_lists where id = ?", todoListId);
		if (rows.size() == 0)
			return null;
		String name = (String) rows.get(0).get("name");
		return new TodoList(todoListId, name);
	}

	@Override
	public int add(TodoList todoList) {
		String sql = "insert into todo_lists (name) values (?) returning id";
		ListOfRows rows = database.select(sql, todoList.getName());
		return (int) rows.get(0).get("id");
	}

	@Override
	public List<TodoList> getAll() {
		return null;
	}

	@Override
	public void clear() {
		database.execute("truncate todo_lists");
	}

	@Override
	public long size() {
		return (long) database.selectOneValue("select count(*) from todo_lists");
	}

}
