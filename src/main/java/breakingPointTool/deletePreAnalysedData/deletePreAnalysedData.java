package main.java.breakingPointTool.deletePreAnalysedData;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.java.breakingPointTool.connection.DatabaseConnection;

public class deletePreAnalysedData 
{
	public deletePreAnalysedData() {}

	// check for phase 2 if this versions is analysed, if it is, deleted first
	public void DeleteFromDBAlreadyAnalysedVersion(String projectName, String language, int version)
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String query;

		query = "SELECT * FROM principalMetrics WHERE project_name = (?) and version = (?)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, projectName);
			pstm.setInt(2, version);
			resultSet = pstm.executeQuery();

			// If there are rows, delete them all
			if(resultSet.next())
			{
				//Delete Principal Metrics
				query = "DELETE FROM principalMetrics WHERE project_name = (?) and version = (?)";
				try 
				{
					//Close previous connect and start new
					pstm.close();
					pstm = conn.prepareStatement(query);
					pstm.setString(1, projectName);
					pstm.setInt(2, version);
					int rowCount = pstm.executeUpdate();

					System.out.println("Record Deleted successfully from database. Row Count returned is :: "
							+ rowCount);

				} catch (SQLException e) {
					System.out
					.println("An exception occured while deleting data from database. Exception is :: "
							+ e);
				}

				String table = "";
				if (language.equals("Java"))
				{
					table = "javaMetrics";
				}
				else
				{
					table = "cMetrics";
				}

				//Delete Interest Metrics
				query = "DELETE FROM " + table + " WHERE project_name = (?) and version = (?)";
				try 
				{
					//Close previous connect and start new
					pstm.close();
					pstm = conn.prepareStatement(query);
					pstm.setString(1, projectName);
					pstm.setInt(2, version);
					int rowCount = pstm.executeUpdate();

					System.out.println("Record Deleted successfully from database. Row Count returned is :: "
							+ rowCount);

				} catch (SQLException e) {
					System.out
					.println("An exception occured while deleting data from database. Exception is :: "
							+ e);
				}
			}

		} catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database select coupling-cohesion request failed."
					+ "Please try again!");
		} finally 
		{
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}
		}
	}

	// Delete already analysed project from DB
	public void DeleteFromDBAlreadyAnalysedProject(String projectName, String language)
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String query;

		query = "SELECT * FROM principalMetrics WHERE project_name = (?) ";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, projectName);
			resultSet = pstm.executeQuery();

			// If there are rows, delete them all
			if(resultSet.next())
			{
				//Delete Principal Metrics
				query = "DELETE FROM principalMetrics WHERE project_name = (?) ";
				try 
				{
					//Close previous connect and start new
					pstm.close();
					pstm = conn.prepareStatement(query);
					pstm.setString(1, projectName);
					int rowCount = pstm.executeUpdate();

					System.out.println("Record Deleted successfully from database. Row Count returned is :: "
							+ rowCount);

				} catch (SQLException e) {
					System.out
					.println("An exception occured while deleting data from database. Exception is :: "
							+ e);
				}

				String table = "";
				if (language.equals("Java"))
				{
					table = "javaMetrics";
				}
				else
				{
					table = "cMetrics";
				}

				//Delete Interest Metrics
				query = "DELETE FROM " + table + " WHERE project_name = (?) ";
				try 
				{
					//Close previous connect and start new
					pstm.close();
					pstm = conn.prepareStatement(query);
					pstm.setString(1, projectName);
					int rowCount = pstm.executeUpdate();

					System.out.println("Record Deleted successfully from database. Row Count returned is :: "
							+ rowCount);

				} catch (SQLException e) {
					System.out
					.println("An exception occured while deleting data from database. Exception is :: "
							+ e);
				}
			}

		} catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database select coupling-cohesion request failed."
					+ "Please try again!");
		} finally 
		{
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}
		}
	}

	// Delete all files from external tools
	public void deleteFile(int versions)
	{	
		String file;
		for (int i = 0; i < versions; i++)
		{
			file = "output" + i + ".csv";
			delete(file);
		}

		delete("output.csv");
		delete("metrics.txt");
		delete("rem_and_cpm_metrics_classLevel.csv");
		delete("rem_and_cpm_metrics_packageLevel.csv");
	}

	public void delete(String file)
	{
		try
		{ 
			Files.deleteIfExists(Paths.get(file)); 
		} 
		catch(NoSuchFileException e) 
		{ 
			System.out.println("No such file/directory exists"); 
		} 
		catch(DirectoryNotEmptyException e) 
		{ 
			System.out.println("Directory is not empty."); 
		} 
		catch(IOException e) 
		{ 
			System.out.println("Invalid permissions."); 
		} 

		System.out.println("Deletion successful."); 
	}


	// Delete source codes cloned from github
	public void deleteSourceCode(File file) throws IOException {
		boolean result = true;

		if (file.isDirectory()) 
		{
			// If directory is empty, then delete it
			if (file.list().length == 0) 
			{
				result = file.delete();
				if (result)
				{
					//System.out.println("File is deleted : " + file.getAbsolutePath());
				}
				else
					System.out.println("Error with the deletion of file");
			} 
			else 
			{
				// List all the directory contents
				String[] files = file.list();

				for (String temp : files) 
				{
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					deleteSourceCode(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) 
				{
					result = file.delete();

					if (result)
					{
						//System.out.println("File is deleted : " + file.getAbsolutePath());
					}
					else
						System.out.println("Error with the deletion of file");
				}
			}

		} 
		else 
		{
			// if file, then delete it
			result = file.delete();

			if (result)
			{
				//System.out.println("File is deleted : " + file.getAbsolutePath());
			}
			else
				System.out.println("Error with the deletion of file");
		}
	}


}
