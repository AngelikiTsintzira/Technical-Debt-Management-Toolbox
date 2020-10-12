package eu.sdk4ed.uom.td.analysis.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection 
{
	//"com.mysql.jdbc.Driver"
	private static final String databaseDriver = "com.mysql.cj.jdbc.Driver";
	private static String databaseUrl = "";
	private static String databaseUsername = "";
	private static String databasePassword = "";

	// Connection Driver for Java and mySQL
	private static Connection connection = null;

	public DatabaseConnection(String user, String pass, String sonar)
	{
		DatabaseConnection.databaseUsername = user;
		DatabaseConnection.databasePassword = pass;
		DatabaseConnection.databaseUrl = "jdbc:mysql://" + sonar + "?useSSL=false&autoReconnect=true";
	}

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
			Class.forName(databaseDriver);
			connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
			//connection = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public static void closeConnection() 
	{
		if (connection != null) 
		{
			try {
				connection.close();
			} catch (SQLException e) {
				Logger logger = Logger.getAnonymousLogger();
				logger.log(Level.SEVERE, "Exception was thrown: ", e);
			}
		}
	}
}
