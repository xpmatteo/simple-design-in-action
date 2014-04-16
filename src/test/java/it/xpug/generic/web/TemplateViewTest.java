package it.xpug.generic.web;

import static org.junit.Assert.*;

import org.junit.*;

public class TemplateViewTest {

	@Test
	public void substitutesVariablesInTemplate() throws Exception {
		TemplateView view = new TemplateView("test.ftl", "src/test/freemarker");
		view.put("name", "Tizius");
	    assertEquals("Hello, Tizius!", view.toHtml());
	}
}
