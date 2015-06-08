package it.xpug.todolists.main;

import java.io.*;

import javax.servlet.http.*;

public class Notfound extends Resource {

	private HttpServletResponse response;

	public Notfound(HttpServletResponse response) {
		super(null, response);
		this.response = response;
    }

	@Override
	public void service() throws IOException {
		response.setStatus(404);
		response.setContentType("application/json");
		response.getWriter().write("{\"error\": \"Not found\"}");
	}

}
