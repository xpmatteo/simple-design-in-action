package it.xpug.todolists.main;

import java.util.*;

public class InMemoryUserRepository {

	Map<String, User> usersByPassword = new HashMap<String, User>();

	public void add(User user, String clearTextPassword) {
		usersByPassword.put(clearTextPassword, user);
    }

	public User authenticate(String email, String clearTextPassword) {
	    User user = usersByPassword.get(clearTextPassword);
	    if (null == user)
	    	return null;
	    if (!user.getEmail().equals(email))
	    	return null;
	    return user;
    }

}
