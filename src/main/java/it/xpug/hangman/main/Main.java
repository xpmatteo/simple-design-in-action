package it.xpug.hangman.main;


import static java.lang.Integer.*;
import it.xpug.generic.web.*;
import it.xpug.hangman.domain.*;


public class Main {
	public static void main(String[] args) {
		String port = System.getenv("PORT");
		if (port == null || port.isEmpty()) {
			port = "8080";
		}

		ReusableJettyApp app = new ReusableJettyApp(new HangmanServlet(new UserBase()));
		app.start(valueOf(port), "src/main/webapp");
	}
}
