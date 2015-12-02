package it.xpug.woodysmart.main.hello;

import static org.junit.Assert.*;
import it.xpug.woodysmart.main.*;
import it.xpug.woodysmart.main.util.*;

import org.junit.*;

public class T1_HelloView_UnitTest {

	private FakeHttpServletResponse response = new FakeHttpServletResponse();
	private HelloView helloView = new HelloView(response);

	@Test@Ignore
    public void showHello() throws Exception {
		helloView.showHello("Foobar");

		assertEquals("<p>Hello, Foobar!</p>", response.getBody());
    }

	@Test@Ignore
    public void showMethodNotAllowed() throws Exception {
		helloView.showMethodNotAllowed();

		assertEquals("<p>Method not allowed</p>", response.getBody());
		assertEquals(405, response.getStatusCode());
    }

	/*
	 * Now is a good time to start the application and test it manually!
	 *
	 * And also remember to un-ignore the T2_HelloWorld_AcceptanceTest
	 * and check that they now work!
	 */
}
