package it.xpug.todolists.main;

import static org.junit.Assert.*;
import it.xpug.toolkit.db.*;

import org.junit.*;

public class DatabaseTodoListRepositoryTest {
	private DatabaseConfiguration configuration = new DatabaseConfiguration(DatabaseTest.TEST_DATABASE_URL);
	private Database database = new Database(configuration);
	private TodoListRepository repository = new DatabaseTodoListRepository(database );

	@Test
    public void initiallyEmpty() throws Exception {
		assertEquals("repo initially empty", 0, repository.size());
    }

	@Test@Ignore
    public void insertOneElement() throws Exception {
	    repository.add(new TodoList("prova"));
		assertEquals("repo now contains 1", 1, repository.size());
    }


}
