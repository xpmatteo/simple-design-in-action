package it.xpug.woodysmart.orders;

import static java.util.Arrays.*;
import static java.util.Collections.*;
import static org.junit.Assert.*;
import it.xpug.toolkit.web.*;
import it.xpug.woodysmart.util.*;

import java.util.*;

import org.junit.*;

public class T4_OrdersView_UnitTest {

	private FakeHttpServletResponse response = new FakeHttpServletResponse();
	private OrdersView ordersView = new OrdersView(response);

	@Test@Ignore
    public void showNoOrders() throws Exception {
		ordersView.show(emptyList());

		// We are looking for the "summary" paragraph with XPath.
		// The //p[@id='summary'] means:
		//   Find a paragraph (p) with id = 'summary'
		String xpathForSummary = "//p[@id='summary']";

		// We find an html node that matches this XPath
		XmlNode node = getHtml().getNode(xpathForSummary);

		// We check its text content
		assertEquals("Outstanding Orders: 0", node.getTextContent());
    }

	@Test@Ignore
    public void showOneOrder() throws Exception {
		ordersView.show(asList(new Order("A", "B", "C")));

		String summary = "//p[@id='summary']";
		assertEquals("Outstanding Orders: 1", getHtml().getNode(summary).getTextContent());

		// Now we are looking for a div with class="shippable-order"
		String shippableOrderDiv = "//div[@class='shippable-order']";

		// that contains a paragraph with class="order-code".
		String orderCode = shippableOrderDiv + "//p[@class='order-code']";

		assertEquals("Order Code: A", getHtml().getNode(orderCode).getTextContent());

		// Etc.

		String articleCode = shippableOrderDiv + "//p[@class='article-code']";
		assertEquals("Article Code: B", getHtml().getNode(articleCode).getTextContent());

		String address = shippableOrderDiv + "//p[@class='address']";
		assertEquals("Address: C", getHtml().getNode(address).getTextContent());
    }

	@Test@Ignore
    public void showTwoOrders() throws Exception {
		ordersView.show(asList(
				new Order("foo", "bar", "baz"),
				new Order("one", "two", "three")
				));

		assertEquals("Outstanding Orders: 2", getHtml().getNode("//p[@id='summary']").getTextContent());
		List<XmlNode> nodes = getHtml().getNodes("//div[@class='shippable-order']/p[@class='order-code']");
		assertEquals("Order Code: foo", nodes.get(0).getTextContent());
		assertEquals("Order Code: one", nodes.get(1).getTextContent());
    }

	@Test@Ignore
    public void showTheShipButton() throws Exception {
		ordersView.show(asList(new Order("88888", "X", "Y")));

		// We assert there is a form that has a given action and method:
		String form = "//form[@action='/orders/shipped'][@method='post']";
		assertTrue("Form not found", getHtml().matchesXPath(form));

		// Then we want the form to contain a hidden field
		String hiddenField = form + "//input[@type='hidden'][@name='order_code'][@value='88888']";
		assertTrue("Hidden field not found", getHtml().matchesXPath(hiddenField));

		// And also a submit button with appropriate label
		String submitButton = form + "//input[@type='submit'][@value='Shipped!']";
		assertTrue("Submit button not found", getHtml().matchesXPath(submitButton));
    }

	@Test@Ignore
    public void refresh() throws Exception {
		ordersView.refresh();

		// refresh should redirect the browser to the present page "."
		assertEquals(".", response.getRedirectedTo());
    }

	/*
	 * If you passed all the tests here, you're awesome!
	 *
	 * What to do now:
	 *  a) un-ignore the T3_ReceiveAndShipOneOrder_AcceptanceTest and check that it works!
	 *  b) run the application and check that it works!
	 *  c) commit your changes (if you wish)
	 *  d) check out stage-04-database
	 */

	private XmlNode getHtml() {
	    return new XmlNode(response.getBody());
    }
}
