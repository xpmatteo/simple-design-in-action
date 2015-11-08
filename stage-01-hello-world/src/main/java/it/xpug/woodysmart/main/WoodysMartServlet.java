package it.xpug.woodysmart.main;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

/*
 *   == Servlet API cheat sheet ==
 *
 *   request.getRequestURI()
 *     returns the path of the request.
 *     If the request is for http://localhost:8080/foobar, it returns "/foobar"
 *
 *   request.getParameter(String name)
 *     returns a request parameter.  If the request is for http://localhost:8080/foobar?foo=bar, then
 *       getParameter("foo") returns "bar".
 *       getParameter("something else") returns null.
 *     It also works for POST request parameters.
 *
 *   request.getMethod()
 *   	returns "GET" or "POST" depending on the http request method.
 *
 *
 *   response.setStatus(int status)
 *     sets the HTTP status code.  Example: response.setStatus(200)
 *
 *   response.getWriter().write("...")
 *     write the content that the user will see.
 *
 *   response.sendRedirect("/foo/bar")
 *     redirect the browser to the url "/foo/bar"
 */
public class WoodysMartServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("......");
	}

}
