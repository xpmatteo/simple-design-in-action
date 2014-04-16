package it.xpug.example.main;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ExampleServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("Hello!");
	}
}
