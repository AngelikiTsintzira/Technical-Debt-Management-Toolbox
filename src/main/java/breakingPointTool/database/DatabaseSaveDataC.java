package main.java.breakingPointTool.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.jdbc.Statement;

import main.java.breakingPointTool.connection.DatabaseConnection;

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
			pstm.setDouble(1, principal);
			pstm.setDouble(2, interest);
			pstm.setDouble(3, breakingPoint);
			pstm.setFloat(4, (float) k);
			pstm.setFloat(5, (float) rate);
			pstm.setString(6, className);
			pstm.setDouble(7, versionNum);
			pstm.executeUpdate();
			
			//conn.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Database request failed. Please try again!");
		} 
		//System.out.println("Calculated Breaking Point - Interest - Pricipal saved in database successfully!");
	}

	public void saveMetricsInDatabase(String projectName, int versionNum, String className, String scope, double loc, double cyclomatic_complexity, double wmoc, double loc_per_loc) throws SQLException, NumberFormatException, IOException
	{
		// Save metrics that calculated from metrics calculator tool in database

		Statement stmt = null;
		Connection conn = DatabaseConnection.getConnection();

		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "INSERT INTO cMetrics (class_name,project_name,scope,loc, cyclomatic_complexity, number_of_functions, comments_density, version)  VALUES('"
				+ className + "'," + "'" + projectName + "'," + "'" + scope + "'," + "" + loc + "," + "" + cyclomatic_complexity + "," + "" + wmoc
				+ "," + "" + loc_per_loc + "," + ""  + versionNum + ") ON DUPLICATE KEY UPDATE class_name = '" + className
				+ "';";
		stmt.executeUpdate(query);

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//System.out.println("Calculated Metrics saved in database successfully!");
	}

	public void savePrincipalMetrics(String className, String projectName, int version, double td, double principal,
			double codeSmells, double bugs, double vulnerabilities, double duplications, String type, double classes, 
			double complexity, double functions, double nloc, double statements, double comments_density, String language) throws SQLException, NumberFormatException, IOException
	{
		// Save metrics that calculated from metrics calculator tool in database

		Statement stmt = null;
		Connection conn = DatabaseConnection.getConnection();

		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "INSERT INTO principalMetrics (class_name, project_name, scope, version, td_minutes, principal, code_smells, bugs, "
				+ "vulnerabilities, duplicated_lines_density, classes, complexity, functions, nloc, statements, comment_lines_density, language)  VALUES('"
				+ className + "'," + "'" + projectName + "'," + "'"  + type + "'," + "'" + version + "'," + "" + td + "," + "" + principal + "," + "" + codeSmells
				+ "," + "" + bugs + "," + "" + vulnerabilities + "," + "" + duplications  + "," + "" + classes + "," + "" + complexity + "," + "" + functions
				+ "," + "" + nloc + "," + "" + statements + "," + "" + comments_density + "," + "'" + language + "'"
				+  ") ON DUPLICATE KEY UPDATE class_name = '" + className
				+ "';";
		stmt.executeUpdate(query);

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		//System.out.println("Calculated Metrics saved in database successfully!");
	}
	public void updatePrincipal(String className, int versionNum, double principal) throws SQLException
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "UPDATE principalMetrics SET principal = ? WHERE class_name = ? AND version = ? ;";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setDouble(1, principal);
			pstm.setString(2, className);
			pstm.setDouble(3, versionNum);
			pstm.executeUpdate();
			
			//conn.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Database request failed. Please try again!");
		} 
		//System.out.println("Calculated Breaking Point - Interest - Pricipal saved in database successfully!");
	}
	

}
