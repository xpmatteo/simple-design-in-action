package it.xpug.hangman.main;

import static org.junit.Assert.*;

import java.io.*;
import java.net.*;

import it.xpug.example.main.*;
import it.xpug.generic.web.*;

import org.junit.*;

public class TemperatureConversionTest extends BaseTestCase {

	@Test@Ignore
	public void containsAFieldForCelsius() throws Exception {
		get("/temp");
		assertStatus(200);
		assertContentType("text/html");
		String expected = "<input type='text' name='celsius' />";
		assertBodyContains(expected);
	}

	@Test@Ignore
	public void convertsCelsiusToFahrenheit() throws Exception {
		get("/temp?celsius=0");
		assertStatus(200);
		assertContentType("text/html");
		assertBodyContains("<p id='output'>0.0&deg;C = 32.0&deg;F</p>");
	}

	@Test@Ignore
	public void containsAFieldForFahrenheit() throws Exception {
		get("/temp");
		assertStatus(200);
		assertContentType("text/html");
		assertBodyContains("<input type='text' name='fahrenheit' />");
	}

	@Test@Ignore
	public void convertsFahrenheitToCelsius() throws Exception {
		get("/temp?fahrenheit=32");
		assertStatus(200);
		assertContentType("text/html");
		assertBodyContains("<p id='output'>32.0&deg;F = 0.0&deg;C</p>");
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
