package it.xpug.todolists.main;

import static java.util.Collections.*;

import java.util.*;

import org.json.*;

public class TodoList {
	private int id;
	private String name;
	private List<TodoItem> todoItems = new ArrayList<>();

	public TodoList(String name) {
	    this.name = name;
    }

	public String getName() {
	    return name;
    }

	public void addItem(TodoItem todoItem) {
		todoItems.add(todoItem);
    }

	public JSONObject toJson() {
	    JSONObject result = new JSONObject()
	    	.put("name", getName())
	    	.put("items", emptyList());
	    for (TodoItem todoItem : todoItems) {
	        result.append("items", todoItem.toJson());
        }
	    return result;
    }

	public void checkItem(int todoItemId, boolean checked) {
		todoItems.get(todoItemId).setChecked(checked);
    }

	public int getId() {
	    return id;
    }

	public void setId(int id) {
		this.id = id;
    }

}
