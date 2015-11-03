package it.xpug.woodysmart.main.util;

import static org.junit.Assert.*;
import it.xpug.toolkit.web.*;
import it.xpug.woodysmart.main.*;

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

public class TestWithALiveServer {

	private static ReusableJettyApp app = new ReusableJettyApp(new WoodysMartServlet());
	private HttpResponse response;
	protected Map<String, String> params = new HashMap<String, String>();

	@BeforeClass
    public static void startApplication() throws Exception {
    	app.start(8123, "src/main/webapp");
    }

	@AfterClass
    public static void shutdownApplication() throws Exception {
    	app.shutdown();
    }

	@Before
    public void clearParams() {
    	params.clear();
    }

	protected String responseBody() throws IOException {
	    byte[] bytes = new byte[10000];
		int bytesRead = response.getEntity().getContent().read(bytes);
		if (bytesRead == -1)
			return "";
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

	protected String queryString() throws UnsupportedEncodingException {
		String queryString = "";
		for (String name : params.keySet()) {
			if (!queryString.isEmpty())
				queryString += "&";
			queryString += urlEncode(name) + "=" + urlEncode(params.get(name));
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

	private String urlEncode(String name) throws UnsupportedEncodingException {
	    return URLEncoder.encode(name, "UTF-8");
    }
}
