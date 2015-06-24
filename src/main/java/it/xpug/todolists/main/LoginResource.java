package it.xpug.todolists.main;

import java.io.*;

import javax.servlet.http.*;

import org.json.JSONObject;

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
		if ("x@x.com".equals(email) && "x".equals(password)) {
			String newSessionId = sessions.newSessionId();
			sessions.add(new TodoListSession(newSessionId, new User("x", "x@x.com")));
			response.addCookie(new Cookie(AuthenticationFilter.SESSION_COOKIE, newSessionId));
		} else {
			response.setStatus(403);
			render(new JSONObject().put("message", "Email or password not valid"));
		}
	}
}
