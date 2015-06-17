package it.xpug.toolkit.db;

import static org.junit.Assert.*;

import java.sql.*;

import org.junit.*;


public class DatabaseTest {

	private static final String TEST_DATABASE_URL = "postgres://todolists:secret@localhost:5432/todolists_test";
	private DatabaseConfiguration configuration;
	private Database database;

	@Before
	public void setUp() throws Exception {
		configuration = new DatabaseConfiguration(TEST_DATABASE_URL);
		database = new Database(configuration);
		database.execute("drop table if exists prova");
		database.execute("create table prova( id serial, name varchar(255) );");
	}

	@Test
	public void configurationReturnsAConnection() throws Exception {
		Connection connection = configuration.getConnection();
		assertNotNull(connection);
	}

	@Test
	public void testSelectSum() throws Exception {
		String sql = "select ? + ?";
		assertEquals(7, database.selectOneValue(sql, 3, 4));
	}

	@Test(expected=RuntimeException.class)
	public void selectNonExistentValue() throws Exception {
		String sql = "select * from prova";
		database.selectOneValue(sql);
	}

	@Test
	public void testSelectOneFromTable() throws Exception {
		database.execute("insert into prova (name) values (?);", "pippo");
		assertEquals("pippo", database.selectOneValue("select name from prova"));
	}

	@Test
	public void testSelectAllFromTable() throws Exception {
		database.execute("insert into prova (name) values (?);", "pluto");
		ListOfRows rows = database.select("select * from prova where name like ?", "pl%");
		assertEquals(1, rows.size());
	}

}
