package it.xpug.woodysmart.main;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class WoodysMartServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(200);
		response.getWriter().write("....");
	}

}
