package main.java.breakingPointTool.preperation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import main.java.breakingPointTool.connection.DatabaseConnection;

public class ConfigurationFile 
{
	private String fileName;
	private String usernameDBConnection;
	private String passwordDBConnection;
	private String serverName ;
	private String dbName ;
	private String sonar_url;
	private String sonarqube_execution;
	private String usernameSQConnection;
	private String passwordSQConnection;


	public ConfigurationFile ()
	{
		this.fileName = System.getProperty("user.dir") + "/configurations.txt";
		this.usernameDBConnection = null;
		this.passwordDBConnection = null;
		this.serverName = null;
		this.dbName = null;
		this.sonar_url = null;
		this.usernameSQConnection = null;
		this.passwordSQConnection = null;
	}

	public boolean readConfigurationFile() throws IOException
	{
		String line;

		if (new File(fileName).exists()) 
		{ 
			BufferedReader br = new BufferedReader(new FileReader(fileName));    

			while ((line = br.readLine()) != null)
			{ 
				if (line.contains("#")) 
					continue;

				//  Database Credentials
				if (line.contains("username=")) 
				{ 
					String[] temp = line.split("username="); 
					usernameDBConnection = temp[1]; 
				}

				if (line.contains("password=")) 
				{  
					String[] temp = line.split("password="); 
					passwordDBConnection = temp[1];
				} 

				//  Metrics DB credentials
				if (line.contains("serverName="))
				{
					String[] temp = line.split("serverName=");
					serverName = temp[1];
				}

				if (line.contains("databaseName="))
				{
					String[] temp = line.split("databaseName=");
					dbName = temp[1];
				}
				
				if (line.contains("sonar_url="))
				{
					String[] temp = line.split("sonar_url=");
					sonar_url = temp[1];
				}	
				
				if (line.contains("sonarqube_execution="))
				{
					String[] temp = line.split("sonarqube_execution=");
					sonarqube_execution = temp[1];
				}
				
				if (line.contains("usernameSQ="))
				{
					String[] temp = line.split("usernameSQ=");
					usernameSQConnection = temp[1];
				}
				
				if (line.contains("passwordSQ="))
				{
					String[] temp = line.split("passwordSQ=");
					passwordSQConnection = temp[1];
				}
			}
			
			br.close();

			// Set Credentials to Database
			new DatabaseConnection(usernameDBConnection, passwordDBConnection, serverName + "/" +  dbName);

			return true;
		}
		return false;
	} 

	public boolean clodeConnection()
	{
		DatabaseConnection.closeConnection();
		return true;
	}
	
	public String getSonarUrl()
	{
		return this.sonar_url;
	}
	
	public String getSonarQubeExec()
	{
		return this.sonarqube_execution;
	}
	
	public String getSQUsername()
	{
		return this.usernameSQConnection;
	}
	
	public String getSQPassword()
	{
		return this.passwordSQConnection;
	}

}
