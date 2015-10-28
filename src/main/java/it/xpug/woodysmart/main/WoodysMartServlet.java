package it.xpug.woodysmart.main;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/*
 * Servlet API cheat sheet:
 *
 *   request.getRequestURI()
 *     returns the path of the request.
 *     If the request is for http://localhost:8080/foobar, it returns "/foobar"
 *
 *   request.getParameter(String name)
 *     returns a query parameter.  If the request is for http://localhost:8080/foobar?foo=bar, then
 *       getParameter("foo") returns "bar".
 *       getParameter("something else") returns null.
 *
 *   request.getMethod()
 *   	returns "GET" or "POST" depending on the http request method.
 *
 *   response.setStatus(int status)
 *     sets the HTTP status code.  Example: response.setStatus(200)
 *
 */
public class WoodysMartServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("......");
	}

}
