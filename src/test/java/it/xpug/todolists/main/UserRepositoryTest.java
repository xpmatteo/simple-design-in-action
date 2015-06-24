package it.xpug.todolists.main;

import static org.junit.Assert.*;

import org.junit.*;

public class UserRepositoryTest {

	@Test
    public void addUser() throws Exception {
	    InMemoryUserRepository repository = new InMemoryUserRepository();
	    User user = new User("Matteo", "matteo@matteo.com");
		repository.add(user, "password");

	    assertEquals("wrong password", null, repository.authenticate("matteo@matteo.com", "wrong password"));
	    assertEquals("wrong email", null, repository.authenticate("wrong email", "password"));
	    assertEquals(user, repository.authenticate("matteo@matteo.com", "password"));
    }

}
