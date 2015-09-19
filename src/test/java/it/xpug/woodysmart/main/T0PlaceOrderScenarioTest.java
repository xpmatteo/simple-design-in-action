package it.xpug.woodysmart.main;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import it.xpug.toolkit.web.*;

import java.io.*;

import javax.servlet.http.*;

import org.junit.*;

public class T0PlaceOrderScenarioTest {
	FakeWriter writer = FakeWriter.create();
	HttpServletRequest request = mock(HttpServletRequest.class);
	HttpServletResponse response = aMockResponse(writer);

	@Before
	public void setUpRequest() {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/orders/new");
	}

	@Test
    public void receiveAndShowOneOrder() throws Exception {
		when(request.getParameter("order_code")).thenReturn("6789");
		when(request.getParameter("article_code")).thenReturn("ABCD");
		when(request.getParameter("address")).thenReturn("123 Main St");

		new WoodysMartServlet().service(request, response);

		verify(response).setStatus(200);
// UNCOMMENT THESE ONE BY ONE
//		verify(response).setContentType("text/html");
//		assertThat(writer.getWrittenText(), containsString("Order Code: 6789"));
//		assertThat(writer.getWrittenText(), containsString("Article Code: ABCD"));
//		assertThat(writer.getWrittenText(), containsString("Address: 123 Main St"));
    }

	// UNIGNORE THIS AFTER THE OTHER ONE PASSES
	@Test@Ignore
    public void receiveAndShowAnotherOrder() throws Exception {
		when(request.getParameter("order_code")).thenReturn("7777");
		when(request.getParameter("article_code")).thenReturn("XYWZ");
		when(request.getParameter("address")).thenReturn("999 Upper Lower Middle");

		new WoodysMartServlet().service(request, response);

		assertThat(writer.getWrittenText(), containsString("Order Code: 7777"));
		assertThat(writer.getWrittenText(), containsString("Article Code: XYWZ"));
		assertThat(writer.getWrittenText(), containsString("Address: 999 Upper Lower Middle"));
    }

	@Test@Ignore
    public void receiveAndShowTwoOrders() throws Exception {
		when(request.getParameter("order_code")).thenReturn("1111");
		when(request.getParameter("article_code")).thenReturn("XXXX");
		when(request.getParameter("address")).thenReturn("FIRST ADDRESS");
		new WoodysMartServlet().service(request, response);

		when(request.getParameter("order_code")).thenReturn("2222");
		when(request.getParameter("article_code")).thenReturn("YYYY");
		when(request.getParameter("address")).thenReturn("SECOND ADDRESS");
		new WoodysMartServlet().service(request, response);

		assertThat(writer.getWrittenText(), containsString("Order Code: 1111"));
		assertThat(writer.getWrittenText(), containsString("Order Code: 2222"));
		assertThat(writer.getWrittenText(), containsString("Article Code: XXXX"));
		assertThat(writer.getWrittenText(), containsString("Article Code: YYYY"));
		assertThat(writer.getWrittenText(), containsString("Address: FIRST ADDRESS"));
		assertThat(writer.getWrittenText(), containsString("Address: SECOND ADDRESS"));
    }

	private HttpServletResponse aMockResponse(PrintWriter writer) {
	    try {
	    	HttpServletResponse response = mock(HttpServletResponse.class);
	        when(response.getWriter()).thenReturn(writer);
	        return response;
        } catch (IOException e) {
	        throw new RuntimeException(e);
        }
    }
}
