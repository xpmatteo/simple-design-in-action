package it.xpug.woodysmart.hello;

import java.io.*;

import javax.servlet.http.*;

public class HelloController {

	private HttpServletRequest request;
	private HelloView view;

	public HelloController(HttpServletRequest request, HelloView view) {
		this.request = request;
		this.view = view;
	}

	public void service() throws IOException {
		if (request.getMethod().equals("POST")) {
			view.showMethodNotAllowed();
			return;
		}

		String name = request.getParameter("name");
		if (null == name)
			name = "world";
		view.showHello(name);
	}

}
