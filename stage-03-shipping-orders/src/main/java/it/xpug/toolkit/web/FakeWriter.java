package it.xpug.toolkit.web;

import java.io.*;

// A PrintWriter that can be used in testing servlets
public class FakeWriter extends PrintWriter {
	private StringWriter stringWriter;

	public static FakeWriter create() {
        try {
        	StringWriter stringWriter = new StringWriter();
        	FakeWriter fakeWriter = new FakeWriter(stringWriter);
        	return fakeWriter;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

	public String getWrittenText() {
	    return stringWriter.getBuffer().toString();
    }

	private FakeWriter(StringWriter stringWriter) throws FileNotFoundException {
        super(stringWriter);
        this.stringWriter = stringWriter;
    }


}