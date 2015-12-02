package it.xpug.woodysmart.orders;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import it.xpug.woodysmart.util.*;

import org.junit.*;

public class T5_ReceiveAndShipOneOrder_AcceptanceTest extends TestWithALiveServer {

	@Test@Ignore
	public void noOrdersToBeShipped() throws Exception {
		get("/orders");

		assertThat(responseBody(), containsString("<p>Outstanding Orders: 0</p>"));
	}

	@Test@Ignore
    public void emptyOrdersPageLooksGood() throws Exception {
	    // Run the application, open the browser at http://localhost:8080/orders
		// It should looks like this:
		// +-----------------------------------------+
		// | Woody's Mart                            |
		// |                                         |
		// | Orders outstanding: 0                   |
		// |                                         |
		// +-----------------------------------------+
		// When it looks good, remove the following line and continue with the next test.
		fail("It does not look good");
    }

	@Test@Ignore
	public void shipAnOrderAndHideIt() throws Exception {
		// create the order
		params.put("order_code", "6789");
		params.put("article_code", "ABCD");
		params.put("address", "123 Main St.");
		post("/orders");

		// check what we see on the orders page
		get("/orders");
		assertThat(responseBody(), containsString("Outstanding Orders: 1"));
		assertThat(responseBody(), containsString("Order Code: 6789"));

		// declare the order shipped
		params.clear();
		params.put("order_code", "6789");
		post("/orders/shipped");

		// check what we see now on the order page
		get("/orders");
		assertThat(responseBody(), containsString("<p>Outstanding Orders: 0</p>"));
		assertThat(responseBody(), not(containsString("<p>Order Code: 6789</p>")));
	}

	@Test@Ignore
    public void manualTestOfTheBusinessCase() throws Exception {
	    // Run the application, open the browser at http://localhost:8080/enter-order.html
		// Then Click "Enter".
		//
		// It should looks more or less like this:
		// +-----------------------------------------+
		// | Woody's Mart                            |
		// |                                         |
		// | Outstanding Orders: 1                   |
		// |                                         |
		// | Order Code: 1024                        |
		// | Article Code: HR000                     |
		// | Address: Mr. Esteemed Customer, ...     |
		// |  +----------+                           |
		// |  | Shipped! |                           |
		// |  +----------+                           |
		// |                                         |
		// +-----------------------------------------+
		// When it looks good, remove the following line and continue with the next test.
		fail("It does not look good");

		// Now press the "Shipped!" button.  The page should now look like this:
		// +-----------------------------------------+
		// | Woody's Mart                            |
		// |                                         |
		// | Outstanding Orders: 0                   |
		// |                                         |
		// +-----------------------------------------+
		// If it looks OK, then remove the following line
		fail("It does not work yet");
    }

}
