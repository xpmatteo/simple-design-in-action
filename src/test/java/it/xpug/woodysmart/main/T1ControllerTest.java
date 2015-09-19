package it.xpug.woodysmart.main;

import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.*;

public class T1ControllerTest {
	OrdersRepository repository = mock(OrdersRepository.class);
	OrdersView view = mock(OrdersView.class);
	OrdersController controller = new OrdersController(repository, view);

	@Test
    public void receiveOneOrder() throws Exception {
		Order order = new Order("1", "X", "ADDR");

		controller.receiveOrder(order);

		verify(repository).add(order);
    }

	@Test
    public void renderOrders() throws Exception {
		Order order1 = new Order("1", "X", "ADDR");
		Order order2 = new Order("2", "X", "ADDR");

		controller.render();

		verify(view).add(Arrays.asList(order1, order2));
		verify(view).render();
    }
}
