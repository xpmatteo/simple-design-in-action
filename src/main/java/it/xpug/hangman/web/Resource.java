package it.xpug.hangman.web;


public abstract class Resource {

	public void service(WebRequest request, WebResponse response) {
		if (request.isGet()) {
			doGet(request, response);
		} else {
			doPost(request, response);
		}
	}

	public void doGet(WebRequest request, WebResponse response) {
		response.methodNotAllowed("Can't use GET on " + request.getPath());
	}

	public void doPost(WebRequest request, WebResponse response) {
		response.methodNotAllowed("Can't use POST on " + request.getPath());
	}

}
