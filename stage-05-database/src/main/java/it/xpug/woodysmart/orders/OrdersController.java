package it.xpug.woodysmart.orders;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

public class OrdersController {

	private HttpServletRequest request;
	private OrdersList orders;
	private OrdersView view;

	public OrdersController(HttpServletRequest request, OrdersList orders, OrdersView view) {
		this.request = request;
		this.orders = orders;
		this.view = view;
    }

	public void service() throws IOException {
		if (request.getRequestURI().equals("/orders/shipped")) {
			String orderCode = request.getParameter("order_code");
			Order orderByCode = findOrder(orderCode);
			orderByCode.ship();
			view.refresh();
		} else if (request.getMethod().equals("POST")) {
			orders.add(orderFromRequest());
		} else {
			view.show(ordersNotShipped());
		}
    }

	private Order orderFromRequest() {
	    Order newOrder = new Order(
	    		request.getParameter("order_code"),
	    		request.getParameter("article_code"),
	    		request.getParameter("address"));
	    return newOrder;
    }

	private Order findOrder(String orderCode) {
	    Order orderByCode = null;
	    for (Order order : orders.all()) {
	    	if (order.getCode().equals(orderCode)) {
	    		orderByCode = order;
	    	}
	    }
	    return orderByCode;
    }

	private List<Order> ordersNotShipped() {
	    List<Order> notShipped = new ArrayList<Order>();
	    for (Order order : orders.all()) {
	    	if (!order.isShipped()) {
	    		notShipped.add(order);
	    	}
	    }
	    return notShipped;
    }
}
