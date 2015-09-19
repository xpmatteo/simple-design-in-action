package it.xpug.woodysmart.main;

import it.xpug.toolkit.web.*;

import org.junit.*;

public class TestWithALiveServer {

	private static ReusableJettyApp app = new ReusableJettyApp(new WoodysMartServlet());

	@BeforeClass
    public static void startApplication() throws Exception {
    	app.start(8123, "src/main/webapp");
    }

	@AfterClass
    public static void shutdownApplication() throws Exception {
    	app.shutdown();
    }

}
