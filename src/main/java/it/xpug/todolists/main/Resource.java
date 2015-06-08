package it.xpug.todolists.main;

import static javax.servlet.http.HttpServletResponse.*;

import java.io.*;
import java.util.regex.*;

import javax.servlet.http.*;

import org.json.*;

public abstract class Resource {

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	private Matcher matcher;

	public Resource(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
    }

	public abstract void service() throws IOException;

	protected void respondWith(int status, String message) throws IOException {
	    response.setStatus(status);
	    render(new JSONObject()
	    	.put("message", message)
	    	.put("status", status));
	}

	protected void notFound() throws IOException {
		respondWith(SC_NOT_FOUND, "Not found");
	}

	protected String getUriParameter(int position) {
	    return matcher.group(position);
	}

	protected boolean uriMatches(String regex) {
		Pattern pattern = Pattern.compile(regex);
		matcher = pattern.matcher(request.getRequestURI());
		return matcher.matches();
	}

	protected void render(JSONObject json) throws IOException {
	    response.setContentType("application/json");
	    response.getWriter().write(json.toString(2));
	}

	protected boolean parameterIsMissing(String parameterName) {
	    return null == request.getParameter(parameterName) || request.getParameter(parameterName).isEmpty();
	}

	protected boolean isPost() {
	    return "POST".equals(request.getMethod());
	}

}
