package it.xpug.woodysmart.main.hello;

import static org.mockito.Mockito.*;
import it.xpug.woodysmart.main.*;

import javax.servlet.http.*;

import org.junit.*;

public class T0_HelloController_UnitTest {

	private HttpServletRequest request = mock(HttpServletRequest.class);
	private HelloView view = mock(HelloView.class);

	private HelloController helloController = new HelloController(request, view);

	@Test
	public void helloWorld() throws Exception {
		when(request.getMethod()).thenReturn("GET");
		when(request.getParameter("name")).thenReturn(null);

		helloController.service();

		verify(view).showHello("world");
	}

	@Test@Ignore
	public void helloWithNameParameter() throws Exception {
		when(request.getMethod()).thenReturn("GET");
		when(request.getParameter("name")).thenReturn("Pippo");

		helloController.service();

		verify(view).showHello("Pippo");
	}

	@Test@Ignore
	public void helloWithPostMethod() throws Exception {
		when(request.getMethod()).thenReturn("POST");

		helloController.service();

		verify(view).showMethodNotAllowed();
		verifyNoMoreInteractions(view);
	}

	/*
	 * Now start solving T1_HelloView_UnitTest !
	 */
}
