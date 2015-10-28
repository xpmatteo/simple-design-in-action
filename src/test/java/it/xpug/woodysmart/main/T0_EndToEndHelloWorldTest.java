package it.xpug.woodysmart.main;



import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import it.xpug.woodysmart.main.util.*;

import org.junit.*;

public class T0_EndToEndHelloWorldTest extends TestWithALiveServer {

	@Test
	public void helloWorld() throws Exception {
		get("/hello");

		assertStatus(200);
		assertThat(responseBody(), containsString("Hello, world!"));
	}

	@Test@Ignore
	public void notFound() throws Exception {
		get("/notexistent");

		assertStatus(404);
		assertThat(responseBody(), containsString("Ooops! Not found!"));
	}

	@Test@Ignore
	public void helloWithNameParameter() throws Exception {
		params.put("name", "Woody");
		get("/hello");

		assertStatus(200);
		assertThat(responseBody(), containsString("Hello, Woody!"));
	}

	@Test@Ignore
	public void helloOnlyWorksWithGetMethod() throws Exception {
		post("/hello");

		assertStatus(405); // method not allowed
		assertThat(responseBody(), containsString("Method not allowed"));
	}
}
