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
		if (request.getRequestURI().equals("/hello")) {
			doHello(request, response);
		} else {
			returnNotFound(response);
		}
	}

	private void doHello(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!request.getMethod().equals("GET")) {
			returnMethodNotAllowed(response);
			return;
		}
	    String name = request.getParameter("name");
	    if (null == name)
	    	name = "world";
	    response.setStatus(200);
	    response.getWriter().write(String.format("Hello, %s!", name));
    }

	private void returnMethodNotAllowed(HttpServletResponse response) throws IOException {
	    returnError(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Method not allowed");
    }

	private void returnNotFound(HttpServletResponse response) throws IOException {
	    returnError(response, 404, "Ooops! Not found!");
    }

	private void returnError(HttpServletResponse response, int statusCode, String message) throws IOException {
	    response.setStatus(statusCode);
		response.getWriter().write(message);
    }
}
