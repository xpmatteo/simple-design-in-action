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
		TodoList todoList = new TodoList(todoListId, name);

		// aggiungi tutti i suoi todoitem

		return todoList;
	}

	@Override
	public int add(TodoList todoList) {
		String sql = "insert into todo_lists (name) values (?) returning id";
		ListOfRows rows = database.select(sql, todoList.getName());
		return (int) rows.get(0).get("id");
	}

	@Override
	public List<TodoList> getAll() {
		List<TodoList> result = new ArrayList<TodoList>();
		ListOfRows rows = database.select("select * from todo_lists order by id");
		for (Map<String, Object> row : rows) {
			String name = (String) row.get("name");
			int id = (int) row.get("id");
			result.add(new TodoList(id, name));
        }
		return result;
	}

	@Override
	public void clear() {
		database.execute("truncate todo_lists");
	}

	@Override
	public long size() {
		return (long) database.selectOneValue("select count(*) from todo_lists");
	}

	@Override
    public void update(TodoList todoList) {
		for (TodoItem todoItem : todoList.getItems()) {
			if (todoItem.getId() == null) {
				String sql = "insert into todo_items "
						+ "(todo_list_id, text, checked)"
						+ "values"
						+ "(?, ?, ?)";
				database.execute(sql, todoList.getId(), todoItem.getText(), todoItem.isChecked());
			}

			// TODO else update the todoitem...
        }
    }

}
