package it.xpug.woodysmart.orders;

import static java.util.Arrays.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;

import javax.servlet.http.*;

import org.junit.*;

public class T3_ReceiveAndShipOrdersTest {

	private HttpServletRequest request = mock(HttpServletRequest.class);
	private OrdersView ordersView = mock(OrdersView.class);
	private List<Order> orders = new ArrayList<Order>();
	private OrdersController ordersController = new OrdersController(request, orders, ordersView);

	@Test
	public void receiveAnOrder() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/orders");
		when(request.getParameter("order_code")).thenReturn("1234");
		when(request.getParameter("article_code")).thenReturn("ABCD");
		when(request.getParameter("address")).thenReturn("Some Place");

		ordersController.service();

		assertEquals(1, orders.size());
		assertEquals(new Order("1234", "ABCD", "Some Place"), orders.get(0));
	}

	@Test@Ignore
	public void showAllNonShippedOrders() throws Exception {
		orders.add(new Order("a", "b", "c"));
		orders.add(new Order("d", "e", "f"));

		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn("/orders");

		ordersController.service();

		verify(ordersView).show(orders);
	}

	@Test@Ignore
    public void anOrderCanBeShipped() throws Exception {
	    Order order = new Order("x", "y", "z");
	    assertEquals(false, order.isShipped());

	    order.ship();

	    assertEquals("order should be shipped", true, order.isShipped());
    }

	@Test@Ignore
    public void theControllerWillShipAnOrder() throws Exception {
		Order order = new Order("5555", "_", "_");
		orders.add(order);

		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/orders/shipped");
		when(request.getParameter("order_code")).thenReturn("5555");

		ordersController.service();

		assertEquals("controller should set shipped", true, order.isShipped());
		verify(ordersView).refresh();
    }

	@Test@Ignore
    public void shippedOrdersAreNotShown() throws Exception {
		Order shipped = new Order("X");
		Order notShipped = new Order("Y");
		orders.addAll(asList(shipped, notShipped));
		shipped.ship();

		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn("/orders");

		ordersController.service();

		verify(ordersView).show(asList(notShipped));
	}
}
