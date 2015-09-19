package it.xpug.woodysmart.main;

import it.xpug.toolkit.web.*;

public class Main {
	public static void main(String[] args) {
		ReusableJettyApp app = new ReusableJettyApp(new WoodysMartServlet());
		app.start(8080, "src/main/webapp");
	}
}
