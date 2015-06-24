package it.xpug.todolists.main;

import javax.servlet.http.*;

public class AuthenticationFilter {
	public static final String SESSION_COOKIE = "todolists_session_id";

	private SessionRepository sessionRepository;

	public AuthenticationFilter(SessionRepository sessions) {
		this.sessionRepository = sessions;
    }

	public TodoListSession getSession(Cookie[] cookies) {
		if (null == cookies)
			return null;
		for (int i = 0; i < cookies.length; i++) {
	        Cookie cookie = cookies[i];
	        if (cookie.getName().equals(SESSION_COOKIE)) {
	    	    return sessionRepository.get(cookie.getValue());
	        }
        }
	    return null;
    }

}
