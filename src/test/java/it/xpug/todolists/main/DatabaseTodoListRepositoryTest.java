package it.xpug.todolists.main;

import static org.junit.Assert.*;

import org.junit.*;

public class DatabaseTodoListRepositoryTest {

	@Test
    public void initiallyEmpty() throws Exception {
	    TodoListRepository database = new DatabaseTodoListRepository();
		assertEquals("repo initially empty", 0, database.size());
    }
}
