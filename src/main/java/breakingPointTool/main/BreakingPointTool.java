package main.java.breakingPointTool.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;

import org.json.JSONException;
import org.xml.sax.SAXException;

import main.java.breakingPointTool.GitClone.GitCloneProject;
import main.java.breakingPointTool.api.SonarQubeArtifacts;
import main.java.breakingPointTool.api.SonarQubeMetrics;
import main.java.breakingPointTool.artifact.ProjectArtifact;
import main.java.breakingPointTool.calculations.AverageLocCalculation;
import main.java.breakingPointTool.calculations.AverageLocCalculationC;
import main.java.breakingPointTool.calculations.FindSimilarArtifacts;
import main.java.breakingPointTool.calculations.FindSimilarArtifactsC;
import main.java.breakingPointTool.calculations.OptimalArtifact;
import main.java.breakingPointTool.calculations.OptimalArtifactC;
import main.java.breakingPointTool.calculations.Results;
import main.java.breakingPointTool.calculations.ResultsC;
import main.java.breakingPointTool.connection.DatabaseConnection;
import main.java.breakingPointTool.database.DatabaseSaveData;
import main.java.breakingPointTool.database.GetAnalysisDataJava;
import main.java.breakingPointTool.database.TablesCreation;
import main.java.breakingPointTool.deletePreAnalysedData.deletePreAnalysedData;
import main.java.breakingPointTool.externalTools.MetricsCalculator;
import main.java.breakingPointTool.externalTools.RippleEffectChangeProneness;
import main.java.breakingPointTool.externalTools.sonarqube;
import main.java.breakingPointTool.versions.Versions;

/* This tool is called Breaking Point Tool and calculates quality metrics for Java, C++ and C
 * such as maintainability metrics and technical debt metrics such as principal, interest and breaking point.
 * 
 * It uses some other support tools.
 * 1) SonarQube (needs to be installed)
 * 2) Metrics Calculator for OOP metrics (jar)
 * 3) Metrics Calculator for NoOOP metrics (jar)
 * 4) Interest Probability for OOP (jar)
 * At the end, writes all the calculated metrics in a database.
 * 
 * The analysis occurs in class, package and project level. 
 * 
 * Prerequisites:
 * 1) A number of software versions
 * 2) A jar file for every software version for java code
 * 3) Specific structure of code: project -> package -> class/file
 * 4) Project name does not allow spaces
 * 5) At least 2 classes/files per project
 */

public class BreakingPointTool 
{
	//public static final String BASE_DIR= "/opt";
	private static Scanner keyboard;

