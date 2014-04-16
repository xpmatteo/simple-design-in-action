package it.xpug.hangman.web;

public interface Filter {
	void service(WebRequest request, WebResponse response);
	boolean shouldContinue();
}
