package it.xpug.toolkit.db;

import java.sql.*;
import java.util.Map;
import java.util.Map.Entry;

public class Database {
	public static final String TEST_DATABASE_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private String databaseUrl;

	public Database(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public void execute(String sql, Object... params) {
		try (
				Connection connection = getConnection();
				PreparedStatement statement = prepareStatement(sql, connection, params);
			)
		{
			statement.execute();
			connection.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public ListOfRows select(String sql, Object... params) {
		try (
			Connection connection = getConnection();
			PreparedStatement statement = prepareStatement(sql, connection, params);
			ResultSet resultSet = statement.executeQuery();
			)
		{
			ResultSetMetaData metaData = resultSet.getMetaData();
			ListOfRows result = new ListOfRows();
			while (resultSet.next()) {
				result.newRow();
				for (int i=0; i<metaData.getColumnCount(); i++) {
					String columnName = metaData.getColumnName(i+1);
					result.put(columnName, resultSet.getObject(columnName));
				}
			}
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object selectOneValue(String sql, Object ... params) {
		ListOfRows rows = select(sql, params);
		Map<String, Object> map = rows.get(0);
		Entry<String, Object> entry = map.entrySet().iterator().next();
		return entry.getValue();
	}

	private PreparedStatement prepareStatement(String sql, Connection connection, Object... params) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(sql);
		for (int i = 0; i < params.length; i++) {
			statement.setObject(i + 1, params[i]);
		}
		return statement;
	}

	private Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");
		Connection connection = DriverManager.getConnection(databaseUrl);
//		connection.setAutoCommit(false);
		return connection;
	}
}
