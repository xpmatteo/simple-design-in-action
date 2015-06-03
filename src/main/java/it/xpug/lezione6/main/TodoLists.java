package it.xpug.lezione6.main;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

import org.json.*;

public class TodoLists extends Resource {
	
	private List<String> todoLists;
	private HttpServletResponse response;

	public TodoLists(HttpServletRequest request, HttpServletResponse response, List<String> todoLists) {
		this.response = response;
		this.todoLists = todoLists;
    }

	@Override
	public void service() throws IOException {
		JSONObject json = new JSONObject();
		json.put("myLists", todoLists);
		respondWith(json);
	}

	protected void respondWith(JSONObject json) throws IOException {
	    response.setContentType("application/json");
		response.getWriter().write(json.toString());
    }

}
