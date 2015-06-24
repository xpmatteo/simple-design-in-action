package it.xpug.todolists.main;

import java.io.*;

import javax.servlet.http.*;

public class NotAuthorized extends Resource {

	public NotAuthorized(HttpServletResponse response) {
	    super(null, response);
    }

	@Override
	public void service() throws IOException {
		respondWith(403, "Please authenticate");
	}

}
