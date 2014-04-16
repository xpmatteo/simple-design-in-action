package it.xpug.hangman.main;


import it.xpug.example.main.*;
import it.xpug.generic.web.*;

import org.junit.*;


public class HelloWorldTest extends BaseTestCase {

	@Test
	public void helloWorld() throws Exception {
		get("/hello");
		assertStatus(200);
		assertContentType("text/html; charset=utf-8");
		assertBody("<h1>Hello, world!</h1>");
	}

	@Test@Ignore
	public void helloName() throws Exception {
		get("/hello?name=Pippo");
		assertBody("<h1>Hello, Pippo!</h1>");
	}

	@BeforeClass
	public static void startApplication() throws Exception {
		app.start(APPLICATION_PORT, "src/main/webapp");
	}

	@AfterClass
	public static void shutdownApplication() throws Exception {
		app.shutdown();
	}

	private static ReusableJettyApp app = new ReusableJettyApp(new ExampleServlet());
}
