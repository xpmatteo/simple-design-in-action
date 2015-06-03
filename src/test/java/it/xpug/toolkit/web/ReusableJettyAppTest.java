package it.xpug.toolkit.web;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.*;
import java.net.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.junit.*;

public class ReusableJettyAppTest {

	private static ReusableJettyApp app = new ReusableJettyApp(new TestServlet());

	@Test
	public void servletIsInvokedOnRoot() throws Exception {
		assertThat(get("/"), is("Hello!"));
	}

	@Test
	public void servletIsInvokedOnAnyArbitraryPath() throws Exception {
		assertThat(get("/pippo"), is("Hello!"));
	}

	@Test
	public void staticResourcesAreReturned() throws Exception {
		assertThat(get("/zot.txt"), is("zot"));
	}

	@Test
	public void ifAnIndexIsPresentThenItIsReturned() throws Exception {
		FileWriter writer = new FileWriter("src/test/webapp/index.html");
		writer.write("Index here");
		writer.close();
		assertThat(get("/"), is("Index here"));
	}

	public static class TestServlet extends HttpServlet {
		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			response.getWriter().write("Hello!");
		}
	}

	@Before
	public void deleteIndex() throws Exception {
		new File("src/test/webapp/index.html").delete();
	}

	@BeforeClass
	public static void startTheApplication() throws Exception {
		app.start(8123, "src/test/webapp");
	}

	@AfterClass
	public static void shutdownTheApplication() throws Exception {
		app.shutdown();
	}

	private String get(String path) throws IOException {
		InputStream stream = new URL("http://localhost:" + 8123 + path).openStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		return reader.readLine();
	}
}
