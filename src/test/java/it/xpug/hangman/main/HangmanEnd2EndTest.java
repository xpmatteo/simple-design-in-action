package it.xpug.hangman.main;


import static org.junit.Assert.*;
import static org.mortbay.util.ajax.JSON.*;
import it.xpug.example.main.*;
import it.xpug.generic.web.*;

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
		assertMimeType("text/html; charset=UTF-8");
		assertBody("<h1>Hello, world!</h1>");
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
	private static ReusableJettyApp app = new ReusableJettyApp(new ExampleServlet());
	private HttpResponse response;
	private Map<String, String> params = new HashMap<String, String>();
}
