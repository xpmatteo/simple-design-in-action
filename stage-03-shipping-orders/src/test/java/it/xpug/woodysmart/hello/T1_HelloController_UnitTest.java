package it.xpug.woodysmart.hello;

import static org.mockito.Mockito.*;

import javax.servlet.http.*;

import org.junit.*;

public class T1_HelloController_UnitTest {

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

	@Test
	public void helloWithNameParameter() throws Exception {
		when(request.getMethod()).thenReturn("GET");
		when(request.getParameter("name")).thenReturn("Pippo");

		helloController.service();

		verify(view).showHello("Pippo");
	}

	@Test
	public void helloWithPostMethod() throws Exception {
		when(request.getMethod()).thenReturn("POST");

		helloController.service();

		verify(view).showMethodNotAllowed();
		verifyNoMoreInteractions(view);
	}

	/*
	 * Now start solving T2_HelloView_UnitTest !
	 */
}
