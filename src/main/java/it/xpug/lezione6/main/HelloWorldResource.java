package it.xpug.lezione6.main;

import java.io.*;

import javax.servlet.http.*;

public class HelloWorldResource extends Resource {
	private HttpServletResponse response;

	public HelloWorldResource(HttpServletResponse response) {
		this.response = response;
    }

	@Override
	public void service() throws IOException {
		response.setContentType("application/json");
		String json = "{"
				+ "\"message\": \"Hello, world!\","
				+ "\"todolists\": \"/todolists\""
				+ "}";
		response.getWriter().write(json);
	}

}
