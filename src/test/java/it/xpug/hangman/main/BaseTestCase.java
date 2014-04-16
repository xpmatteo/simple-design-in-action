package it.xpug.hangman.main;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

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

public class BaseTestCase {
	protected static final int APPLICATION_PORT = 8123;
	protected Map<String, String> params = new HashMap<String, String>();

	private HttpResponse response;

	protected void assertBody(String expectedBody) throws IllegalStateException, IOException {
		assertEquals("Body", expectedBody, getBodyAsString());
	}

	protected void assertBodyStartsWith(String string) throws IOException {
		assertThat(getBodyAsString(), startsWith(string));
	}

	protected void assertBodyContains(String expected) throws IOException {
		assertThat(getBodyAsString(), containsString(expected));
	}

	protected void assertContentType(String expectedMimeType) {
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

	protected void get(String path) throws Exception {
		URI url = new URI(baseUrl() + path + queryString());
		HttpGet request = new HttpGet(url);
		this.response = makeHttpClient().execute(request);
	}

	protected void post(String path) throws Exception {
		URI url = new URI(baseUrl() + path);
		HttpPost request = new HttpPost(url);
		addParameters(request);
		this.response = makeHttpClient().execute(request);
	}

	private String getBodyAsString() throws IOException {
		byte[] bytes = new byte[10000];
		response.getEntity().getContent().read(bytes);
		String body = new String(bytes, Charset.forName("UTF-8"));
		return body;
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

}
