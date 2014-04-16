package it.xpug.hangman.main;

import static org.junit.Assert.*;
import it.xpug.hangman.domain.*;
import it.xpug.hangman.web.*;

import java.util.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;

public class HangmanRouterTest {

	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();
	WebResponse response = context.mock(WebResponse.class);
	WebRequest request = context.mock(WebRequest.class);

	Random random = new Random(444);
	UserBase users = new UserBase(random);
	Router router = new HangmanRouter(users);

	@Test
	public void itTakesANameToCreateAUser() throws Exception {
		givenPostRequest("/users");
		givenNoParameters();
		context.checking(new Expectations() {{
			oneOf(response).validationError("Parameter 'name' is required");
		}});

		router.service(request, response);
	}

	@Test
	public void itTakesAPasswordToCreateAUser() throws Exception {
		givenPostRequest("/users");
		givenParameter("name", "gino");
		givenNoParameter("password");
		context.checking(new Expectations() {{
			oneOf(response).validationError("Parameter 'password' is required");
		}});

		router.service(request, response);
	}

	@Test
	public void createsAUser() throws Exception {
		givenPostRequest("/users");
		givenParameter("name", "gino");
		givenParameter("password", "secr3t");

		context.checking(new Expectations() {{
			oneOf(response).created("/users/3cb54a30");
		}});

		router.service(request, response);

		assertTrue("user created", users.contains(new UserId("3cb54a30"), "secr3t"));
	}

	@Test
	public void returnListOfPrisoners() throws Exception {
		final UserId userId = new UserId("1234");
		users.add(userId, "name", "s3cret");
		givenGetRequest("/users/1234/prisoners");
		givenParameter("password", "s3cret");
		context.checking(new Expectations() {{
			allowing(request).getUserId(); will(returnValue(userId));
			oneOf(response).put("items", emptyList());
			oneOf(response).put("url", "/users/1234/prisoners");
		}});
		router.service(request, response);
	}

	@Test
	public void createAPrisoner() throws Exception {
		final UserId userId = new UserId("1234");
		users.add(userId, "name", "s3cret");
		givenPostRequest("/users/1234/prisoners");
		givenParameter("password", "s3cret");
		context.checking(new Expectations() {{
			allowing(request).getUserId(); will(returnValue(userId));
			oneOf(response).created("/users/1234/prisoners/3cb54a30");
		}});
		router.service(request, response);

		assertEquals(1, users.findPrisoners(userId).size());
	}

	@Test
	public void guess() throws Exception {
		final UserId userId = new UserId("1234");
		users.add(userId, "name", "s3cret");
		users.addPrisoner(userId, new Prisoner("abc123", "word"));

		givenPostRequest("/users/1234/prisoners/abc123");
		givenParameter("password", "s3cret");
		givenParameter("guess", "x");

		context.checking(new Expectations() {{
			allowing(request).getUserId(); will(returnValue(userId));
			allowing(request).getPrisonerId(); will(returnValue("abc123"));
			oneOf(response).created("/users/1234/prisoners/abc123");
		}});
		router.service(request, response);

		assertEquals(17, users.findPrisoner(userId, "abc123").getGuessesRemaining());
	}

	@Test
	public void guessValidation() throws Exception {
		final UserId userId = new UserId("1234");
		users.add(userId, "name", "s3cret");
		users.addPrisoner(userId, new Prisoner("abc123", "word"));

		givenPostRequest("/users/1234/prisoners/abc123");
		givenParameter("password", "s3cret");
		givenNoParameter("guess");
		context.checking(new Expectations() {{
			allowing(request).getUserId(); will(returnValue(userId));
			oneOf(response).validationError("Parameter 'guess' is required");
		}});
		router.service(request, response);

		assertEquals(18, users.findPrisoner(userId, "abc123").getGuessesRemaining());
	}

	@Test
	public void guessForbidden() throws Exception {
		final UserId userId = new UserId("1234");
		users.add(userId, "name", "s3cret");
		users.addPrisoner(userId, new Prisoner("abc123", "word"));

		givenPostRequest("/users/1234/prisoners/abc123");
		givenNoParameter("password");
		givenParameter("guess", "x");
		context.checking(new Expectations() {{
			allowing(request).getUserId(); will(returnValue(userId));
			allowing(request).getPrisonerId(); will(returnValue("abc123"));
			oneOf(response).forbidden(with(any(String.class)));
		}});
		router.service(request, response);

		assertEquals(18, users.findPrisoner(userId, "abc123").getGuessesRemaining());
	}

	private void givenPostRequest(final String path) {
		context.checking(new Expectations() {{
			allowing(request).getPath(); will(returnValue(path));
			allowing(request).isGet(); will(returnValue(false));
			allowing(request).isPost(); will(returnValue(true));
		}});
	}

	private void givenGetRequest(final String path) {
		context.checking(new Expectations() {{
			allowing(request).getPath(); will(returnValue(path));
			allowing(request).isGet(); will(returnValue(true));
			allowing(request).isPost(); will(returnValue(false));
		}});
	}

	private void givenNoParameters() {
		context.checking(new Expectations() {{
			allowing(request).getParameter(with(any(String.class))); will(returnValue(null));
		}});
	}

	private void givenParameter(final String name, final String value) {
		context.checking(new Expectations() {{
			allowing(request).getParameter(with(name)); will(returnValue(value));
		}});
	}

	private void givenNoParameter(final String name) {
		context.checking(new Expectations() {{
			allowing(request).getParameter(name); will(returnValue(null));
		}});
	}

	private List<Prisoner> emptyList() {
		return new ArrayList<Prisoner>();
	}
}
