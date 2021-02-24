package eu.sdk4ed.uom.td.analysis.database;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import eu.sdk4ed.uom.td.analysis.connection.DatabaseConnection;

public class DatabaseSaveDataC 
{
	public void saveBreakingPointInDatabase(String className, int versionNum, double breakingPoint, double principal, double interest, double k, double rate) throws SQLException
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "UPDATE cMetrics SET principal = ? , interest = ? , breaking_point = ? , frequency_of_change = ?, interest_probability = ?  WHERE class_name = ? AND version = ? ;";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setDouble(1, new BigDecimal(principal).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(2, new BigDecimal(interest).setScale(2, RoundingMode.HALF_UP).doubleValue() );
			pstm.setDouble(3, new BigDecimal(breakingPoint).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(4, new BigDecimal(k).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(5, new BigDecimal(rate).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setString(6, className);
			pstm.setDouble(7, versionNum);
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
			/*if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}*/
		}
	}

	public void saveMetricsInDatabase(String projectName, int versionNum, String className, String scope, double loc, double cyclomatic_complexity, double wmoc, double loc_per_loc, double coupling, double cohesion, String commitHash) throws SQLException, NumberFormatException, IOException
	{
		// Save metrics that calculated from metrics calculator tool in database
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "INSERT INTO cMetrics (class_name,project_name,scope,loc, cyclomatic_complexity, number_of_functions, comments_density, version, coupling, cohesion, commit_hash) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, className);
			pstm.setString(2, projectName);
			pstm.setString(3, scope);
			pstm.setDouble(4, new BigDecimal(loc).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(5, new BigDecimal(cyclomatic_complexity).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(6, new BigDecimal(wmoc).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(7, new BigDecimal(loc_per_loc).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(8, versionNum);
			pstm.setDouble(9, new BigDecimal(coupling).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setDouble(10, new BigDecimal(cohesion).setScale(2, RoundingMode.HALF_UP).doubleValue());
			pstm.setString(11, commitHash);
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
			/*if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}*/
		}

	}

	public void savePrincipalMetrics(String className, String projectName, int version, double td, double principal,
			double codeSmells, double bugs, double vulnerabilities, double duplications, String type, double classes, 
			double complexity, double functions, double nloc, double statements, double comments_density, String language) throws SQLException, NumberFormatException, IOException
	{
		// Save metrics that calculated from metrics calculator tool in database
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "INSERT INTO principalMetrics (class_name, project_name, scope, version, td_minutes, principal, code_smells, bugs,vulnerabilities, duplicated_lines_density, classes, complexity, functions, nloc, statements, comment_lines_density, language) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ON DUPLICATE KEY UPDATE class_name=VALUES(class_name)";
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
			/*if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}*/
		}

	}

	public void updatePrincipal(String className, int versionNum, double principal,  String projectName) throws SQLException
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "UPDATE principalMetrics SET principal = ? WHERE class_name = ? AND version = ? AND project_name = ?;";
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
			/*if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					Logger logger = Logger.getAnonymousLogger();
					logger.log(Level.SEVERE, "Exception was thrown: ", e);
				}
			}*/
		}
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

		String query = "DELETE FROM cMetrics WHERE project_name = ? and class_name = ?";
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
