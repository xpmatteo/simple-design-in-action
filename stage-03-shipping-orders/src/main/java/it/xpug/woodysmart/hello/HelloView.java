package it.xpug.woodysmart.hello;

import java.io.*;

import javax.servlet.http.*;

public class HelloView {

	private HttpServletResponse response;

	public HelloView(HttpServletResponse response) {
		this.response = response;
    }

	public void showHello(String toWhom) throws IOException {
		response.getWriter().write(String.format("<p>Hello, %s!</p>", toWhom));
    }

	public void showMethodNotAllowed() throws IOException {
		response.getWriter().write("<p>Method not allowed</p>");
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

}
