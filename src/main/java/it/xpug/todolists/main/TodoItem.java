package it.xpug.todolists.main;

import org.json.*;

public class TodoItem {
	private String text;
	private boolean isChecked;
	private Integer id;

	public TodoItem(String text) {
	    this.text = text;
    }

	public JSONObject toJson() {
	    return new JSONObject()
	    	.put("text", text)
	    	.put("status", isChecked ? "checked" : "unchecked");
    }

	public boolean isChecked() {
	    return isChecked;
    }

	public void setChecked(boolean checked) {
		isChecked = checked;
    }

	public Integer getId() {
	    return id;
    }

	public String getText() {
	    return text;
    }

}
