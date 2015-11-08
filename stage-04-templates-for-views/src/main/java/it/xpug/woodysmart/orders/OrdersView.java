package it.xpug.woodysmart.orders;

import it.xpug.toolkit.web.*;

import java.io.*;
import java.util.*;

import javax.servlet.http.*;

public class OrdersView {

	private HttpServletResponse response;
	private TemplateView template = new TemplateView("orders.ftl");

	public OrdersView(HttpServletResponse response) {
		this.response = response;
    }

	public void show(List<Order> orders) throws IOException {
		template.put("orders", orders);
		response.getWriter().write(template.toHtml());
    }

	public void refresh() throws IOException {
		response.sendRedirect(".");
    }
}
