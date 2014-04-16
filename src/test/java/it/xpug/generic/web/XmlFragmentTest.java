package it.xpug.generic.web;

import static org.junit.Assert.*;
import it.xpug.generic.web.XmlFragment.ElementNotFoundException;

import java.util.*;

import org.junit.*;



public class XmlFragmentTest {
	
	@Test
	public void returnsTextContent() throws Exception {
		String xml = "<b><c>pippo</c></b>";
		XmlFragment document = new XmlFragment(xml);
		assertEquals("pippo", document.getTextContent());
		assertEquals("pippo", document.getChildren().get(0).getTextContent());
	}
	
	@Test
	public void returnsChildren() throws Exception {
		String xml = "<root><a/><b/><c/></root>";
		XmlFragment document = new XmlFragment(xml);
		List<XmlFragment> children = document.getChildren();
		assertEquals(3, children.size());
		assertEquals("a", children.get(0).getNodeName());
	}
	
	@Test
	public void ignoresWhitespaceNodes() throws Exception {
		String xml = "<a> <b/> </a>";
		XmlFragment document = new XmlFragment(xml);
		List<XmlFragment> children = document.getChildren();
		assertEquals(1, children.size());
		assertEquals("b", children.get(0).getNodeName());
	}
	
	@Test(timeout=100)
	public void doesNotTryToResolveEntities() throws Exception {
		String doctype =
				"<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">";
		String xml = "<a></a>";
		XmlFragment document = new XmlFragment(doctype+xml);
		assertEquals("a", document.getNodeName());
	}
	
	@Test
	public void treatsUndeclaredEntitiesAsText() throws Exception {
		new XmlFragment("<p> perch&eacute; </p>");
	}
	
	@Test
	public void equalityIsByStructure() throws Exception {
		XmlFragment a = new XmlFragment("<a><b><c>pippo</c></b></a>");
		XmlFragment equal = new XmlFragment("<a><b><c>pippo</c></b></a>");
		XmlFragment differentText = new XmlFragment("<a><b><c>pluto</c></b></a>");
		XmlFragment differentNodeName = new XmlFragment("<X><b><c>pippo</c></b></X>");
		XmlFragment differentStructure = new XmlFragment("<a><c>pluto</c></a>");
		
		assertEquals("same", a, a);
		assertEquals("equal", a, equal);
		assertEquals("equal", equal, a);
		assertFalse("differentNodeName", a.equals(differentNodeName));
		assertFalse("differentStructure", a.equals(differentStructure));
		assertFalse("differentText", a.equals(differentText));
	}
	
	@Test
	public void returnsSubDocument() throws Exception {
		XmlFragment parent = new XmlFragment("<a><b><c>pippo</c></b></a>");		
		XmlFragment child = parent.getNode("//b");
		assertEquals(child, new XmlFragment("<b><c>pippo</c></b>"));
	}

	@Test
	public void returnsNumberOfMatches() throws Exception {
		XmlFragment xmlDocument = new XmlFragment("<a><b><c>pippo</c></b></a>");
		assertEquals(0, xmlDocument.numberOfMatches("//foo"));
		assertEquals(1, xmlDocument.numberOfMatches("//b"));
	}
	
	@Test
	public void findsElementByXpath() throws Exception {
		XmlFragment xmlDocument = new XmlFragment("<a><b><c>pippo</c></b></a>");
		assertEquals("pippo", xmlDocument.getNode("//c").getTextContent());
	}
	
	@Test
	public void shouldThrowExceptionIfMoreNodesMatch() throws Exception {
        XmlFragment document = new XmlFragment(
        		"<a>" +
        		"  <b>foo</b>" +
        		"  <b>bar</b>" +
        		"</a>");
		assertEquals(2, document.numberOfMatches("//b"));
		try {
			document.getNode("//b");
			fail("should Throw an Exception If More Nodes Match");
		} catch (ElementNotFoundException ex) {
			assertEquals("\"//b\": expected 1 node, found 2", ex.getMessage());
		}
	}

	@Test
	public void shouldThrowNoNodesMatch() throws Exception {
        XmlFragment document = new XmlFragment("<a></a>");
		assertEquals(0, document.numberOfMatches("//xyzzy"));
		try {
			document.getNode("//xyzzy");
			fail("should throw");
		} catch (ElementNotFoundException ex) {
			assertEquals("\"//xyzzy\": expected 1 node, found 0", ex.getMessage());
		}
	}
	
	@Test
	public void returnsAttributeValue() throws Exception {
        XmlFragment document = new XmlFragment("<a href='ciao'></a>");
		assertEquals("ciao", document.getAttribute("href"));
	}

	
}
