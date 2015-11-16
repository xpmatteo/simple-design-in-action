package it.xpug.woodysmart.orders;

import static org.junit.Assert.*;
import it.xpug.toolkit.db.*;

import java.util.*;

import org.junit.*;


public class T3_DatabaseOrdersListTest {
	Database database = new Database(Database.TEST_DATABASE_URL);

	// learning test: how to select one value from the database
	@Test
    public void selectOneValue() throws Exception {
		Object actual = database.selectOneValue("select 1+2");

		// fix the assertion: what should we be expecting?
		assertEquals(0, actual);
    }

	// learning test: how to create tables in the database
	@Test@Ignore
    public void createTables() throws Exception {
		database.execute("create table foo ( a int )");
		database.execute("insert into foo (a) values (123);");

		// fix the assertion: what should we be expecting?
		assertEquals(0, database.selectOneValue("select a from foo"));
    }

	// learning test: how to load a SQL script from the file system
	@Test@Ignore
    public void loadScriptFromFile() throws Exception {
		// Create an appropriate definition for table "bar" in src/test/sql/000_create_bar.sql
		database.execute("runscript from 'src/test/sql/000_create_bar.sql'");
		database.execute("insert into bar (b) values ('blah');");

		assertEquals("blah", database.selectOneValue("select b from bar"));
    }

	@Test@Ignore
    public void addAndFindOneOrder() throws Exception {
		// load production sql code
		database.execute("runscript from 'src/main/sql/000_init_schema_info.sql'");
		database.execute("runscript from 'src/main/sql/001_create_orders.sql'");

		DatabaseOrdersList ordersList = new DatabaseOrdersList(database);
		Order order = new Order("a", "b", "c");
		ordersList.add(order);

		Collection<Order> foundOrders = ordersList.all();

		assertEquals(1, foundOrders.size());
		assertEquals(order, foundOrders.iterator().next());
    }

}
