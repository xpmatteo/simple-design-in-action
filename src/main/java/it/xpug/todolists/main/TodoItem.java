package it.xpug.todolists.main;

import org.json.*;

public class TodoItem {
	private String text;
	private boolean isChecked;

	public TodoItem(String text) {
	    this.text = text;
    }

	public JSONObject toJson() {
	    return new JSONObject()
	    	.put("text", text)
	    	.put("status", isChecked ? "checked" : "unchecked");
    }

}