	public static void main(String [ ] args) throws IOException, InterruptedException, NumberFormatException, SQLException, JSONException, SAXException, ParserConfigurationException, InstantiationException, IllegalAccessException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException
	{	
		// Passing parameters through stream 
		String projectName = null, jarName = null, language = null;
		int versionsNum = 0;
		int typeAnalysis = 0;
		String gitUsername = null, gitPassword = null, gitUrl = null;
		String shas = null;

		ArrayList <String> artifactLongNamesPackage = new ArrayList<>();

		ArrayList<FindSimilarArtifacts> similarCl = new ArrayList<FindSimilarArtifacts>();
		ArrayList<FindSimilarArtifacts> similarPck = new ArrayList<FindSimilarArtifacts>();

		ArrayList<FindSimilarArtifactsC> similarClC = new ArrayList<FindSimilarArtifactsC>();
		ArrayList<FindSimilarArtifactsC> similarPckC = new ArrayList<FindSimilarArtifactsC>();
		
		deletePreAnalysedData del = new deletePreAnalysedData();

		// NEW DATA STRUCTURES
		ProjectArtifact projectArtifacts = new ProjectArtifact();	

		if(args.length != 0)
		{		
			try {
				projectName = args[0];
				jarName = projectName;
				language = args[1];
				versionsNum = Integer.parseInt(args[2]);
				typeAnalysis = Integer.parseInt(args[3]);
				gitUrl = args[4];
				gitUsername = args[5];
				gitPassword = args[6];
				shas = args[7];
			}
			catch (ArrayIndexOutOfBoundsException e){
				System.out.println("Error: You should pass 7 parameters! \n 1) Project Name \n 2) Language \n 3) Num of Versions "
						+ "\n4) Type of analysis \n 5) Git URL \n 6) Git Username \n  7) Git Password");
			}
		}
		else
		{
			keyboard = new Scanner(System.in);
			System.out.println("Please type programming language for analysis: \nAvailable options:\n  1) Java \n"
					+ "  2) C 3) C++");
			language = keyboard.nextLine();
			System.out.println("Type the project Name: ");
			projectName = keyboard.nextLine();
			System.out.println("Type the number of versions: ");
			versionsNum = keyboard.nextInt();
			keyboard.nextLine(); // Consume newline left-over
			//System.out.println("Type the projects jar file name: "); 
			//jarName = keyboard.nextLine();	
			jarName = projectName;
			//System.out.println("Type path of main project for example: src/main/java/ "); 
			//path = keyboard.nextLine();	
			System.out.println("Type 1 for new analysis or 2 for a new version analysis to existed project"); 
			typeAnalysis = keyboard.nextInt();

			System.out.println("Type the Git repository URL:");
			keyboard.nextLine(); // Consume newline left-over
			gitUrl = keyboard.nextLine();
			System.out.println("Type the Git Account Username:");
			gitUsername = keyboard.nextLine();
			System.out.println("Type the Git Account Password:");
			gitPassword = keyboard.nextLine();
			System.out.println("Type SHAs of version seperate them with comma:");
			shas = keyboard.nextLine();
		}

		// NEW DATA STRUCTURES
		projectArtifacts.setprojectName(projectName);
		projectArtifacts.setNumOfVersions(versionsNum);

		// Configuration File, get all data from file and make the jar works in every machine
		String line;
		String exec = "" ;
		String projectPath = "";
		String jarPath = "";
		String serverUrl = "";
		// For sonarqube connection
		String usernameSQConnection = "", passwordSQConnection = "";
		// For sonarqube database
		//String usernameSQ = "", passwordSQ = "";
		// For results database
		String usernameDBConnection = "", passwordDBConnection= "";
		String sonarName = "";
		String serverName = "";
		String dbName = "";
		
		String jarLocation = System.getProperty("user.dir");
		// for eclipse execution
		//int t = jarLocation.lastIndexOf("/");
		//jarLocation = jarLocation.substring(0,t);

		String credentials = jarLocation + "/configurations.txt";
		//String credentials = BreakingPointTool.BASE_DIR + "/configurations.txt";
		
		System.out.println("Location of config file: " + credentials);


		if (new File(credentials).exists()) 
		{ 
			BufferedReader br = new BufferedReader(new FileReader(credentials));

			while ((line = br.readLine()) != null)
			{ 
				if (line.contains("#")) 
					continue;

				// Metrics Database Credentials
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

				if (line.contains("sonarqube_execution:")) 
				{ 
					String[] temp = line.split("sonarqube_execution:"); 
					exec = temp[1]; 
				}

				if (line.contains("project_path:")) 
				{ 
					String[] temp = line.split("project_path:"); 
					projectPath = temp[1]; 
				}

				if (line.contains("jar_path:")) 
				{ 
					String[] temp = line.split("jar_path:");
					jarPath = temp[1]; 
				}

				if (line.contains("sonar_url:")) 
				{ 	
					String[] temp = line.split("sonar_url:");
					serverUrl = temp[1]; 
				}

				/*
				if (line.contains("sonarName:"))
				{
					String[] temp = line.split("sonarName:");
					sonarName = temp[1];
				}
				*/

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


				// SonarQube Execution Credentials
				if (line.contains("usernameSQ:")) 
				{ 
					if (line.length() > 11) 
					{ 
						String[] temp = line.split("usernameSQ:"); 
						usernameSQConnection = temp[1]; 
					}
				}

				if (line.contains("passwordSQ:")) 
				{  
					if (line.length() > 11) 
					{ 
						String[] temp = line.split("passwordSQ:"); 
						passwordSQConnection = temp[1];
					}
				} 
			}
			br.close();

			// Git clone code
			ArrayList<String> shaS = new ArrayList<String>();
			
			String [] splitShas = shas.split(",");
			
			if (versionsNum != splitShas.length)
			{
				System.out.println("Number of SHAs and versions are not the same. Try again");
				System.exit(0);
			}
			
			for (int tr = 0; tr < splitShas.length; tr++)
			{
				shaS.add(splitShas[tr]);
			}
			

			GitCloneProject git = new GitCloneProject();

			git.cloneCommits(jarLocation, gitUsername, gitPassword, shaS, gitUrl, projectName, versionsNum);

			projectPath = git.getProjectPath();
			projectPath = projectPath.replace(projectName, "");

			// Set Credentials to Database
			new DatabaseConnection(usernameDBConnection, passwordDBConnection, serverName + "/" +  dbName);
			// Execute SonarQube
			sonarqube sonar = new sonarqube();
			sonar.sonarQube(exec,projectName, versionsNum, projectPath, language, serverUrl, usernameSQConnection, passwordSQConnection, typeAnalysis); 
			// Create database tables
			TablesCreation tables = new TablesCreation();
			tables.createDatabaseTables();

			// If the project has records on DB delete them
			if (typeAnalysis == 1) 
				del.DeleteFromDBAlreadyAnalysedProject(projectName, language);
			else
				del.DeleteFromDBAlreadyAnalysedVersion(projectName,language, versionsNum-1);	
		}


		if (language.equals("Java"))
		{
			// Databases call for every level		
			//DatabaseGetData dbCall = new DatabaseGetData(projectName);	
			SonarQubeArtifacts artifacts = new SonarQubeArtifacts(serverUrl);

			if (typeAnalysis == 1) {

				DatabaseSaveData dbSave = new DatabaseSaveData();
				for (int i = 0; i < versionsNum; i ++)
				{	
					dbSave.saveTimestamp(projectName, i);
				}

				for (int i = 0; i < versionsNum; i ++)
				{
					// Jar Path inside git clone
					jarPath = projectPath + projectName +  File.separator + projectName + i +  File.separator + "jars" + File.separator;
					
					// Ripple Effect and Change Proneness Measure Execution
					RippleEffectChangeProneness rem = new RippleEffectChangeProneness();
					rem.ExtractJar(jarName, i, jarPath, projectName);

					// Metrics Calculator Execution
					MetricsCalculator metricsCalc = new MetricsCalculator();
					// sta  "" jarPath
					
					metricsCalc.executeOneVersion(jarName, i, jarPath, projectName);

					//dbCall.getDirectoriesForProject(projectName, dbCall.getProjectsKees().get(i));
					//dbCall.getClassesForProject(projectName, dbCall.getProjectsKees().get(i));
					
					artifacts.getArtifactsName(projectName + i, "FIL");
					artifacts.getArtifactsName(projectName + i, "DIR");
							
					Versions v = new Versions();
					v.setProjectName(projectName);
					v.setVersionId(i);			

					// Get metrics from Sonar API for every level
					SonarQubeMetrics metricsFromSonarClassLevel = new SonarQubeMetrics(serverUrl);
					metricsFromSonarClassLevel.getMetricsFromApiSonarClassLevel(artifacts.getClassesId(), language);
					metricsFromSonarClassLevel.getTDFromApiSonar(artifacts.getClassesId());
					metricsFromSonarClassLevel.getMetricCommentsDensityFromApi(artifacts.getClassesId());

					SonarQubeMetrics metricsFromSonarPackageLevel = new SonarQubeMetrics(serverUrl);
					metricsFromSonarPackageLevel.getMetricsFromSonarPackageLevel(artifacts.getPackagesId(), language);
					metricsFromSonarPackageLevel.getTDFromApiSonar(artifacts.getPackagesId());
					metricsFromSonarPackageLevel.getMetricCommentsDensityFromApi(artifacts.getPackagesId());

					// Set metrics from metrics calculator at class and package level
					AverageLocCalculation calcAverageAllLevels = new AverageLocCalculation();
					calcAverageAllLevels.setMetricsToClassLevel(projectName, i);
					calcAverageAllLevels.setClassToPackageLevel(artifacts.getPackagesId(),projectName, i);	

					v.setPackageInProject(calcAverageAllLevels.getObjectsPackageMetrics());
					
					for (int index = 0; index < calcAverageAllLevels.getObjectsPackageMetrics().size(); index++)
					{
						System.out.println("Package name: " + calcAverageAllLevels.getObjectsPackageMetrics().get(index).getPackageName());
						System.out.println("Classes in package: "); 
						for (int tired = 0; tired < calcAverageAllLevels.getObjectsPackageMetrics().get(index).getClassInProject().size(); tired++)
						 {
							 System.out.println(calcAverageAllLevels.getObjectsPackageMetrics().get(index).getClassInProject().get(tired).getClassName());
						 }
						System.out.println();
					}
					//artifactLongNamesPackage.clear();

					for (int j = 0; j < v.getPackages().size(); j++)
					{
						String pack1 = v.getPackages().get(j).getPackageName();

						for (int l = 0; l < metricsFromSonarPackageLevel.getArtifactNames().size(); l++)
						{
							String pack2 = metricsFromSonarPackageLevel.getArtifactNames().get(l);
							
							if (pack2.contains(pack1))
							{
								v.getPackages().get(j).metricsfromSonar(metricsFromSonarPackageLevel.getNumOfClasses().get(l),
										metricsFromSonarPackageLevel.getComplexity().get(l),
										metricsFromSonarPackageLevel.getFunctions().get(l),
										metricsFromSonarPackageLevel.getNcloc().get(l),
										metricsFromSonarPackageLevel.getStatements().get(l),
										metricsFromSonarPackageLevel.getTechnicalDebt().get(l),
										metricsFromSonarPackageLevel.getCodeSmells().get(l),
										metricsFromSonarPackageLevel.getBugs().get(l),
										metricsFromSonarPackageLevel.getVulnerabilities().get(l),
										metricsFromSonarPackageLevel.getDuplicationsDensity().get(l)
										);

								DatabaseSaveData saveInDataBase = new DatabaseSaveData(); 
								saveInDataBase.savePrincipalMetrics(pack1, projectName, i, 
										metricsFromSonarPackageLevel.getTechnicalDebt().get(l), 0, metricsFromSonarPackageLevel.getCodeSmells().get(l), 
										metricsFromSonarPackageLevel.getBugs().get(l), metricsFromSonarPackageLevel.getVulnerabilities().get(l), 
										metricsFromSonarPackageLevel.getDuplicationsDensity().get(l), "DIR", metricsFromSonarPackageLevel.getNumOfClasses().get(l),
										metricsFromSonarPackageLevel.getComplexity().get(l), metricsFromSonarPackageLevel.getFunctions().get(l),
										metricsFromSonarPackageLevel.getNcloc().get(l), metricsFromSonarPackageLevel.getStatements().get(l),
										0, language);

								break;
							}

						}

						// set sonar metrics in class level
						for (int k = 0; k < v.getPackages().get(j).getClassInProject().size(); k++)
						{
							String class1 = v.getPackages().get(j).getClassInProject().get(k).getClassName();

							for (int l = 0; l < metricsFromSonarClassLevel.getArtifactNames().size(); l++)
							{
								String class2 = metricsFromSonarClassLevel.getArtifactNames().get(l);

								if (class2.contains(class1))
								{
									v.getPackages().get(j).getClassInProject().get(k).metricsfromSonar(metricsFromSonarClassLevel.getNumOfClasses().get(l),
											metricsFromSonarClassLevel.getComplexity().get(l),
											metricsFromSonarClassLevel.getFunctions().get(l),
											metricsFromSonarClassLevel.getNcloc().get(l),
											metricsFromSonarClassLevel.getStatements().get(l),
											metricsFromSonarClassLevel.getTechnicalDebt().get(l),
											metricsFromSonarClassLevel.getCodeSmells().get(l),
											metricsFromSonarClassLevel.getBugs().get(l),
											metricsFromSonarClassLevel.getVulnerabilities().get(l),
											metricsFromSonarClassLevel.getDuplicationsDensity().get(l));

									DatabaseSaveData saveInDataBase = new DatabaseSaveData(); 
									saveInDataBase.savePrincipalMetrics(class1, projectName, i, 
											metricsFromSonarClassLevel.getTechnicalDebt().get(l), 0, metricsFromSonarClassLevel.getCodeSmells().get(l), 
											metricsFromSonarClassLevel.getBugs().get(l), metricsFromSonarClassLevel.getVulnerabilities().get(l), 
											metricsFromSonarClassLevel.getDuplicationsDensity().get(l), "FIL", metricsFromSonarClassLevel.getNumOfClasses().get(l),
											metricsFromSonarClassLevel.getComplexity().get(l), metricsFromSonarClassLevel.getFunctions().get(l),
											metricsFromSonarClassLevel.getNcloc().get(l), metricsFromSonarClassLevel.getStatements().get(l),
											0, language);

									break;
								}
							}
						}
					}

					projectArtifacts.setVersion(v);
					artifacts.clearData();


				} // End of FOR of versions

				// Calculate LOC 
				AverageLocCalculation calcAverageAllLevels = new AverageLocCalculation();
				calcAverageAllLevels.calculateLocPackageLevel(projectArtifacts);
				calcAverageAllLevels.calculateLocClassLevel(projectArtifacts);

				for (int i = 0; i < versionsNum; i++)
				{
					// Find similar classes
					FindSimilarArtifacts similarArtifacts = new FindSimilarArtifacts();
					similarCl = similarArtifacts.calculateSimilarityForClasses(projectArtifacts, i, typeAnalysis);

					// Find similar packages
					similarPck = similarArtifacts.calculateSimilarityForPackages(projectArtifacts, i, typeAnalysis);

					// Calculate Optimal Class
					OptimalArtifact optimalClass = new OptimalArtifact();
					optimalClass.calculateOptimalClass(similarCl, i);

					if (similarPckC.size() == 1)
					{
						Results rs = new Results();
						rs.calculateInterestOnePackage(similarPck.get(0).getPackage(), projectName, i);
					}
					else
					{
						OptimalArtifact optimalPackage = new OptimalArtifact();
						optimalPackage.calculateOptimalPackage(similarPck, i);
					} 

					dbSave.deleteTimestamp(projectName, i);
				}
			}
			else if (typeAnalysis == 2)
			{

				DatabaseSaveData dbSave = new DatabaseSaveData();
				dbSave.saveTimestamp(projectName, versionsNum - 1);

				// Get metrics from Database from previous version
				GetAnalysisDataJava classMetrics = new GetAnalysisDataJava();
				classMetrics.getAnalysisDataBPTJava(projectName, "FIL", Integer.toString(versionsNum - 2));
				classMetrics.getAnalysisDataPrincipalSonar(projectName, "FIL", versionsNum - 2);
				GetAnalysisDataJava packageMetrics = new GetAnalysisDataJava();
				packageMetrics.getAnalysisDataBPTJava(projectName, "DIR", Integer.toString(versionsNum - 2));
				packageMetrics.getAnalysisDataPrincipalSonar(projectName, "DIR", versionsNum - 2);

				//################################ Analysis of NEW VERSION ###########################################
				// Ripple Effect and Change Proneness Measure Execution
				RippleEffectChangeProneness rem = new RippleEffectChangeProneness();
				rem.ExtractJar(jarName, versionsNum-1, jarPath, projectName);

				// Metrics Calculator Execution
				MetricsCalculator metricsCalc = new MetricsCalculator();
				// sta  "" jarPath
				metricsCalc.executeOneVersion(jarName, versionsNum-1, jarPath, projectName);

				// Get directories and files names from SonarQube DB Last Version
				//dbCall.getDirectoriesForProject(projectName, artifacts.getProjectsKees().get(versionsNum - 1));
				//dbCall.getClassesForProject(projectName, dbCall.getProjectsKees().get(versionsNum - 1));	
				
				artifacts.getArtifactsName(projectName + (versionsNum - 1), "FIL");
				artifacts.getArtifactsName(projectName + (versionsNum - 1), "DIR");

				// Get metrics from Sonar API for every level - NEW VERSION
				SonarQubeMetrics metricsFromSonarClassLevel = new SonarQubeMetrics(serverUrl);
				metricsFromSonarClassLevel.getMetricsFromApiSonarClassLevel(artifacts.getClassesId(), language);
				metricsFromSonarClassLevel.getTDFromApiSonar(artifacts.getClassesId());
				metricsFromSonarClassLevel.getMetricCommentsDensityFromApi(artifacts.getClassesId());

				SonarQubeMetrics metricsFromSonarPackageLevel = new SonarQubeMetrics(serverUrl);
				metricsFromSonarPackageLevel.getMetricsFromSonarPackageLevel(artifacts.getPackagesId(), language);
				metricsFromSonarPackageLevel.getTDFromApiSonar(artifacts.getPackagesId());
				metricsFromSonarPackageLevel.getMetricCommentsDensityFromApi(artifacts.getPackagesId());

				// Set metrics from metrics calculator at class and package level
				AverageLocCalculation calcAverageAllLevels = new AverageLocCalculation();
				// Save Metrics Calculator Class Metrics
				calcAverageAllLevels.setMetricsToClassLevel(projectName, versionsNum-1);
				// Save Metrics Calculator Package Metrics
				calcAverageAllLevels.setClassToPackageLevel(artifacts.getPackagesId(),projectName, versionsNum-1);

				Versions v = new Versions();
				v.setProjectName(projectName);
				v.setVersionId(versionsNum-1);

				v.setPackageInProject(calcAverageAllLevels.getObjectsPackageMetrics());

				for (int j = 0; j < v.getPackages().size(); j++)
				{
					String pack1 = v.getPackages().get(j).getPackageName();

					for (int l = 0; l < metricsFromSonarPackageLevel.getArtifactNames().size(); l++)
					{
						String pack2 = metricsFromSonarPackageLevel.getArtifactNames().get(l);

						if (pack2.contains(pack1))
						{
							v.getPackages().get(j).metricsfromSonar(metricsFromSonarPackageLevel.getNumOfClasses().get(l),
									metricsFromSonarPackageLevel.getComplexity().get(l),
									metricsFromSonarPackageLevel.getFunctions().get(l),
									metricsFromSonarPackageLevel.getNcloc().get(l),
									metricsFromSonarPackageLevel.getStatements().get(l),
									metricsFromSonarPackageLevel.getTechnicalDebt().get(l),
									metricsFromSonarPackageLevel.getCodeSmells().get(l),
									metricsFromSonarPackageLevel.getBugs().get(l),
									metricsFromSonarPackageLevel.getVulnerabilities().get(l),
									metricsFromSonarPackageLevel.getDuplicationsDensity().get(l)
									);

							DatabaseSaveData saveInDataBase = new DatabaseSaveData(); 
							saveInDataBase.savePrincipalMetrics(pack1, projectName, versionsNum-1, 
									metricsFromSonarPackageLevel.getTechnicalDebt().get(l), 0, metricsFromSonarPackageLevel.getCodeSmells().get(l), 
									metricsFromSonarPackageLevel.getBugs().get(l), metricsFromSonarPackageLevel.getVulnerabilities().get(l), 
									metricsFromSonarPackageLevel.getDuplicationsDensity().get(l), "DIR", metricsFromSonarPackageLevel.getNumOfClasses().get(l),
									metricsFromSonarPackageLevel.getComplexity().get(l), metricsFromSonarPackageLevel.getFunctions().get(l),
									metricsFromSonarPackageLevel.getNcloc().get(l), metricsFromSonarPackageLevel.getStatements().get(l),
									0, language);

							break;
						}

					}

					// set sonar metrics in class level
					for (int k = 0; k < v.getPackages().get(j).getClassInProject().size(); k++)
					{
						String class1 = v.getPackages().get(j).getClassInProject().get(k).getClassName();

						for (int l = 0; l < metricsFromSonarClassLevel.getArtifactNames().size(); l++)
						{
							String class2 = metricsFromSonarClassLevel.getArtifactNames().get(l);

							if (class2.contains(class1))
							{
								v.getPackages().get(j).getClassInProject().get(k).metricsfromSonar(metricsFromSonarClassLevel.getNumOfClasses().get(l),
										metricsFromSonarClassLevel.getComplexity().get(l),
										metricsFromSonarClassLevel.getFunctions().get(l),
										metricsFromSonarClassLevel.getNcloc().get(l),
										metricsFromSonarClassLevel.getStatements().get(l),
										metricsFromSonarClassLevel.getTechnicalDebt().get(l),
										metricsFromSonarClassLevel.getCodeSmells().get(l),
										metricsFromSonarClassLevel.getBugs().get(l),
										metricsFromSonarClassLevel.getVulnerabilities().get(l),
										metricsFromSonarClassLevel.getDuplicationsDensity().get(l));

								DatabaseSaveData saveInDataBase = new DatabaseSaveData(); 
								saveInDataBase.savePrincipalMetrics(class1, projectName, versionsNum - 1, 
										metricsFromSonarClassLevel.getTechnicalDebt().get(l), 0, metricsFromSonarClassLevel.getCodeSmells().get(l), 
										metricsFromSonarClassLevel.getBugs().get(l), metricsFromSonarClassLevel.getVulnerabilities().get(l), 
										metricsFromSonarClassLevel.getDuplicationsDensity().get(l), "FIL", metricsFromSonarClassLevel.getNumOfClasses().get(l),
										metricsFromSonarClassLevel.getComplexity().get(l), metricsFromSonarClassLevel.getFunctions().get(l),
										metricsFromSonarClassLevel.getNcloc().get(l), metricsFromSonarClassLevel.getStatements().get(l),
										0, language);

								break;
							}
						}
					}
				}

				projectArtifacts.setVersion(v);
				artifacts.clearData();

				// Calculate LOC 
				AverageLocCalculation calcLOC = new AverageLocCalculation();

				calcLOC.calculateLOCClassLevelNewVersion(classMetrics.getClassMetricsMap(), v.getPackages(), versionsNum -1);
				calcLOC.calculateLOCCPackageLevelNewVersion(packageMetrics.getPackageMetricsMap(), v.getPackages(), versionsNum -1);

				// Find similar classes
				FindSimilarArtifacts similarArtifacts = new FindSimilarArtifacts();
				similarCl = similarArtifacts.calculateSimilarityForClasses(projectArtifacts, versionsNum-1, typeAnalysis);

				// Find similar packages
				similarPck = similarArtifacts.calculateSimilarityForPackages(projectArtifacts, versionsNum-1, typeAnalysis);

				// Calculate Optimal Class
				OptimalArtifact optimalClass = new OptimalArtifact();
				optimalClass.calculateOptimalClass(similarCl, versionsNum-1);

				//OptimalArtifact optimalPackage = new OptimalArtifact();
				//optimalPackage.calculateOptimalPackage(similarPck, versionsNum-1);

				if (similarPckC.size() == 1)
				{
					Results rs = new Results();
					rs.calculateInterestOnePackage(similarPck.get(0).getPackage(), projectName, versionsNum - 1);
				}
				else
				{
					OptimalArtifact optimalPackage = new OptimalArtifact();
					optimalPackage.calculateOptimalPackage(similarPck, versionsNum-1);
				} 

				dbSave.deleteTimestamp(projectName, versionsNum - 1);

			}


			// CHECK DATA PRINT
			/*
			for (int i = 0; i < versionsNum; i++)
			{
				System.out.println("Project ID: " + projectArtifacts.getProjectName());
				System.out.println("Version ID: " + projectArtifacts.getVersions().get(i).getVersionId());
				for (int j = 0; j < projectArtifacts.getVersions().get(i).getPackages().size(); j++)
				{
					System.out.println("Package: " +  projectArtifacts.getVersions().get(i).getPackages().get(j).getPackageName());
					System.out.println("Size1: " +  projectArtifacts.getVersions().get(i).getPackages().get(j).getSize1());
					System.out.println("TD: " +  projectArtifacts.getVersions().get(i).getPackages().get(j).getTD());
					System.out.println("LOC: " +  projectArtifacts.getVersions().get(i).getPackages().get(j).getAverageLocChange());


					for (int x = 0; x < projectArtifacts.getVersions().get(i).getPackages().get(j).getClassInProject().size(); x++)
					{
						System.out.println("Class : " + projectArtifacts.getVersions().get(i).getPackages().get(j).getClassInProject().get(x).getClassName());
						System.out.println("Size1 : " + projectArtifacts.getVersions().get(i).getPackages().get(j).getClassInProject().get(x).getSize1());
						System.out.println("TD : " + projectArtifacts.getVersions().get(i).getPackages().get(j).getClassInProject().get(x).getTD());
						System.out.println("LOC : " + projectArtifacts.getVersions().get(i).getPackages().get(j).getClassInProject().get(x).getAverageLocChange());
					}
				}
				System.out.println("---------");
			}*/

		}
		else if (language.equals("C") || language.equals("C++"))
		{
			// Databases call for every level		
			SonarQubeArtifacts artifacts = new SonarQubeArtifacts(serverUrl);
			
			if (typeAnalysis == 1) 
			{
				DatabaseSaveData dbSave = new DatabaseSaveData();
				for (int i = 0; i < versionsNum; i ++)
				{	
					dbSave.saveTimestamp(projectName, i);
				}

				for (int i = 0; i < versionsNum; i ++)
				{
					//String p = "/opt/" + projectName + "/"+ projectName + i;

					String p = projectPath + File.separator + projectName + File.separator + projectName + i;


					artifacts.getArtifactsName(projectName + i, "FIL");
					artifacts.getArtifactsName(projectName + i, "DIR");
					
					Versions v = new Versions();
					v.setProjectName(projectName);
					v.setVersionId(i);	

					// Get metrics from Sonar API for every level
					SonarQubeMetrics metricsFromSonarClassLevel = new SonarQubeMetrics(serverUrl);

					metricsFromSonarClassLevel.getMetricsFromApiSonarClassLevel(artifacts.getClassesId(), language);
					metricsFromSonarClassLevel.getTDFromApiSonar(artifacts.getClassesId());
					metricsFromSonarClassLevel.getMetricCommentsDensityFromApi(artifacts.getClassesId());

					SonarQubeMetrics metricsFromSonarPackageLevel = new SonarQubeMetrics(serverUrl);
					metricsFromSonarPackageLevel.getMetricsFromSonarPackageLevel(artifacts.getPackagesId(), language);
					metricsFromSonarPackageLevel.getTDFromApiSonar(artifacts.getPackagesId());
					metricsFromSonarPackageLevel.getMetricCommentsDensityFromApi(artifacts.getPackagesId());

					for (String pc : artifacts.getPackagesId())
					{
						if (pc.contains("test"))
							continue;
						String[] tempPackage = pc.split(":");

						if (tempPackage.length == 1)
							continue;
						//String[] temp1 = tempPackage[1].split(".java");
						//artifactLongNamesPackage.add("com/" + temp1[0]); 
						//System.out.println("check after: " +tempPackage[1]);
						artifactLongNamesPackage.add(tempPackage[1]); 
					}

					// Set metrics from sonar at class and package level	
					AverageLocCalculationC calcAverageAllLevels = new AverageLocCalculationC();
					calcAverageAllLevels.setMetricsToClassLevel(metricsFromSonarClassLevel, projectName, i, language, p, credentials);
					calcAverageAllLevels.setClassToPackageLevel(artifactLongNamesPackage,projectName, i, metricsFromSonarPackageLevel, language);

					v.setPackageInProjectC(calcAverageAllLevels.getObjectsPackageMetrics());
					artifactLongNamesPackage.clear();

					projectArtifacts.setVersion(v);
					artifacts.clearData();


				} 

				// Calculate LOC
				AverageLocCalculationC calcAverageAllLevels = new AverageLocCalculationC();
				calcAverageAllLevels.calculateLocPackageLevel(projectArtifacts);
				calcAverageAllLevels.calculateLocClassLevel(projectArtifacts);

				for (int i = 0; i < versionsNum; i++)
				{
					System.out.println("-------------------------");
					// Find similar classes
					FindSimilarArtifactsC similarArtifacts = new FindSimilarArtifactsC();
					similarClC = similarArtifacts.calculateSimilarityForClasses(projectArtifacts, i, typeAnalysis);

					// Find similar packages
					similarPckC = similarArtifacts.calculateSimilarityForPackages(projectArtifacts, i, typeAnalysis);

					// Calculate Optimal Class
					OptimalArtifactC optimalClass = new OptimalArtifactC();
					optimalClass.calculateOptimalClass(similarClC, i);

					// If only one package
					if (similarPckC.size() == 1)
					{
						ResultsC rs = new ResultsC();
						rs.calculateInterestOnePackage(similarPckC.get(0).getPackage(), projectName, i);
					}
					else
					{
						OptimalArtifactC optimalPackage = new OptimalArtifactC();
						optimalPackage.calculateOptimalPackage(similarPckC, i); 
					} 

					dbSave.deleteTimestamp(projectName, i);
				}
			}

			else if (typeAnalysis == 2)
			{

				DatabaseSaveData dbSave = new DatabaseSaveData();
				dbSave.saveTimestamp(projectName, versionsNum - 1);

				//String p = "/opt/" + projectName + "/"+ projectName + (versionsNum - 1);

				String p = projectPath + File.separator + projectName + File.separator + projectName + (versionsNum - 1);

				// Get metrics from Database from previous version
				GetAnalysisDataJava classMetrics = new GetAnalysisDataJava();
				classMetrics.getAnalysisDataC(projectName, "FIL", versionsNum - 2);
				classMetrics.getAnalysisDataBPTC(projectName, "FIL", versionsNum - 2);

				GetAnalysisDataJava packageMetrics = new GetAnalysisDataJava();				
				packageMetrics.getAnalysisDataC(projectName, "DIR", versionsNum - 2);
				packageMetrics.getAnalysisDataBPTC(projectName, "DIR", versionsNum - 2);

				artifacts.getArtifactsName(projectName + (versionsNum - 1), "FIL");
				artifacts.getArtifactsName(projectName + (versionsNum - 1), "DIR");

				Versions v = new Versions();
				v.setProjectName(projectName);
				v.setVersionId(versionsNum-1);	

				// Get metrics from Sonar API for every level
				SonarQubeMetrics metricsFromSonarClassLevel = new SonarQubeMetrics(serverUrl);

				metricsFromSonarClassLevel.getMetricsFromApiSonarClassLevel(artifacts.getClassesId(), language);
				metricsFromSonarClassLevel.getTDFromApiSonar(artifacts.getClassesId());
				metricsFromSonarClassLevel.getMetricCommentsDensityFromApi(artifacts.getClassesId());

				SonarQubeMetrics metricsFromSonarPackageLevel = new SonarQubeMetrics(serverUrl);
				metricsFromSonarPackageLevel.getMetricsFromSonarPackageLevel(artifacts.getPackagesId(), language);
				metricsFromSonarPackageLevel.getTDFromApiSonar(artifacts.getPackagesId());
				metricsFromSonarPackageLevel.getMetricCommentsDensityFromApi(artifacts.getPackagesId());

				for (String pc : artifacts.getPackagesId())
				{
					if (pc.contains("test"))
						continue;
					String[] tempPackage = pc.split(":");

					if (tempPackage.length == 1)
						continue;

					artifactLongNamesPackage.add(tempPackage[1]); 
				}

				// Set metrics from sonar at class and package level	
				AverageLocCalculationC calcAverageAllLevels = new AverageLocCalculationC();
				calcAverageAllLevels.setMetricsToClassLevel(metricsFromSonarClassLevel, projectName, versionsNum-1, language, p, credentials);
				calcAverageAllLevels.setClassToPackageLevel(artifactLongNamesPackage,projectName, versionsNum-1, metricsFromSonarPackageLevel, language);

				v.setPackageInProjectC(calcAverageAllLevels.getObjectsPackageMetrics());
				artifactLongNamesPackage.clear();

				projectArtifacts.setVersion(v);
				artifacts.clearData();

				// Calculate LOC

				AverageLocCalculationC calcLOC = new AverageLocCalculationC();
				calcLOC.calculateLOCClassLevelNewVersion(classMetrics.getClassMetricsCMap(), v.getPackagesC(), versionsNum -1);
				calcLOC.calculateLOCPackageLevelNewVersion(packageMetrics.getPackageMetricsCMap(), v.getPackagesC(), versionsNum -1);

				// Find similar classes
				FindSimilarArtifactsC similarArtifacts = new FindSimilarArtifactsC();
				similarClC = similarArtifacts.calculateSimilarityForClasses(projectArtifacts, versionsNum-1, typeAnalysis);

				// Find similar packages
				similarPckC = similarArtifacts.calculateSimilarityForPackages(projectArtifacts, versionsNum-1, typeAnalysis);

				// Calculate Optimal Class
				OptimalArtifactC optimalClass = new OptimalArtifactC();
				optimalClass.calculateOptimalClass(similarClC, versionsNum-1);

				if (similarPckC.size() == 1)
				{
					ResultsC rs = new ResultsC();
					rs.calculateInterestOnePackage(similarPckC.get(0).getPackage(), projectName, versionsNum-1);
				}
				else
				{
					OptimalArtifactC optimalPackage = new OptimalArtifactC();
					optimalPackage.calculateOptimalPackage(similarPckC, versionsNum-1); 
				}

				dbSave.deleteTimestamp(projectName, versionsNum - 1);

			}

		}
		else
		{
			System.out.println("Programming language does not supported. Execute the software again and choose one of the available options.");
		}

		//SonarDatabaseConnection.closeConnection();
		DatabaseConnection.closeConnection();
		del.deleteFile(versionsNum);

		// Delete directory after used
		String javaRunningDirectory = System.getProperty("user.dir");
		String cloneDirectoryPath = javaRunningDirectory + "/Projects/" + projectName;
		File directory = new File(cloneDirectoryPath);
		del.deleteSourceCode(directory);
	}
}
