package it.xpug.todolists.main;

import static org.junit.Assert.*;

import javax.servlet.http.*;

import org.junit.*;


public class AuthenticationFilterTest {

	SessionRepository sessionRepository = new SessionRepository();
	AuthenticationFilter filter = new AuthenticationFilter(sessionRepository);

	@Test
	public void accessWithoutCookie() {
		assertNull("null cookies", filter.getSession(null));
		assertNull("empty cookies", filter.getSession(new Cookie[] {} ));
		assertNull("cookie with bad id", filter.getSession(new Cookie[] { new Cookie("something", "something")} ));
	}

	@Test
    public void accessWithInvalidCookie() throws Exception {
		TodoListSession session = new TodoListSession();
		sessionRepository.put("1234", session);

		Cookie cookie = new Cookie(AuthenticationFilter.SESSION_COOKIE, "4567");
		assertEquals("session not found", null, filter.getSession(new Cookie[] {cookie}));
    }

	@Test
    public void accessWithValidCookie() throws Exception {
		TodoListSession session = new TodoListSession();
		sessionRepository.put("1234", session);

		Cookie cookie = new Cookie(AuthenticationFilter.SESSION_COOKIE, "1234");
		assertEquals(session, filter.getSession(new Cookie[] {cookie}));
    }

}
