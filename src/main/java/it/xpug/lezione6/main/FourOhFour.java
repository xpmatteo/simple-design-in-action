package it.xpug.lezione6.main;

import java.io.*;

import javax.servlet.http.*;

public class FourOhFour extends Resource {

	private HttpServletResponse response;

	public FourOhFour(HttpServletResponse response) {
		this.response = response;
    }

	@Override
	public void service() throws IOException {
		response.setStatus(404);
		response.setContentType("application/json");
		response.getWriter().write("{\"error\": \"Not found\"}");
	}

}
