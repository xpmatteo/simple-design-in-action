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
		Object actual = database.selectOneValue("select ?+?", 1, 2);

		// fix the assertion: what should we be expecting?
		assertEquals(0, actual);
    }

	// learning test: how to create tables in the database
	@Test@Ignore
    public void createTables() throws Exception {
		database.execute("create table foo ( a int )");
		database.execute("insert into foo (a) values (?);", 123);

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

	// learning test: how to use the rows in a recordset
	@Test@Ignore
    public void readManyRows() throws Exception {
		// create a table
		database.execute("create table numbers ( x int )");

		// insert 4 numbers
		database.execute("insert into numbers (x) values (1), (2), (3), (4);");

		// read back all the the rows
		ListOfRows rows = database.select("select * from numbers");
		int total = 0;
		for (int i=0; i<rows.size(); i++) {
	        total += (Integer) rows.get(i).get("x");
        }

		// fix the assertion: what value should we expect?
		assertEquals(0, total);
    }

	@Before
	public void recreateSchema() {
		database.execute("drop table if exists schema_info");
		database.execute("drop table if exists orders");

		// load production sql code
		database.execute("runscript from 'src/main/sql/000_init_schema_info.sql'");
		database.execute("runscript from 'src/main/sql/001_create_orders.sql'");
	}

	DatabaseOrdersList ordersList = new DatabaseOrdersList(database);

	@Test@Ignore
    public void addAndFindOneOrder() throws Exception {
		Order order = new Order("a", "b", "c");
		ordersList.add(order);

		List<Order> foundOrders = ordersList.all();

		assertEquals(1, foundOrders.size());
		assertEquals(order, foundOrders.get(0));
    }

	@Test@Ignore
    public void addAndFindTwoOrders() throws Exception {
		Order order0 = new Order("1", "2", "3");
		Order order1 = new Order("X", "Y", "Z");
		ordersList.add(order0);
		ordersList.add(order1);

		List<Order> foundOrders = ordersList.all();

		assertEquals(2, foundOrders.size());
		assertEquals(order0, foundOrders.get(0));
		assertEquals(order1, foundOrders.get(1));
    }

	@Test@Ignore
    public void changeAndUpdateOneOrderOnTheDatabase() throws Exception {
		Order order = new Order("a", "b", "c");
		ordersList.add(order);

		order.ship();
		ordersList.update(order);

		List<Order> foundOrders = ordersList.all();
		assertEquals("We still have just on order", 1, foundOrders.size());
		assertEquals("And now the order is shipped", true, foundOrders.get(0).isShipped());
    }
}
