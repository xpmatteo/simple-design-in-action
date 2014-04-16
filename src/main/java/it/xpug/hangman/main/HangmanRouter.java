package it.xpug.hangman.main;

import it.xpug.hangman.domain.*;
import it.xpug.hangman.web.*;


public class HangmanRouter extends Router {
	public HangmanRouter(UserBase users) {
		setFilter(new PasswordProtectionFilter(users));
		addRoute("/", new IndexResource());
		addRoute("/users", new UsersCollectionResource(users));
		addRoute("/users/[a-f0-9]+/prisoners/[a-f0-9]+", new PrisonerResource(users));
		addRoute("/users/[a-f0-9]+/prisoners", new PrisonersCollectionResource(users));
		addRoute("/users/[a-f0-9]+", new UserResource());
	}
}
