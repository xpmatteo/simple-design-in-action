package it.xpug.todolists.main;

import static java.util.Collections.*;

import java.util.*;

public class InMemoryTodoListRepository implements TodoListRepository {
	private List<TodoList> todoLists = synchronizedList(new ArrayList<TodoList>());

	@Override
	public TodoList get(int todoListId) {
		return todoLists.get(todoListId);
	}

	@Override
	public int add(TodoList todoList) {
		synchronized (todoLists) {
			todoLists.add(todoList);
			todoList.setId(todoLists.size()-1);
			return todoList.getId();
        }
	}

	@Override
	public List<TodoList> getAll() {
		return new ArrayList<TodoList>(todoLists);
	}

	@Override
    public void clear() {
		todoLists.clear();
    }

	@Override
    public long size() {
	    return todoLists.size();
    }

	@Override
    public void update(TodoList todoList) {
    }

}
