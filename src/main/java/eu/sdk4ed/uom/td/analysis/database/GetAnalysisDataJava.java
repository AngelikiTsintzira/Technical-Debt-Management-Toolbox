package eu.sdk4ed.uom.td.analysis.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.sdk4ed.uom.td.analysis.artifact.ArtifactMetrics;
import eu.sdk4ed.uom.td.analysis.connection.DatabaseConnection;

// This class gets the analysis results and analyzes a new version instead of all software
public class GetAnalysisDataJava 
{
	ConcurrentHashMap<String, ArtifactMetrics> artifacts = new ConcurrentHashMap<String, ArtifactMetrics>();

	public GetAnalysisDataJava()
	{
		this.artifacts = new ConcurrentHashMap<String, ArtifactMetrics>();
	}

	public void getInterestMetricsJava(String projectName, String scope, int version) 
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String query;

		query = "SELECT * FROM javaMetrics WHERE project_name LIKE(?) and scope LIKE (?) and version LIKE (?)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, projectName);
			pstm.setString(2, scope);
			pstm.setInt(3, version);
			resultSet = pstm.executeQuery();
			while (resultSet.next()) 
			{
				ArtifactMetrics artifactObject = new ArtifactMetrics(projectName, resultSet.getString("class_name"), "Java", resultSet.getString("commit_hash"), resultSet.getInt("version"), resultSet.getString("scope"));
				artifactObject.metricsfromMetricsCalculator(resultSet.getDouble("mpc"), resultSet.getDouble("nom"), resultSet.getDouble("dit"), 
						resultSet.getDouble("nocc"), resultSet.getDouble("rfc"), resultSet.getDouble("lcom"), resultSet.getDouble("wmc_dec"), 
						resultSet.getDouble("dac"), resultSet.getDouble("loc"), resultSet.getDouble("number_of_properties"));
				artifactObject.setInterestProbability(resultSet.getDouble("interest_probability"));
				artifactObject.setAverageLOCChange(resultSet.getDouble("frequency_of_change"));

				artifacts.put(artifactObject.getArtifactName(), artifactObject);

			}

		} catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database select request failed. The project or the kee does not exist in the database."
					+ "Please try again!");
		} finally {
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

	public void getInterestMetricsC(String projectName, String scope, int version) 
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String query;

		query = "SELECT * FROM cMetrics WHERE project_name LIKE (?) and scope LIKE (?) and version = (?)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, projectName);
			pstm.setString(2, scope);
			pstm.setDouble(3, version);
			resultSet = pstm.executeQuery();
			while (resultSet.next()) 
			{		
				
				ArtifactMetrics artifactObject = new ArtifactMetrics(projectName, resultSet.getString("class_name"), "C", resultSet.getString("commit_hash"), resultSet.getInt("version"), resultSet.getString("scope"));
				artifactObject.setAverageLOCChange(resultSet.getDouble("frequency_of_change"));
				
				artifacts.put(artifactObject.getArtifactName(), artifactObject);	
			}

		} catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database select request failed. The project or the kee does not exist in the database."
					+ "Please try again!");
		} finally {
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

	public void getPrincipalMetrics(String projectName, String scope, int version) 
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String query;

		query = "SELECT * FROM principalMetrics WHERE project_name LIKE(?) and scope LIKE (?) and version LIKE (?)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, projectName);
			pstm.setString(2, scope);
			pstm.setInt(3, version);
			resultSet = pstm.executeQuery();

			while (resultSet.next()) 
			{

				ArtifactMetrics artifactObject = new ArtifactMetrics(projectName, resultSet.getString("class_name"), "", resultSet.getString("commit_hash"), resultSet.getInt("version"), resultSet.getString("scope"));
				artifactObject.setMetricsfromSonar(resultSet.getDouble("classes"), resultSet.getDouble("complexity"), resultSet.getDouble("functions"),
						resultSet.getDouble("nloc"), resultSet.getDouble("statements"), resultSet.getDouble("principal"), resultSet.getDouble("code_smells"), 
						resultSet.getDouble("bugs"), resultSet.getDouble("vulnerabilities"), resultSet.getDouble("duplicated_lines_density"), resultSet.getDouble("comment_lines_density")) ;

				artifacts.replace(artifactObject.getArtifactName(), artifactObject);

			}


		} catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database select request failed. The project or the kee does not exist in the database."
					+ "Please try again!");
		} finally {
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

	public ArrayList<ArtifactMetrics> getArtifactsMetrics()
	{
		//Getting Collection of values       
		Collection<ArtifactMetrics> values = artifacts.values(); 

		//Creating an ArrayList of values  
		ArrayList<ArtifactMetrics> clMetrics = new ArrayList<ArtifactMetrics>(values);

		return clMetrics;
	}

	public ConcurrentHashMap<String, ArtifactMetrics> getArtifacts()
	{        
		return artifacts;
	}

}
