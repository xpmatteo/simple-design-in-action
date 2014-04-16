package it.xpug.hangman.web;

import it.xpug.hangman.domain.*;

public class PrisonerResource extends Resource {

	private UserBase users;

	public PrisonerResource(UserBase users) {
		this.users = users;
	}

	public void doGet(WebRequest request, WebResponse response) {
		UserId userId = request.getUserId();
		String prisonerId = request.getPrisonerId();
		response.put("url", "/users/" + userId + "/prisoners/" + prisonerId);
		response.put("prisoner", users.findPrisoner(userId, prisonerId));
	}

	public void doPost(WebRequest request, WebResponse response) {
		if (null == request.getParameter("guess")) {
			response.validationError("Parameter 'guess' is required");
			return;
		}
		UserId userId = request.getUserId();
		String prisonerId = request.getPrisonerId();
		Prisoner prisoner = users.findPrisoner(userId, prisonerId);
		prisoner.guess(request.getParameter("guess"));
		response.created(request.getPath());
	}
}
