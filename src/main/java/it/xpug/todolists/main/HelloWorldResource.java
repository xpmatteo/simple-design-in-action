package it.xpug.todolists.main;

import java.io.*;

import javax.servlet.http.*;

import org.json.*;

public class HelloWorldResource extends Resource {

	public HelloWorldResource(HttpServletResponse response) {
		super(null, response);
    }

	@Override
	public void service() throws IOException {
		render(new JSONObject()
			.put("message", "Hello, world")
			.put("todolists", "/todolists"));
	}
}
