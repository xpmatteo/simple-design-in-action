package it.xpug.example.main;


import it.xpug.lezione6.main.*;
import it.xpug.toolkit.web.*;

import org.junit.*;


public class HelloWorldTest extends BaseTestCase {

	@Test@Ignore
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

	private static ReusableJettyApp app = new ReusableJettyApp(new Lezione6Servlet());
}
