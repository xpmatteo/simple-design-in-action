package it.xpug.todolists.main;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

public class InMemorySessionRepositoryTest {

	@Test
    public void createNewSession() throws Exception {
	    Random random = new Random(1234);
		InMemorySessionRepository repository = new InMemorySessionRepository(random);
		assertEquals("5a799757bd7e682e", repository.newSessionId());
    }
}
