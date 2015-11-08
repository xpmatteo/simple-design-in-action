package it.xpug.woodysmart.hello;

import it.xpug.toolkit.web.*;

import java.io.*;

import javax.servlet.http.*;

public class HelloView {

	private HttpServletResponse response;
	private TemplateView template;

	public HelloView(HttpServletResponse response) {
		this.response = response;
		this.template = new TemplateView("hello.ftl");
    }

	public void showHello(String toWhom) throws IOException {
		template.put("name", "abracadabra");
		response.getWriter().write(template.toHtml());
    }

	public void showMethodNotAllowed() throws IOException {
		response.getWriter().write("Method not allowed");
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
    }

}
