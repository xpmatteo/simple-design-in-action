package it.xpug.hangman.main;


import static org.junit.Assert.*;
import static org.mortbay.util.ajax.JSON.*;
import it.xpug.generic.web.*;
import it.xpug.hangman.domain.*;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;
import org.junit.*;


public class HangmanEnd2EndTest {

	@Test
	public void rootUrl() throws Exception {
		get("/");
		assertStatus(200);
		assertMimeType("application/json; charset=UTF-8");
		assertHeader("Access-Control-Allow-Origin", "*");
		assertBody("{\"index\":\"/\",\"users\":\"/users\"}");
	}

	@Test
	public void unauthenticatedUsers() throws Exception {
		get("/users");
		assertBody("{" +
				"\"description\": \"Use POST on /users to create a user\",\n" +
				"\"status\": \"Method not allowed\",\n" +
				"\"status_code\": 405\n" +
				"}");
		assertStatus(405);
		assertMimeType("application/json; charset=UTF-8");
	}

	@Test
	public void createAUser() throws Exception {
		params.put("name", "Pippo");
		params.put("password", "Pluto");
		post("/users");

		assertStatus(201);
		assertMimeType("application/json; charset=UTF-8");
		assertLocationHeader("/users/4cc2d9f6");
		assertBody("{\"status_code\": 201, \"location\": \"/users/4cc2d9f6\", \"status\": \"Created\"}");
	}

	@Test
	public void validationErrorOnUserCreation() throws Exception {
		post("/users");

		assertStatus(400);
		assertMimeType("application/json; charset=UTF-8");
		assertBody("{"
				+ "\"status_code\": 400, "
				+ "\"status\": \"Bad Request\", "
				+ "\"description\": \"Parameter 'name' is required\","
				+ "}");
	}

	@Test
	public void seeMyself() throws Exception {
		givenUser("888", "Pippoz", "s3cr3t");

		params.put("name", "Pippoz");
		params.put("password", "s3cr3t");
		get("/users/888");

		assertStatus(200);
		assertMimeType("application/json; charset=UTF-8");
		assertBody("{" +
				" \"prisoners\": \"/users/888/prisoners\",\n" +
				" \"id\": \"888\",\n" +
				" \"url\": \"/users/888\"\n" +
				"}\n" +
				"");
	}

	@Test
	public void getPrisonersWithNoPrisoners() throws Exception {
		givenUser("999", "zug", "zot");

		params.put("password", "zot");
		get("/users/999/prisoners");

		assertStatus(200);
		assertMimeType("application/json; charset=UTF-8");
	}

	@Test
	public void wrongPassword() throws Exception {
		givenUser("123", "Pippoz", "s3cr3t");

		params.put("name", "Pippoz");
		params.put("password", "nottherightpassword");
		get("/users/123");

		assertStatus(403);
		assertMimeType("application/json; charset=UTF-8");
		assertBody("{\n" +
				" \"description\": \"You don't have the permission to access the requested resource. It is either read-protected or not readable by the server.\",\n" +
				" \"status\": \"Forbidden\",\n" +
				" \"status_code\": 403\n" +
				"}");
	}


	private void givenUser(String userId, String name, String password) {
		users.add(new UserId(userId), name, password);
	}

	private void assertBody(String expectedBody) throws IllegalStateException, IOException {
		byte[] bytes = new byte[10000];
		response.getEntity().getContent().read(bytes);
		String body = new String(bytes, Charset.forName("UTF-8"));
		assertEquals("Body", parse(expectedBody), parse(body));
	}

	private void assertMimeType(String expectedMimeType) {
		assertHeader("content-type", expectedMimeType);
	}

	private void assertLocationHeader(String expectedLocation) {
		assertHeader("location", expectedLocation);
	}

	private void assertHeader(String name, String expectedValue) {
		Header header = response.getLastHeader(name.toLowerCase());
		assertNotNull(name + " not set", header);
		assertEquals(name, expectedValue, header.getValue());
	}

	private void assertStatus(int expectedStatus) {
		assertEquals("Status code", expectedStatus, response.getStatusLine().getStatusCode());
	}

	private void get(String path) throws IOException, URISyntaxException {
		URI url = new URI(baseUrl() + path + queryString());
		HttpGet request = new HttpGet(url);
		this.response = makeHttpClient().execute(request);
	}

	private void post(String path) throws URISyntaxException, ClientProtocolException, IOException {
		URI url = new URI(baseUrl() + path);
		HttpPost request = new HttpPost(url);
		addParameters(request);
		this.response = makeHttpClient().execute(request);
	}

	private HttpClient makeHttpClient() {
		return HttpClientBuilder.create().disableRedirectHandling().build();
	}

	private String baseUrl() {
		return "http://localhost:" + APPLICATION_PORT;
	}

	private String queryString() {
		String queryString = "";
		for (String name : params.keySet()) {
			if (!queryString.isEmpty())
				queryString += "&";
			queryString += name + "=" + params.get(name);
		}
		if (!queryString.isEmpty())
			queryString = "?" + queryString;
		return queryString;
	}

	private void addParameters(HttpPost request) throws UnsupportedEncodingException {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (String name : params.keySet()) {
			parameters.add(new BasicNameValuePair(name, params.get(name)));
		}
		request.setEntity(new UrlEncodedFormEntity(parameters));
	}

	@BeforeClass
	public static void startApplication() throws Exception {
		app.start(APPLICATION_PORT, "../hangman-server/src/main/webapp");
	}

	@AfterClass
	public static void shutdownApplication() throws Exception {
		app.shutdown();
	}

	private static final int APPLICATION_PORT = 8123;
	private static UserBase users = new UserBase(new Random(888));
	private static ReusableJettyApp app = new ReusableJettyApp(new HangmanServlet(users));
	private HttpResponse response;
	private Map<String, String> params = new HashMap<String, String>();
}
