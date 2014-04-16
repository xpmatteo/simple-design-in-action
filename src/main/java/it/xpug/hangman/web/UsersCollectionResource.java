package it.xpug.hangman.web;

import it.xpug.hangman.domain.*;

public class UsersCollectionResource extends Resource {

	private UserBase users;

	public UsersCollectionResource(UserBase users) {
		this.users = users;
	}

	@Override
	public void doGet(WebRequest request, WebResponse response) {
		response.methodNotAllowed("Use POST on /users to create a user");
	}

	@Override
	public void doPost(WebRequest request, WebResponse response) {
		createNewUser(request, response);
	}

	private void createNewUser(WebRequest request, WebResponse response) {
		if (null == request.getParameter("name")) {
			response.validationError("Parameter 'name' is required");
		} else if (null == request.getParameter("password")) {
			response.validationError("Parameter 'password' is required");
		} else {
			String newUserId = users.getNextUserId();
			String path = "/users/" + newUserId;
			response.created(path);
			users.add(new UserId(newUserId), "a name", request.getParameter("password"));
		}
	}

}
