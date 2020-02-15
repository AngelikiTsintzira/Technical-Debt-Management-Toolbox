package main.java.breakingPointTool.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SonarDatabaseConnection 
{
	private static final String SONAR_DRIVER = "";
	private static final String SONAR_URL = "";
	private static final String SONAR_USERNAME = "";
	private static final String SONAR_PASSWORD = "";	

	// Connection Driver for Java and mySQL
	private static Connection connection = null;

	public static Connection getConnection() 
	{
		if (connection != null) 
		{
			return connection;
		}
		return createConnection();
	}

	private static Connection createConnection() 
	{
		try {
			Class.forName(SONAR_DRIVER);
			connection = DriverManager.getConnection(SONAR_URL, SONAR_USERNAME, SONAR_PASSWORD);
			//connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
}
