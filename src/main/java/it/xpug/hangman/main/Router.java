package it.xpug.hangman.main;

import it.xpug.hangman.web.*;

import java.util.*;

public class Router {

	protected Map<String, Resource> map = new HashMap<String, Resource>();
	protected Filter filter;

	protected void setFilter(Filter filter) {
		this.filter = filter;
	}

	protected void addRoute(String path, Resource resource) {
		map.put(path, resource);
	}

	public void service(WebRequest request, WebResponse response) {
		if (filter != null) {
			filter.service(request, response);
			if (!filter.shouldContinue())
				return;
		}

		for (String pathRegexp : map.keySet()) {
			if (request.getPath().matches(pathRegexp)) {
				map.get(pathRegexp).service(request, response);
				return;
			}
		}
	}

}
