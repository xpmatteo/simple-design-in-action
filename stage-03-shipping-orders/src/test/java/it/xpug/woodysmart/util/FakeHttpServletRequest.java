package it.xpug.woodysmart.util;

import java.util.*;

public class FakeHttpServletRequest extends NullServletRequest {

	private String requestURI;
	private String method = "GET";
	private Map<String, String> parameters = new HashMap<String, String>();

	public FakeHttpServletRequest withRequestURI(String requestURI) {
		this.requestURI = requestURI;
	    return this;
    }

	public FakeHttpServletRequest withPostMethod() {
		method = "POST";
	    return this;
    }

	public FakeHttpServletRequest withParameter(String name, String value) {
	    parameters.put(name, value);
	    return this;
    }

	@Override
	public String getMethod() {
	    return method;
	}

	@Override
	public String getParameter(String name) {
	    return parameters.get(name);
	}

	@Override
	public String getRequestURI() {
	    return requestURI;
	}

}
