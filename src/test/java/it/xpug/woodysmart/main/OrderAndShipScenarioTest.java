package it.xpug.woodysmart.main;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import it.xpug.toolkit.web.*;

import java.io.*;

import javax.servlet.http.*;

import org.junit.*;

public class OrderAndShipScenarioTest {
	FakeWriter writer = FakeWriter.create();
	HttpServletRequest request = mock(HttpServletRequest.class);
	HttpServletResponse response = aMockResponse();


	@Test
    public void receiveAndShowOneOrder() throws Exception {
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn("/orders/new");
		when(request.getParameter("article_code")).thenReturn("ABCD");
		when(request.getParameter("order_code")).thenReturn("6789");
		when(request.getParameter("address")).thenReturn("123 Main St");

		new WoodysMartServlet().service(request, response);

		verify(response).setStatus(200);
		verify(response).setContentType("text/html");
		assertThat(writer.getWrittenText(), containsString("Order Code: 6789"));
		assertThat(writer.getWrittenText(), containsString("Article Code: ABCD"));
		assertThat(writer.getWrittenText(), containsString("Address: 123 Main St"));
    }

	private HttpServletResponse aMockResponse() {
	    try {
	    	HttpServletResponse response = mock(HttpServletResponse.class);
	        when(response.getWriter()).thenReturn(writer);
	        return response;
        } catch (IOException e) {
	        throw new RuntimeException(e);
        }
    }
}
