package it.xpug.woodysmart.orders;

import static java.util.Arrays.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import it.xpug.toolkit.db.*;

import javax.servlet.http.*;

import org.junit.*;

public class T4_ReceiveAndShipOrdersTest {

	private HttpServletRequest request = mock(HttpServletRequest.class);
	private OrdersView ordersView = mock(OrdersView.class);
	private DatabaseOrdersList orders = new DatabaseOrdersList(new Database(Database.IN_MEMORY_DATABASE_URL));
	private OrdersController ordersController = new OrdersController(request, orders, ordersView);

	@Test@Ignore
	public void receiveAnOrder() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/orders");
		when(request.getParameter("order_code")).thenReturn("1234");
		when(request.getParameter("article_code")).thenReturn("ABCD");
		when(request.getParameter("address")).thenReturn("Some Place");

		ordersController.service();

		assertEquals(1, orders.all().size());
		assertEquals(new Order("1234", "ABCD", "Some Place"), orders.all().iterator().next());
	}

	@Test@Ignore
	public void showAllNonShippedOrders() throws Exception {
		Order order0 = new Order("a", "b", "c");
		Order order1 = new Order("d", "e", "f");
		orders.add(order0);
		orders.add(order1);

		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn("/orders");

		ordersController.service();

		verify(ordersView).show(asList(order0, order1));
	}

	@Test
    public void anOrderCanBeShipped() throws Exception {
	    Order order = new Order("x", "y", "z");
	    assertEquals(false, order.isShipped());

	    order.ship();

	    assertEquals("order should be shipped", true, order.isShipped());
    }

	@Test
    public void anOrderReturnsItsCode() throws Exception {
	    assertEquals("xyz", new Order("xyz", null, null).getCode());
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
		orders.add(shipped);
		orders.add(notShipped);
		shipped.ship();

		when(request.getMethod()).thenReturn("GET");
		when(request.getRequestURI()).thenReturn("/orders");

		ordersController.service();

		verify(ordersView).show(asList(notShipped));
	}
}
