package main.java.breakingPointTool.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.java.breakingPointTool.connection.DatabaseConnection;
import main.java.breakingPointTool.connection.SonarDatabaseConnection;

public class DatabaseGetData 
{
	private String kee;
	private ArrayList<String> projectKees;
	private ArrayList<String> classesIDs;
	private ArrayList<String> packagesIDs;
	private ArrayList<String> projectsIDs;
	private ArrayList<String> projectsNames;
	
	public DatabaseGetData(String projectName)
	{
		this.projectKees = new ArrayList<String>();
		this.classesIDs = new ArrayList<String>();
		this.packagesIDs = new ArrayList<String>();
		this.projectsIDs = new ArrayList<String>();
		this.projectsNames = new ArrayList<String>();
		this.projectKees = new ArrayList<String>();
		getKeeForProject(projectName);
	}
	
	public DatabaseGetData()
	{
		this.projectKees = new ArrayList<String>();
		this.classesIDs = new ArrayList<String>();
		this.packagesIDs = new ArrayList<String>();
		this.projectsIDs = new ArrayList<String>();
		this.projectsNames = new ArrayList<String>();
	}
	
	public void DatabaseForPackages(String projectName) 
	{
		getDirectoriesForProject(projectName, kee);	
	}
	
	public void DatabaseForProjects()
	{
		projectsNames = new ArrayList<String>();
		projectsIDs = new ArrayList<String>();
		getAllProjects();
	}
	
