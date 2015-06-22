package it.xpug.todolists.main;

import static org.junit.Assert.*;

import java.util.*;

import it.xpug.toolkit.db.*;

import org.junit.*;

public class DatabaseTodoListRepositoryTest {
	private DatabaseConfiguration configuration = new DatabaseConfiguration(DatabaseTest.TEST_DATABASE_URL);
	private Database database = new Database(configuration);
	private DatabaseTodoListRepository repository = new DatabaseTodoListRepository(database );

	@Before
	public void clearRepository() {
		repository.clear();
	}

	@Test
    public void initiallyEmpty() throws Exception {
		assertEquals("repo initially empty", 0, repository.size());
    }

	@Test
    public void insertOneElement() throws Exception {
	    repository.add(new TodoList("prova"));
		assertEquals("repo now contains 1", 1, repository.size());
    }

	@Test
    public void getOneElement() throws Exception {
	    TodoList todoList = new TodoList("prova");
		int id = repository.add(todoList);

		TodoList todoListFromRepo = repository.get(id);

		assertEquals("prova", todoListFromRepo.getName());
		assertEquals(id, todoListFromRepo.getId());
    }

	@Test
    public void notFound() throws Exception {
	    assertNull("not found", repository.get(9786987));
    }

	@Test
	public void allLists() throws Exception {
	    int id0 = repository.add(new TodoList("zero"));
	    int id1 = repository.add(new TodoList("one"));

	    List<TodoList> allLists = repository.getAll();

	    assertEquals(2, allLists.size());
	    assertEquals("zero", allLists.get(0).getName());
	    assertEquals(id0, allLists.get(0).getId());
	    assertEquals("one", allLists.get(1).getName());
	    assertEquals(id1, allLists.get(1).getId());
	}
}
