package main.java.breakingPointTool.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import main.java.breakingPointTool.connection.DatabaseConnection;

public class DatabaseSaveData 
{
	public void saveMetricsInDatabase(String projectName, int versionNum, String className, String scope, double wmc, double dit, double cbo, double rfc, double lcom, 
			double wmc_dec, double nocc, double mpc, double dac, double size1, double size2, double dsc, double noh, double ana, double dam, double dcc, double camc, 
			double moa, double mfa, double nop, double cis, double nom, double Reusability, double Flexibility, double Understandability, double Functionality, 
			double Extendibility, double Effectiveness, double FanIn, double rem, double cpm) throws SQLException, NumberFormatException, IOException
	{
		// Save metrics that calculated from metrics calculator tool in database

		Statement stmt = null;
		Connection conn = DatabaseConnection.getConnection();

		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "INSERT INTO javaMetrics (class_name,project_name,scope,wmc,dit,cbo,rfc,lcom,wmc_dec,nocc,mpc,dac,loc,number_of_properties,dsc,noh,ana,dam,dcc,camc,moa,mfa,nop,cis,nom,"
				+ "reusability,flexibility,understandability,functionality,extendibility,effectiveness,fanIn,commit_hash,version,rem,cpm)  VALUES('"
				+ className + "'," + "'" + projectName + "'," + "'" + scope + "'," + "" + wmc + "," + "" + dit + "," + "" + cbo
				+ "," + "" + rfc + "," + "" + lcom + "," + "" + wmc_dec + "," + "" + nocc + "," + "" + mpc
				+ "," + "" + dac + "," + "" + size1 + "," + "" + size2 + "," + "" + dsc + "," + "" + noh
				+ "," + "" + ana + "," + "" + dam + "," + "" + dcc + "," + "" + camc + "," + "" + moa + ","
				+ "" + mfa + "," + "" + nop + "," + "" + cis + "," + "" + nom + "," + "" + Reusability + ","
				+ "" + Flexibility + "," + "" + Understandability + "," + "" + Functionality + "," + ""
				+ Extendibility + "," + "" + Effectiveness + "," + "" + FanIn + "," + "'" + "empty"
				+ "'," + "'" + versionNum + "'"     + "," + "" + rem    + "," + "" + cpm    + ") ON DUPLICATE KEY UPDATE class_name = '" + className
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
	
	public void saveBreakingPointInDatabase(String className, int versionNum, double breakingPoint, double principal, double interest, double k, double rate) throws SQLException
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;

		String query = "UPDATE javaMetrics SET principal = ? , interest = ? , breakingpoint = ? , frequency_of_change = ?, interest_probability = ?  WHERE class_name = ? AND version = ? ;";
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
				+ "," + "" + nloc + "," + "" + statements + "," + "" + comments_density + "," + "'"  + language + "'"
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
	
	public void saveTimestamp(String projectName, int versionNum) throws SQLException
	{
		Statement stmt = null;
		Connection conn = DatabaseConnection.getConnection();

		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "INSERT INTO executionTimestap (project_name, version)  VALUES('"
				+ projectName + "'," + versionNum +  ") ON DUPLICATE KEY UPDATE project_name = '" + projectName
				+ "';";
		stmt.executeUpdate(query);

		if (stmt != null) 
		{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteTimestamp(String projectName, int versionNum) throws SQLException
	{
		Statement stmt = null;
		Connection conn = DatabaseConnection.getConnection();

		try {
			stmt = (Statement) conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String query = "DELETE FROM executionTimestap WHERE project_name = '" + projectName 
				+ "' and version=" + versionNum + ";";
		stmt.executeUpdate(query);

		if (stmt != null) 
		{
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
