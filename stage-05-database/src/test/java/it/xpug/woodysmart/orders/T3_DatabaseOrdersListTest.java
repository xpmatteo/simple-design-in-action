package it.xpug.woodysmart.orders;

import static org.junit.Assert.*;
import it.xpug.toolkit.db.*;

import java.util.*;

import org.junit.*;


public class T3_DatabaseOrdersListTest {

	@Test
    public void addAndFindOneOrder() throws Exception {
		Database database = new Database(Database.IN_MEMORY_DATABASE_URL);
		DatabaseOrdersList ordersList = new DatabaseOrdersList(database);
		Order order = new Order("a", "b", "c");
		ordersList.add(order);

		Collection<Order> foundOrders = ordersList.all();

		assertEquals(1, foundOrders.size());
		assertEquals(order, foundOrders.iterator().next());
    }

}
