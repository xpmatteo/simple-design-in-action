package it.xpug.todolists.main;

import java.util.*;

public interface TodoListRepository {

	TodoList get(int todoListId);

	int add(TodoList todoList);

	List<TodoList> getAll();

	void clear();

	long size();

	void update(TodoList todoList);

}
