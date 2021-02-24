package eu.sdk4ed.uom.td.analysis.database;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.sdk4ed.uom.td.analysis.artifact.ArtifactMetrics;
import eu.sdk4ed.uom.td.analysis.connection.DatabaseConnection;

public class DatabaseSaveData 
{

	public void saveMetricsInDatabase(ArtifactMetrics artifact) throws SQLException, NumberFormatException, IOException
	{
		// Save metrics that calculated from metrics calculator tool in database

		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "INSERT INTO javaMetrics (class_name,project_name,scope,wmc,dit,cbo,rfc,lcom,wmc_dec,nocc,mpc,dac,loc,number_of_properties,dsc,noh,ana,dam,dcc,camc,moa,mfa,nop,cis,nom," + 
				"reusability,flexibility,understandability,functionality,extendibility,effectiveness,fanIn,commit_hash,version,rem,cpm) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE class_name=VALUES(class_name)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, artifact.getArtifactName());
			pstm.setString(2, artifact.getProjectName());
			pstm.setString(3, artifact.getArtifactType());
			pstm.setDouble(4, new BigDecimal(artifact.getNom()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(5, new BigDecimal(artifact.getDit()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(6, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(7, new BigDecimal(artifact.getRfc()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(8, new BigDecimal(artifact.getLcom()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(9, new BigDecimal(artifact.getWmpc()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(10, new BigDecimal(artifact.getNocc()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(11, new BigDecimal(artifact.getMpc()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(12, new BigDecimal(artifact.getDac()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(13, new BigDecimal(artifact.getLoc()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(14, new BigDecimal(artifact.getNop()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(15, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(16, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(17, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(18, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(19, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(20, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(21, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(22, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(23, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(24, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(25, new BigDecimal(artifact.getNom()).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(26, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(27, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(28, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(29, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());		
			pstm.setDouble(30, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(31, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(32, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setString(33, artifact.getCommitSha());
			pstm.setInt(34, artifact.getVersion());
			pstm.setDouble(35, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(36, new BigDecimal(0.0).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.executeUpdate();

		} catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database request failed. Please try again!");
		} 
		finally {
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

	public void saveBreakingPointInDatabase(String className, int versionNum, double breakingPoint, double principal, double interest, double k, double rate, String projectName) throws SQLException
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "UPDATE javaMetrics SET principal = ? , interest = ? , breakingpoint = ? , frequency_of_change = ?, interest_probability = ?  WHERE class_name = ? AND version = ? and project_name = ? ;";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setDouble(1, new BigDecimal(principal).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(2, new BigDecimal(interest).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(3, new BigDecimal(breakingPoint).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(4, new BigDecimal(k).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(5, new BigDecimal(rate).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setString(6, className);
			pstm.setDouble(7, versionNum);
			pstm.setString(8, projectName);
			pstm.executeUpdate();
			

		} catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database request failed. Please try again!");
		} 
		finally {
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

	public void savePrincipalMetrics(String className, String projectName, int version, double td, double principal,
			double codeSmells, double bugs, double vulnerabilities, double duplications, String type, double classes, 
			double complexity, double functions, double nloc, double statements, double comments_density, String language) throws SQLException, NumberFormatException, IOException
	{
		// Save metrics that calculated from metrics calculator tool in database

		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "INSERT INTO principalMetrics (class_name, project_name, scope, version, td_minutes, principal, code_smells, bugs," + 
				"vulnerabilities, duplicated_lines_density, classes, complexity, functions, nloc, statements, comment_lines_density, language) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE class_name=VALUES(class_name)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, className);
			pstm.setString(2, projectName);
			pstm.setString(3, type);
			pstm.setDouble(4, version);
			pstm.setDouble(5, new BigDecimal(td).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(6, new BigDecimal(principal).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setInt(7, (int) codeSmells);
			pstm.setInt(8, (int) bugs);
			pstm.setInt(9, (int) vulnerabilities);
			pstm.setDouble(10, new BigDecimal(duplications).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(11, new BigDecimal(classes).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(12, new BigDecimal(complexity).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(13, new BigDecimal(functions).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(14, new BigDecimal(nloc).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(15, new BigDecimal(statements).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(16, new BigDecimal(comments_density).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setString(17, language);
			pstm.executeUpdate();

		} catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database request failed. Please try again!");
		} 
		finally {
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

	public void updatePrincipal(String className, int versionNum, double principal, String projectName) throws SQLException
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "UPDATE principalMetrics SET principal = ? WHERE class_name = ? AND version = ? and project_name = ?;";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setDouble(1, new BigDecimal(principal).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setString(2, className);
			pstm.setDouble(3, versionNum);
			pstm.setString(4, projectName);
			pstm.executeUpdate();

		} catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database request failed. Please try again!");
		} 
		finally {
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

	public boolean saveTimestamp(String projectName, int versionNum) throws SQLException
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "INSERT INTO executionTimestap (project_name, version) VALUES (?,?)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, projectName);
			pstm.setInt(2, versionNum);
			pstm.executeUpdate();

		}  
		catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database request failed. Please try again!");
		} 
		finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}
		}
		return true;
	}

	public boolean deleteTimestamp(String projectName, int versionNum) throws SQLException
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "DELETE FROM executionTimestap WHERE project_name = ? and version = ?";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, projectName);
			pstm.setInt(2, versionNum);
			pstm.executeUpdate();

		}  
		catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database request failed. Please try again!");
		} 
		finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}
		}
		return true;
	}
	
	public boolean deleteInstancesPrincipal(String projectName, String className) throws SQLException
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "DELETE FROM principalMetrics WHERE project_name = ? and class_name = ?";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, projectName);
			pstm.setString(2, className);
			pstm.executeUpdate();

		}  
		catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database request failed. Please try again!");
		} 
		finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}
		}
		return true;
	}
	
	public boolean deleteInstancesInterest(String projectName, String className) throws SQLException
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "DELETE FROM javaMetrics WHERE project_name = ? and class_name = ?";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, projectName);
			pstm.setString(2, className);
			pstm.executeUpdate();

		}  
		catch (SQLException ex) {
			Logger logger = Logger.getAnonymousLogger();
			logger.log(Level.SEVERE, "Exception was thrown: ", ex);
			System.out.println("Database request failed. Please try again!");
		} 
		finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}
		}
		return true;
	}
	
}
