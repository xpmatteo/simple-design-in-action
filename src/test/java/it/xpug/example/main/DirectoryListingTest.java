package it.xpug.example.main;

import it.xpug.toolkit.web.*;

import org.junit.*;

public class DirectoryListingTest extends BaseTestCase {

	// Note: must configure the servlet to serve files in src/test/files

	@Test@Ignore
	public void returnsATextFile() throws Exception {
		get("/files/ciao.txt");
		assertStatus(200);
		assertContentType("text/plain");
		assertBody("ciao");
	}

	@Test@Ignore
	public void returnsAGraphicFile() throws Exception {
		get("/files/spinner.gif");
		assertStatus(200);
		assertContentType("image/gif");
		assertBodyStartsWith("GIF");
	}

	@Test@Ignore
	public void returnsDirectoryListing() throws Exception {
		get("/files/");
		assertStatus(200);
		assertContentType("text/html");
		String expected = "<html>\n" +
				"  <head>\n" +
				"    <title>Index of /files</title>\n" +
				"  </head>\n" +
				"  <body>\n" +
				"    <h1>Index of /files</h1>\n" +
				"    <table>\n" +
				"      <tr>\n" +
				"        <th>&nbsp;</th>\n" +
				"        <th>Name</th>\n" +
				"        <th>Size</th>\n" +
				"      </tr>\n" +
				"      <tr>\n" +
				"        <td><img src=\"/icons/text.gif\" alt=\"[TXT]\" /></td>\n" +
				"        <td><a href=\"ciao.txt\">ciao.txt</a></td>\n" +
				"        <td align=\"right\">5</td>\n" +
				"      </tr>\n" +
				"      <tr>\n" +
				"        <td valign=\"top\"><img src=\"/icons/image2.gif\" alt=\"[IMG]\" /></td>\n" +
				"        <td><a href=\"spinner.gif\">spinner.gif</a></td>\n" +
				"        <td align=\"right\">2037</td>\n" +
				"      </tr>\n" +
				"    </table>\n" +
				"  </body>\n" +
				"</html>\n" +
				"";
		assertBody(expected);
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
