package it.xpug.woodysmart.main.util;

import it.xpug.toolkit.web.*;

import java.io.*;


public class FakeHttpServletResponse extends NullServletResponse {

	private FakeWriter writer = FakeWriter.create();
	private int statusCode = 200;

	public int getStatusCode() {
	    return statusCode ;
    }

	@Override
	public PrintWriter getWriter() throws IOException {
	    return writer ;
	}

	@Override
	public void setStatus(int statusCode) {
	    this.statusCode = statusCode;
	}

	public String getBody() {
	    return writer.getWrittenText();
    }

}
