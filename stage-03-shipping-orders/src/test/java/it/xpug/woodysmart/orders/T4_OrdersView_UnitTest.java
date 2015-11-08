package it.xpug.woodysmart.orders;

import static java.util.Arrays.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.*;

import it.xpug.woodysmart.util.*;

import org.junit.*;

public class T4_OrdersView_UnitTest {

	private FakeHttpServletResponse response = new FakeHttpServletResponse();
	private OrdersView ordersView = new OrdersView(response);

	@Test@Ignore
    public void showNoOrders() throws Exception {
		ordersView.show(emptyOrdersList());

		assertThat(response.getBody(), containsString("<p>Outstanding Orders: 0</p>"));
    }

	@Test@Ignore
    public void showOneOrder() throws Exception {
		ordersView.show(asList(new Order("A", "B", "C")));

		assertThat(response.getBody(), containsString("<p>Outstanding Orders: 1</p>"));
		assertThat(response.getBody(), containsString("<p>Order Code: A</p>"));
		assertThat(response.getBody(), containsString("<p>Article Code: B</p>"));
		assertThat(response.getBody(), containsString("<p>Address: C</p>"));
    }

	@Test@Ignore
    public void showTwoOrders() throws Exception {
		ordersView.show(asList(
				new Order("foo", "bar", "baz"),
				new Order("one", "two", "three")
				));

		assertThat(response.getBody(), containsString("Outstanding Orders: 2"));
		assertThat(response.getBody(), containsString("Order Code: foo"));
		assertThat(response.getBody(), containsString("Article Code: bar"));
		assertThat(response.getBody(), containsString("Address: baz"));
		assertThat(response.getBody(), containsString("Order Code: one"));
		assertThat(response.getBody(), containsString("Article Code: two"));
		assertThat(response.getBody(), containsString("Address: three"));
    }

	@Test@Ignore
    public void refresh() throws Exception {
		ordersView.refresh();

		// refresh should redirect the browser to the present page "."
		assertEquals(".", response.getRedirectedTo());
    }

	@Test@Ignore
    public void showTheShipButton() throws Exception {
		String expected =
				"  <form action='/orders/shipped' method='post'>\n" +
				"    <input type='hidden' name='order_code' value='88888' />\n" +
				"    <p><input type='submit' value='Shipped!' /></p>\n" +
				"  </form>\n";

		ordersView.show(asList(new Order("88888")));

		assertThat(response.getBody(), containsString(expected));
    }

	private List<Order> emptyOrdersList() {
	    return new ArrayList<Order>();
    }
}
