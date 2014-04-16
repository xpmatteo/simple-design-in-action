package it.xpug.hangman.web;

public interface WebResponse {

	void methodNotAllowed(String description);
	void created(String path);
	void forbidden(String description);
	void validationError(String message);

	void put(String name, Object value);

}