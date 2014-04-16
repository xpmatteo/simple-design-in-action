package it.xpug.hangman.main;

import static org.junit.Assert.*;
import it.xpug.hangman.domain.*;

import javax.servlet.http.*;

import org.jmock.*;
import org.jmock.integration.junit4.*;
import org.junit.*;

public class HttpServletWebRequestTest {


	@Rule
	public JUnitRuleMockery context = new JUnitRuleMockery();

	@Test
	public void test() {
		final HttpServletRequest httpServletRequest = context.mock(HttpServletRequest.class);
		context.checking(new Expectations() {
			{
				allowing(httpServletRequest).getRequestURI();
				will(returnValue("/users/abc123"));
			}
		});
		HttpServletWebRequest request = new HttpServletWebRequest(httpServletRequest);
		assertEquals(new UserId("abc123"), request.getUserId());
	}

}
