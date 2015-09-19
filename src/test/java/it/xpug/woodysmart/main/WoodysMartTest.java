package it.xpug.woodysmart.main;



import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import it.xpug.toolkit.web.*;

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
import org.json.*;
import org.junit.*;

public class WoodysMartTest {

	private static ReusableJettyApp app = new ReusableJettyApp(new WoodysMartServlet());

	@BeforeClass
	public static void startApplication() throws Exception {
		app.start(8123, "src/main/webapp");
	}

	@AfterClass
	public static void shutdownApplication() throws Exception {
		app.shutdown();
	}

	@Before
	public void clearTodoLists() {
		params.clear();
    }

	@Test
	public void notFound() throws Exception {
		get("/notexistent");

		assertStatus(404);
	}

	@Test
	public void index() throws Exception {
		get("/");

		assertStatus(200);
		assertThat(responseBody(), containsString("Welcome to Woody's Mart!"));
	}


	protected void assertBody(String expectedBody) throws IllegalStateException, IOException {
		String body = responseBody();
		String expected = new JSONObject(expectedBody).toString(2);
		String actual = new JSONObject(body).toString(2);
		assertEquals("Body",  expected, actual);
	}

	private String responseBody() throws IOException {
	    byte[] bytes = new byte[10000];
		int bytesRead = response.getEntity().getContent().read(bytes);
		String body = new String(bytes, 0, bytesRead, Charset.forName("UTF-8"));
	    return body;
    }

	protected void assertMimeType(String expectedMimeType) {
		assertHeader("content-type", expectedMimeType);
	}

	protected void assertLocationHeader(String expectedLocation) {
		assertHeader("location", expectedLocation);
	}

	protected void assertHeader(String name, String expectedValue) {
		Header header = response.getLastHeader(name.toLowerCase());
		assertNotNull(name + " not set", header);
		assertEquals(name, expectedValue, header.getValue());
	}

	protected void assertStatus(int expectedStatus) {
		assertEquals("Status code", expectedStatus, response.getStatusLine().getStatusCode());
	}

	protected void get(String path) throws IOException, URISyntaxException {
		URI url = new URI(baseUrl() + path + queryString());
		HttpGet request = new HttpGet(url);
		this.response = makeHttpClient().execute(request);
	}

	protected void post(String path) throws URISyntaxException, ClientProtocolException, IOException {
		URI url = new URI(baseUrl() + path);
		HttpPost request = new HttpPost(url);
		addParameters(request);
		this.response = makeHttpClient().execute(request);
	}

	protected HttpClient makeHttpClient() {
		return HttpClientBuilder.create().disableRedirectHandling().build();
	}

	protected String baseUrl() {
		return "http://localhost:" + 8123;
	}

	protected String queryString() {
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

	protected void addParameters(HttpPost request) throws UnsupportedEncodingException {
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (String name : params.keySet()) {
			parameters.add(new BasicNameValuePair(name, params.get(name)));
		}
		request.setEntity(new UrlEncodedFormEntity(parameters));
	}

	private HttpResponse response;
	private Map<String, String> params = new HashMap<String, String>();
}
