package it.xpug.toolkit.db;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConfiguration {

	private URI dbUri;

	public DatabaseConfiguration(String herokuDatabaseUrl) {
		try {
			this.dbUri = new URI(herokuDatabaseUrl);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	public Connection getConnection() {
		try {
		    String[] userInfo = dbUri.getUserInfo().split(":");
			Class.forName("org.postgresql.Driver");
		    return DriverManager.getConnection(jdbcUrl(), userInfo[0], userInfo[1]);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String jdbcUrl() {
		return String.format("jdbc:postgresql://%s:%s%s", dbUri.getHost(), dbUri.getPort(), dbUri.getPath());
	}

}
