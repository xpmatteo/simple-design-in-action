package it.xpug.todolists.main;

import java.io.*;

import javax.servlet.http.*;

public class LoginResource extends Resource {

	private SessionRepository sessions;

	public LoginResource(HttpServletRequest request, HttpServletResponse response, SessionRepository sessions) {
	    super(request, response);
		this.sessions = sessions;
    }

	@Override
	public void service() throws IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if ("x".equals(email) && "x".equals(password)) {
			String newSessionId = sessions.newSessionId();
			sessions.add(new TodoListSession(newSessionId, null));
			response.addCookie(new Cookie(AuthenticationFilter.SESSION_COOKIE, newSessionId));
		}
	}
}