	public ArrayList<Double> getCouplingCohesion(String fileName, int version)
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String query;
		ArrayList<Double> cc = new ArrayList<Double>();
		
		
		query = "SELECT coupling, cohesion FROM cMetrics WHERE class_name = (?) AND version = (?) ";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, fileName);
			pstm.setInt(2, version);
			resultSet = pstm.executeQuery();
			while (resultSet.next()) 
			{
				cc.add(resultSet.getDouble("coupling"));
				cc.add(resultSet.getDouble("cohesion"));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Database select request failed. The project or the kee does not exist in the database."
					+ "Please try again!");
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cc;
	}
	
	
	public void getKeeForProject(String projectName)
	{
		Connection conn = SonarDatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String query;
		query = "SELECT kee FROM projects WHERE name LIKE (?) ORDER BY LENGTH(kee), kee ASC";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, projectName);
			resultSet = pstm.executeQuery();
			while (resultSet.next()) 
			{
				this.projectKees.add(resultSet.getString("kee"));
			}
			/*if (resultSet.next())
				kee = resultSet.getString("kee");
			else
			{
				System.out.println("Project not found in database, execution failed! Please try again!");
				System.exit(0);
			}*/
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Database Query Request failed. The project does not exist!");
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Kee from project " + projectName+ " retrieved from database successfully!");
	}
	
	public void getClassesForProject(String projectName, String kee)
	{
		Connection conn = SonarDatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String query;
		query = "SELECT kee FROM projects WHERE scope LIKE 'FIL' AND kee LIKE (?)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, kee + ":%");
			resultSet = pstm.executeQuery();
			while (resultSet.next()) 
			{
				String classesId = resultSet.getString("kee");
				this.classesIDs.add(classesId);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Database select request failed. The project or the kee does not exist in the database."
					+ "Please try again!");
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Classes from project " + projectName + " retrieved from database successfully!");		
	}
	
	public void  getDirectoriesForProject(String projectName, String kee)
	{
		Connection conn = SonarDatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String query;
		query = "SELECT kee FROM projects WHERE scope LIKE 'DIR' AND kee LIKE (?)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, kee + ":%");
			resultSet = pstm.executeQuery();
			while (resultSet.next()) 
			{
				String classesId = resultSet.getString("kee");
				this.packagesIDs.add(classesId);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Database select request failed. The project or the kee does not exist in the database."
					+ "Please try again!");
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Directories from project " + projectName + " retrieved from database successfully!");
		//return keeDirectoryID;	
	}
	
	public void  getAllProjects()
	{
		ArrayList<String> keeDirectoryID = new ArrayList<>();
		Connection conn = SonarDatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		String query;
		query = "SELECT kee,name FROM projects WHERE scope LIKE 'PRJ' ORDER by kee DESC";
		try 
		{
			pstm = conn.prepareStatement(query);
			//pstm.setString(1, kee + ":%");
			resultSet = pstm.executeQuery();
			while (resultSet.next()) 
			{
				String classesId = resultSet.getString("kee");
				String projectName = resultSet.getString("name");
				this.projectsNames.add(projectName);
				keeDirectoryID.add(classesId);
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Database select request failed. There are not projects in the database."
					+ "Please try again!");
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("Projects retrieved from database successfully!");
		processingForProjectsAndVersions(keeDirectoryID);
		//return keeDirectoryID;	
	}
	
	public void processingForProjectsAndVersions(ArrayList<String> kees)
	{
		int i = 1;
		String investigatedId = kees.get(0);
		this.projectsIDs.add(investigatedId);
		investigatedId = investigatedId.replaceAll("\\d","");
		while (i < (kees.size()-1))
		{
			if (kees.get(i).contains(investigatedId)) 
			{
				i++;
			}
			else
			{
				investigatedId = kees.get(i);
				this.projectsIDs.add(investigatedId);
				investigatedId = investigatedId.replaceAll("\\d","");				
				i++;
			}
		}
		
	}
	
	public ArrayList<String> getUniqueNames()
	{
		// Store unique items in result.
        ArrayList<String> result = new ArrayList<>();
        // Loop over argument list.
        for (String item : projectsNames) {

            // If String is not in set, add it to the list and the set.
            if (!result.contains(item)) 
            {
                result.add(item);
            }
        }
        return result;
	}
	
	public ArrayList<Double> getKForArtifact(String className)
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		ArrayList<Double> k = new ArrayList<Double>();
		String query;
		
		query = "SELECT frequency_of_change FROM javaMetrics WHERE class_name LIKE (?)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, className);
			resultSet = pstm.executeQuery();
			while (resultSet.next()) 
			{
				k.add(resultSet.getDouble("frequency_of_change"));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Database select request failed. The project or the kee does not exist in the database."
					+ "Please try again!");
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return k;
	}
	
	public ArrayList<Double> getLoCForArtifact(String className, int version)
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		ArrayList<Double> loc = new ArrayList<Double>();
		String query;
		
		query = "SELECT loc FROM javaMetrics WHERE class_name LIKE (?)  AND version <= (?) ";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, className);
			pstm.setDouble(2, version);
			resultSet = pstm.executeQuery();
			while (resultSet.next()) 
			{
				loc.add(resultSet.getDouble("loc"));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Database select request failed. The project or the kee does not exist in the database."
					+ "Please try again!");
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return loc;
	}
	
	public ArrayList<Double> getLoCForArtifactC(String className, int version)
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		ArrayList<Double> loc = new ArrayList<Double>();
		String query;
		
		query = "SELECT loc FROM cMetrics WHERE class_name LIKE (?)  AND version <= (?) ";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, className);
			pstm.setDouble(2, version);
			resultSet = pstm.executeQuery();
			while (resultSet.next()) 
			{
				loc.add(resultSet.getDouble("loc"));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Database select request failed. The project or the kee does not exist in the database."
					+ "Please try again!");
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return loc;
	}
	
	public ArrayList<Double> getKForArtifactC(String className)
	{
		Connection conn = DatabaseConnection.getConnection();
		PreparedStatement pstm = null;
		ResultSet resultSet = null;
		ArrayList<Double> k = new ArrayList<Double>();
		String query;
		
		query = "SELECT frequency_of_change FROM cMetrics WHERE class_name LIKE (?)";
		try 
		{
			pstm = conn.prepareStatement(query);
			pstm.setString(1, className);
			resultSet = pstm.executeQuery();
			while (resultSet.next()) 
			{
				k.add(resultSet.getDouble("frequency_of_change"));
			}

		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("Database select request failed. The project or the kee does not exist in the database."
					+ "Please try again!");
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return k;
	}
	
	public void clearData()
	{
		this.classesIDs.clear();
		this.packagesIDs.clear();
		this.projectsIDs.clear();
		this.projectsNames.clear();
	}
	
	public ArrayList<String> getClassesId()
	{
		return classesIDs;
	}
	
	public ArrayList<String> getPackagesId()
	{
		return packagesIDs;
	}
	
	public ArrayList<String> getProjectsId()
	{
		return projectsIDs;
	}
	
	public ArrayList<String> getProjectsName()
	{
		return projectsNames;
	}
	
	public ArrayList<String> getProjectsKees()
	{
		return projectKees;
	}

}
