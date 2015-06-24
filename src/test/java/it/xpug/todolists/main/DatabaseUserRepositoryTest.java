package it.xpug.todolists.main;

import static org.junit.Assert.*;
import it.xpug.toolkit.db.*;

import org.junit.*;

public class DatabaseUserRepositoryTest {

	public static final String TEST_DATABASE_URL = "postgres://todolists:secret@localhost:5432/todolists_test";
	private DatabaseConfiguration configuration = new DatabaseConfiguration(TEST_DATABASE_URL);
	private Database database = new Database(configuration);
	DatabaseUserRepository repository = new DatabaseUserRepository(database);

	@Test
    public void saveAUser() throws Exception {
		int newUserId = repository.add("password", new User("name", "email"));

		User found = repository.get(newUserId);
		assertEquals("name", found.getName());
		assertEquals("email", found.getEmail());
    }

	@Test
    public void authenticateAUser() throws Exception {
		repository.add("password", new User("name", "email"));

		User found = repository.authenticate("email", "password");
		assertEquals("name", found.getName());
		assertEquals("email", found.getEmail());

		assertNull("wrong password", repository.authenticate("email", "BAD PASSWORD"));
		assertNull("wrong email", repository.authenticate("WRONG EMAIL", "password"));
    }

}
