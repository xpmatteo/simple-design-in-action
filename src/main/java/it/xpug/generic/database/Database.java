package it.xpug.generic.database;


import java.io.*;
import java.sql.*;
import java.util.*;

import javax.servlet.*;

public class Database {
	
	private final Connection connection;

	public Database(Connection connection) {
		this.connection = connection;
	}
	
	public Database() {
		try {
			connection = getConnection(getConfiguration());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public RowsList select(String sql) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			RowsList result = new RowsList();
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();
			ResultSetMetaData metaData = resultSet.getMetaData();
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
		} finally {
			close(resultSet);
			close(statement);
		}
	}

	public void execute(String sql, Object... params) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql);

			for (int i = 0; i < params.length; i++) {
				statement.setObject(i + 1, params[i]);
			}
			statement.execute();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			close(statement);
		}
	}

	private void close(ResultSet resultSet) {
		if (null != resultSet) {
			try {
				resultSet.close();
			} catch (Exception ignored) {}
		}
	}

	private void close(Statement statement) {
		if (null != statement) {
			try {
				statement.close();
			} catch (Exception ignored) {}
		}
	}

	private Properties getConfiguration() throws IOException, ServletException {
		InputStream stream = this.getClass().getClassLoader().getResourceAsStream("/database.properties");
		if (null == stream) {
			throw new ServletException("cant't find database.properties");
		}
		Properties properties = new Properties();
		properties.load(stream);
		return properties;
	}

	private Connection getConnection(Properties properties) throws ClassNotFoundException, SQLException {
		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		String driver = properties.getProperty("jdbc_driver");
		Class.forName(driver);
		Connection connection = DriverManager.getConnection(url, username , password );
		connection.setAutoCommit(false);
		return connection;
	}
}
