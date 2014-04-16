package it.xpug.hangman.web;


public class IndexResource extends Resource {

	public void doGet(WebRequest request, WebResponse response) {
		response.put("users", "/users");
		response.put("index", "/");
	}

}
