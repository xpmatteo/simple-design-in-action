package it.xpug.todolists.main;



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

public class TodoListsTest {

	private static List<TodoList> todoLists = new ArrayList<>();
	private static ReusableJettyApp app = new ReusableJettyApp(new TodoListsServlet(todoLists));

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
		todoLists.clear();
		params.clear();
    }

	@Test
	public void noTodoLists() throws Exception {
		get("/todolists");

		assertStatus(200);
		assertBody("{\"myLists\": []}");
	}

	@Test
    public void showListOfTodoLists() throws Exception {
		todoLists.add(new TodoList("e uno"));
		todoLists.add(new TodoList("e due"));

		get("/todolists");

		assertBody("{\"myLists\": ["
				+ "{\"name\": \"e uno\", \"uri\": \"/todolists/0\"},"
				+ "{\"name\": \"e due\", \"uri\": \"/todolists/1\"},"
				+ "]}");
    }

	@Test
	public void parameterNameRequiredForNewList() throws Exception {
		params.put("name", "");
		post("/todolists");

		assertStatus(400);
		assertBody("{\"status\":400, \"message\": \"Parameter 'name' is required\"}");
		assertEquals("No list has been created", 0, todoLists.size());
	}

	@Test
	public void createANewList() throws Exception {
		params.put("name", "New List");
		post("/todolists");

		assertStatus(302);
		assertHeader("location", "http://localhost:8123/todolists/0");

		assertEquals(1, todoLists.size());
		assertEquals("New List", todoLists.get(0).getName());
	}

	@Test
    public void createATodoItem() throws Exception {
		TodoList todoList = new TodoList("Nome lista");
		todoLists.add(todoList);

		params.put("text", "Compra il latte");
		post("/todolists/0/items");

		assertStatus(302);
		assertEquals("{\n" +
				"  \"name\": \"Nome lista\",\n" +
				"  \"items\": [{\n" +
				"    \"text\": \"Compra il latte\",\n" +
				"    \"status\": \"unchecked\"\n" +
				"  }]\n" +
				"}", todoList.toJson().toString(2));
    }

	@Test
    public void showListWithItems() throws Exception {
		TodoList list = new TodoList("Nome lista");
		list.addItem("Compra il latte");
		todoLists.add(list);

		get("/todolists/0");

		assertBody("{\n" +
				"  \"name\": \"Nome lista\",\n" +
				"  \"items\": [\n" +
				"    {\n" +
				"      \"text\": \"Compra il latte\",\n" +
				"      \"status\": \"unchecked\",\n" +
				"      \"uri\": \"/todolists/0/items/0\"\n" +
				"    }\n" +
				"  ]\n" +
				"}");
    }



	protected void assertBody(String expectedBody) throws IllegalStateException, IOException {
		assertHeader("content-type", "application/json; charset=ISO-8859-1");
		byte[] bytes = new byte[10000];
		int bytesRead = response.getEntity().getContent().read(bytes);
		String body = new String(bytes, 0, bytesRead, Charset.forName("UTF-8"));
		String expected = new JSONObject(expectedBody).toString(2);
		String actual = new JSONObject(body).toString(2);
		assertEquals("Body",  expected, actual);
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

	protected void get(String path, String authentication) throws IOException, URISyntaxException {
		URI url = new URI(baseUrl() + path + queryString());
		HttpGet request = new HttpGet(url);
		this.response = makeHttpClient().execute(request);
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
