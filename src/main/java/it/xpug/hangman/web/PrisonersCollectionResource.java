package it.xpug.hangman.web;

import it.xpug.hangman.domain.*;

public class PrisonersCollectionResource extends Resource {

	private UserBase users;

	public PrisonersCollectionResource(UserBase users) {
		this.users = users;
	}

	public void doGet(WebRequest request, WebResponse response) {
		UserId userId = request.getUserId();
		response.put("url", "/users/" + userId + "/prisoners");
		response.put("items", users.findPrisoners(userId));
	}

	public void doPost(WebRequest request, WebResponse response) {
		String prisonerId = users.getNextUserId();
		String path = request.getPath() + "/" + prisonerId;

		response.created(path);
		users.addPrisoner(request.getUserId(), new Prisoner(prisonerId, new WordList().getRandomWord()));
	}

}
