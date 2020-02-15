package main.java.breakingPointTool.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import main.java.breakingPointTool.api.ApiCall;
import main.java.breakingPointTool.artifact.ProjectArtifact;
import main.java.breakingPointTool.calculations.AverageLocCalculation;
import main.java.breakingPointTool.calculations.AverageLocCalculationC;
import main.java.breakingPointTool.calculations.FindSimilarArtifacts;
import main.java.breakingPointTool.calculations.FindSimilarArtifactsC;
import main.java.breakingPointTool.calculations.OptimalArtifact;
import main.java.breakingPointTool.calculations.OptimalArtifactC;
import main.java.breakingPointTool.database.DatabaseGetData;
import main.java.breakingPointTool.database.DatabaseSaveData;
import main.java.breakingPointTool.database.GetAnalysisDataJava;
import main.java.breakingPointTool.externalTools.MetricsCalculator;
import main.java.breakingPointTool.externalTools.RippleEffectChangeProneness;
import main.java.breakingPointTool.versions.Versions;


/* This tool is called Breaking Point Tool and calculates quality metrics for Java, C++ and C
 * such as maintainability metrics and technical debt metrics such as principal, interest and breaking point.
 * 
 * It uses some other support tools.
 * 1) SonarQube (needs to be installed)
 * 2) Metrics Calculator for Java metrics (jar)
 * 3) Metrics Calculator for C (jar)
 * 4) Metrics Calculator for C++ (jar)
 * At the end, writes all the calculated metrics in a database.
 * 
 * The analysis occurs in class, package and project level. 
 * 
 * Prerequisites:
 * 1) A number of software versions
 * 2) A jar file for every software version for java code
 * 3) Specific structure of code - project -> package -> class (src/main/java) for java code
 */

public class BreakingPointTool 
{
	private static Scanner keyboard;
	
	public static void main(String [ ] args) throws IOException, InterruptedException, NumberFormatException, SQLException
	{	
		// Passing parameters through stream 
		String projectName = null, jarName = null, language = null, path=null, server=null;
		int versionsNum = 0;
		int typeAnalysis = 0;

		ArrayList <String> artifactLongNamesPackage = new ArrayList<>();

		ArrayList<FindSimilarArtifacts> similarCl = new ArrayList<FindSimilarArtifacts>();
		ArrayList<FindSimilarArtifacts> similarPck = new ArrayList<FindSimilarArtifacts>();

		ArrayList<FindSimilarArtifactsC> similarClC = new ArrayList<FindSimilarArtifactsC>();
		ArrayList<FindSimilarArtifactsC> similarPckC = new ArrayList<FindSimilarArtifactsC>();
		//ArrayList<FindSimilarProjects> similarPrj = new ArrayList<FindSimilarProjects>();

		// NEW DATA STRUCTURES
		ProjectArtifact projectArtifacts = new ProjectArtifact();	

		if(args.length != 0)
		{		
			try {
				projectName = args[0];
				jarName = args[1];
				language = args[2];
				versionsNum = Integer.parseInt(args[3]);
				path = args[4];
				typeAnalysis = Integer.parseInt(args[5]);
				server = args[6];
			}
			catch (ArrayIndexOutOfBoundsException e){
				System.out.println("Error: You should pass 5 parameters! 1) Project Name 2) JarName 3) Language 4) Num of Versions "
						+ "5) Path to the actuall java code");
			}
		}
		else
		{
			keyboard = new Scanner(System.in);
			System.out.println("Please type programming language for analysis: \nAvailable options:\n  1) Java \n"
					+ "  2) C");
			language = keyboard.nextLine();
			System.out.println("Type the project Name: ");
			projectName = keyboard.nextLine();
			System.out.println("Type the number of versions: ");
			versionsNum = keyboard.nextInt();
			keyboard.nextLine(); // Consume newline left-over
			System.out.println("Type the projects jar file name: "); 
			jarName = keyboard.nextLine();	
			System.out.println("Type path of main project for example: src/main/java/ "); 
			path = keyboard.nextLine();	
			System.out.println("Type 1 for new analysis or 2 for a new version analysis to existed project"); 
			typeAnalysis = keyboard.nextInt();	
			System.out.println("Server Name: protocol/host/port");
			server = keyboard.nextLine();	
		}

		// NEW DATA STRUCTURES
		projectArtifacts.setprojectName(projectName);
		projectArtifacts.setNumOfVersions(versionsNum);

		// SonarQube Execution
		/*String line;
		String exec = null ;
		String projectPath = null;
		String jarPath = null;
		String serverUrl = null;
		String username = "", password = "";*/


		/*
		 * //read specific version file java.net.URL jarLocationUrl =
		 * BreakingPointTool.class.getProtectionDomain().getCodeSource().getLocation();
		 * String jarLocation = new File(jarLocationUrl.toString()).getParent();
		 * 
		 * jarLocation = jarLocation.replace("file:", "");
		 * 
		 * br = new BufferedReader(new FileReader(jarLocation + "/configurations.txt"));
		 * //System.out.println("Version: " + i); while ((line = br.readLine()) != null)
		 * { if (line.contains("#")) continue;
		 * 
		 * if (line.contains("sonarqube_execution:")) { String[] temp =
		 * line.split("sonarqube_execution:"); exec = temp[1]; }
		 * 
		 * if (line.contains("project_path:")) { String[] temp =
		 * line.split("project_path:"); projectPath = temp[1]; }
		 * 
		 * if (line.contains("jar_path:")) { String[] temp = line.split("jar_path:");
		 * jarPath = temp[1]; }
		 * 
		 * if (line.contains("sonar_url:")) { String[] temp = line.split("sonar_url:");
		 * serverUrl = temp[1]; }
		 * 
		 * if (line.contains("usernameSQ:")) { if (line.length() > 11) { String[] temp =
		 * line.split("usernameSQ:"); username = temp[1]; } }
		 * 
		 * if (line.contains("passwordSQ:")) { if (line.length() > 11) { String[] temp =
		 * line.split("passwordSQ:"); password = temp[1]; } } }
		 * sonarQube(exec,projectName, versionsNum, projectPath, language, serverUrl,
		 * username, password);
		 * 
		 */
		if (language.equals("Java"))
		{
			// Databases call for every level		
			DatabaseGetData dbCall = new DatabaseGetData(projectName);	

			if (typeAnalysis == 1) {
				for (int i = 0; i < versionsNum; i ++)
				{
					// Ripple Effect and Change Proneness Measure Execution
					RippleEffectChangeProneness rem = new RippleEffectChangeProneness();
					rem.ExtractJar(jarName, i, "", projectName);

					// Metrics Calculator Execution
					MetricsCalculator metricsCalc = new MetricsCalculator();
					// sta  "" jarPath
					metricsCalc.executeOneVersion(jarName, i, "", projectName);

					dbCall.getDirectoriesForProject(projectName, dbCall.getProjectsKees().get(i));
					dbCall.getClassesForProject(projectName, dbCall.getProjectsKees().get(i));
					Versions v = new Versions();
					v.setProjectName(projectName);
					v.setVersionId(i);			

					// Get metrics from Sonar API for every level
					ApiCall metricsFromSonarClassLevel = new ApiCall(server);
					metricsFromSonarClassLevel.getMetricsFromApiSonarClassLevel(dbCall.getClassesId(), language, path);
					metricsFromSonarClassLevel.getTDFromApiSonar(dbCall.getClassesId());
					metricsFromSonarClassLevel.getMetricCommentsDensityFromApi(dbCall.getClassesId());

					ApiCall metricsFromSonarPackageLevel = new ApiCall(server);
					metricsFromSonarPackageLevel.getMetricsFromSonarPackageLevel(dbCall.getPackagesId(), language, path);
					metricsFromSonarPackageLevel.getTDFromApiSonar(dbCall.getPackagesId());
					metricsFromSonarPackageLevel.getMetricCommentsDensityFromApi(dbCall.getPackagesId());

					for (String pc : dbCall.getPackagesId())
					{
						if (pc.contains(path))
						{			
							String[] tempPackage = pc.split(path);

							if (tempPackage.length == 1)
								continue;

							artifactLongNamesPackage.add(tempPackage[1]); 
						}

					}			

					// Set metrics from metrics calculator at class and package level
					AverageLocCalculation calcAverageAllLevels = new AverageLocCalculation();
					calcAverageAllLevels.setMetricsToClassLevel(projectName, i);
					calcAverageAllLevels.setClassToPackageLevel(artifactLongNamesPackage,projectName, i);	

					v.setPackageInProject(calcAverageAllLevels.getObjectsPackageMetrics());
					artifactLongNamesPackage.clear();

					// Set metrics from sonar at class and metrics level				

					for (int j = 0; j < v.getPackages().size(); j++)
					{
						// set sonar metrics in package level
						for (int l = 0; l < metricsFromSonarPackageLevel.getArtifactNames().size(); l++)
						{
							//System.out.println("Check first name: "  +  metricsFromSonarPackageLevel.getArtifactNames().get(l));
							//System.out.println("Check second name: " + v.getPackages().get(j).getPackageName());
							if (metricsFromSonarPackageLevel.getArtifactNames().get(l).equals(v.getPackages().get(j).getPackageName()))
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
								saveInDataBase.savePrincipalMetrics(metricsFromSonarPackageLevel.getArtifactNames().get(l), projectName, i, 
										metricsFromSonarPackageLevel.getTechnicalDebt().get(l), 0, metricsFromSonarPackageLevel.getCodeSmells().get(l), 
										metricsFromSonarPackageLevel.getBugs().get(l), metricsFromSonarPackageLevel.getVulnerabilities().get(l), 
										metricsFromSonarPackageLevel.getDuplicationsDensity().get(l), "DIR", metricsFromSonarPackageLevel.getNumOfClasses().get(l),
										metricsFromSonarPackageLevel.getComplexity().get(l), metricsFromSonarPackageLevel.getFunctions().get(l),
										metricsFromSonarPackageLevel.getNcloc().get(l), metricsFromSonarPackageLevel.getStatements().get(l),
										0, language);
							}
						}

						// set sonar metrics in class level
						for (int k = 0; k < v.getPackages().get(j).getClassInProject().size(); k++)
						{
							for (int l = 0; l < metricsFromSonarClassLevel.getArtifactNames().size(); l++)
							{
								//System.out.println("++++ " + metricsFromSonarClassLevel.getArtifactNames().get(l));
								//System.out.println("&&&&& " + v.getPackages().get(j).getClassInProject().get(k).getClassName());
								if (metricsFromSonarClassLevel.getArtifactNames().get(l).equals(v.getPackages().get(j).getClassInProject().get(k).getClassName()))
								{
									//System.out.println("$$$$" + metricsFromSonarClassLevel.getTechnicalDebt().get(l));
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
									saveInDataBase.savePrincipalMetrics(metricsFromSonarClassLevel.getArtifactNames().get(l), projectName, i, 
											metricsFromSonarClassLevel.getTechnicalDebt().get(l), 0, metricsFromSonarClassLevel.getCodeSmells().get(l), 
											metricsFromSonarClassLevel.getBugs().get(l), metricsFromSonarClassLevel.getVulnerabilities().get(l), 
											metricsFromSonarClassLevel.getDuplicationsDensity().get(l), "FIL", metricsFromSonarClassLevel.getNumOfClasses().get(l),
											metricsFromSonarClassLevel.getComplexity().get(l), metricsFromSonarClassLevel.getFunctions().get(l),
											metricsFromSonarClassLevel.getNcloc().get(l), metricsFromSonarClassLevel.getStatements().get(l),
											0, language);

								}
							}

						}
					}			
					projectArtifacts.setVersion(v);
					dbCall.clearData();

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

					OptimalArtifact optimalPackage = new OptimalArtifact();
					optimalPackage.calculateOptimalPackage(similarPck, i);
				}
			}
			else if (typeAnalysis == 2)
			{
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
				rem.ExtractJar(jarName, versionsNum-1, "", projectName);

				// Metrics Calculator Execution
				MetricsCalculator metricsCalc = new MetricsCalculator();
				// sta  "" jarPath
				metricsCalc.executeOneVersion(jarName, versionsNum-1, "", projectName);
			
				// Get directories and files names from SonarQube DB Last Version
				dbCall.getDirectoriesForProject(projectName, dbCall.getProjectsKees().get(versionsNum - 1));
				dbCall.getClassesForProject(projectName, dbCall.getProjectsKees().get(versionsNum - 1));			

				// Get metrics from Sonar API for every level - NEW VERSION
				ApiCall metricsFromSonarClassLevel = new ApiCall(server);
				metricsFromSonarClassLevel.getMetricsFromApiSonarClassLevel(dbCall.getClassesId(), language, path);
				metricsFromSonarClassLevel.getTDFromApiSonar(dbCall.getClassesId());
				metricsFromSonarClassLevel.getMetricCommentsDensityFromApi(dbCall.getClassesId());

				ApiCall metricsFromSonarPackageLevel = new ApiCall(server);
				metricsFromSonarPackageLevel.getMetricsFromSonarPackageLevel(dbCall.getPackagesId(), language, path);
				metricsFromSonarPackageLevel.getTDFromApiSonar(dbCall.getPackagesId());
				metricsFromSonarPackageLevel.getMetricCommentsDensityFromApi(dbCall.getPackagesId());
				
				for (String pc : dbCall.getPackagesId())
				{
					if (pc.contains(path))
					{			
						String[] tempPackage = pc.split(path);

						if (tempPackage.length == 1)
							continue;

						artifactLongNamesPackage.add(tempPackage[1]); 
					}

				}
				
				// Set metrics from metrics calculator at class and package level
				AverageLocCalculation calcAverageAllLevels = new AverageLocCalculation();
				// Save Metrics Calculator Class Metrics
				calcAverageAllLevels.setMetricsToClassLevel(projectName, versionsNum-1);
				// Save Metrics Calculator Package Metrics
				calcAverageAllLevels.setClassToPackageLevel(artifactLongNamesPackage,projectName, versionsNum-1);
				
				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! maybe DELETE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				Versions v = new Versions();
				v.setProjectName(projectName);
				v.setVersionId(versionsNum-1);

				
				v.setPackageInProject(calcAverageAllLevels.getObjectsPackageMetrics());
				artifactLongNamesPackage.clear();

				
				// Merge sonar and metrics calculator values for new version				

				for (int j = 0; j < v.getPackages().size(); j++)
				{
					// set sonar metrics in package level
					for (int l = 0; l < metricsFromSonarPackageLevel.getArtifactNames().size(); l++)
					{
						//System.out.println("Check first name: "  +  metricsFromSonarPackageLevel.getArtifactNames().get(l));
						//System.out.println("Check second name: " + v.getPackages().get(j).getPackageName());
						if (metricsFromSonarPackageLevel.getArtifactNames().get(l).equals(v.getPackages().get(j).getPackageName()))
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
							saveInDataBase.savePrincipalMetrics(metricsFromSonarPackageLevel.getArtifactNames().get(l), projectName, versionsNum -1, 
									metricsFromSonarPackageLevel.getTechnicalDebt().get(l), 0, metricsFromSonarPackageLevel.getCodeSmells().get(l), 
									metricsFromSonarPackageLevel.getBugs().get(l), metricsFromSonarPackageLevel.getVulnerabilities().get(l), 
									metricsFromSonarPackageLevel.getDuplicationsDensity().get(l), "DIR", metricsFromSonarPackageLevel.getNumOfClasses().get(l),
									metricsFromSonarPackageLevel.getComplexity().get(l), metricsFromSonarPackageLevel.getFunctions().get(l),
									metricsFromSonarPackageLevel.getNcloc().get(l), metricsFromSonarPackageLevel.getStatements().get(l),
									0, language);
						}
					}

					// set sonar metrics in class level
					for (int k = 0; k < v.getPackages().get(j).getClassInProject().size(); k++)
					{
						for (int l = 0; l < metricsFromSonarClassLevel.getArtifactNames().size(); l++)
						{
							//System.out.println("++++ " + metricsFromSonarClassLevel.getArtifactNames().get(l));
							//System.out.println("&&&&& " + v.getPackages().get(j).getClassInProject().get(k).getClassName());
							if (metricsFromSonarClassLevel.getArtifactNames().get(l).equals(v.getPackages().get(j).getClassInProject().get(k).getClassName()))
							{
								//System.out.println("$$$$" + metricsFromSonarClassLevel.getTechnicalDebt().get(l));
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
								saveInDataBase.savePrincipalMetrics(metricsFromSonarClassLevel.getArtifactNames().get(l), projectName, versionsNum-1, 
										metricsFromSonarClassLevel.getTechnicalDebt().get(l), 0, metricsFromSonarClassLevel.getCodeSmells().get(l), 
										metricsFromSonarClassLevel.getBugs().get(l), metricsFromSonarClassLevel.getVulnerabilities().get(l), 
										metricsFromSonarClassLevel.getDuplicationsDensity().get(l), "FIL", metricsFromSonarClassLevel.getNumOfClasses().get(l),
										metricsFromSonarClassLevel.getComplexity().get(l), metricsFromSonarClassLevel.getFunctions().get(l),
										metricsFromSonarClassLevel.getNcloc().get(l), metricsFromSonarClassLevel.getStatements().get(l),
										0, language);

							}
						}

					}
				}
					projectArtifacts.setVersion(v);
					dbCall.clearData();
				
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

				OptimalArtifact optimalPackage = new OptimalArtifact();
				optimalPackage.calculateOptimalPackage(similarPck, versionsNum-1);
								

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
					System.out.println("LOC: " +  projectArtifacts.getVersions().get(i).getPackages().get(j).getAverageNocChange());


					for (int x = 0; x < projectArtifacts.getVersions().get(i).getPackages().get(j).getClassInProject().size(); x++)
					{
						System.out.println("Class : " + projectArtifacts.getVersions().get(i).getPackages().get(j).getClassInProject().get(x).getClassName());
						System.out.println("Size1 : " + projectArtifacts.getVersions().get(i).getPackages().get(j).getClassInProject().get(x).getSize1());
						System.out.println("TD : " + projectArtifacts.getVersions().get(i).getPackages().get(j).getClassInProject().get(x).getTD());
						System.out.println("LOC : " + projectArtifacts.getVersions().get(i).getPackages().get(j).getClassInProject().get(x).getAverageNocChange());
					}
				}
				System.out.println("---------");
			}
			 */
		}
		else if (language.equals("C"))
		{
			// Databases call for every level		
			DatabaseGetData dbCall = new DatabaseGetData(projectName);	
			if (typeAnalysis == 1) 
			{
				for (int i = 0; i < versionsNum; i ++)
				{
					dbCall.getDirectoriesForProject(projectName, dbCall.getProjectsKees().get(i));
					dbCall.getClassesForProject(projectName, dbCall.getProjectsKees().get(i));
					Versions v = new Versions();
					v.setProjectName(projectName);
					v.setVersionId(i);	

					// Get metrics from Sonar API for every level
					ApiCall metricsFromSonarClassLevel = new ApiCall(server);

					metricsFromSonarClassLevel.getMetricsFromApiSonarClassLevel(dbCall.getClassesId(), language, path);
					metricsFromSonarClassLevel.getTDFromApiSonar(dbCall.getClassesId());
					metricsFromSonarClassLevel.getMetricCommentsDensityFromApi(dbCall.getClassesId());

					ApiCall metricsFromSonarPackageLevel = new ApiCall(server);
					metricsFromSonarPackageLevel.getMetricsFromSonarPackageLevel(dbCall.getPackagesId(), language, path);
					metricsFromSonarPackageLevel.getTDFromApiSonar(dbCall.getPackagesId());
					metricsFromSonarPackageLevel.getMetricCommentsDensityFromApi(dbCall.getPackagesId());

					for (String pc : dbCall.getPackagesId())
					{
						//System.out.println("check before: " + pc);
						if (pc.contains("test"))
							continue;
						if (pc.contains("res"))
							continue;
						if (pc.contains("tools"))
							continue;
						//String[] tempPackage = pc.split("src/main/java/");
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
					calcAverageAllLevels.setMetricsToClassLevel(metricsFromSonarClassLevel, projectName, i);
					calcAverageAllLevels.setClassToPackageLevel(artifactLongNamesPackage,projectName, i, metricsFromSonarPackageLevel);

					v.setPackageInProjectC(calcAverageAllLevels.getObjectsPackageMetrics());
					artifactLongNamesPackage.clear();

					projectArtifacts.setVersion(v);
					dbCall.clearData();

				} 
				
				// Calculate LOC
				AverageLocCalculationC calcAverageAllLevels = new AverageLocCalculationC();
				calcAverageAllLevels.calculateLocPackageLevel(projectArtifacts);
				calcAverageAllLevels.calculateLocClassLevel(projectArtifacts);
				
				for (int i = 0; i < versionsNum; i++)
				{
					// Find similar classes
					FindSimilarArtifactsC similarArtifacts = new FindSimilarArtifactsC();
					similarClC = similarArtifacts.calculateSimilarityForClasses(projectArtifacts, i, typeAnalysis);

					// Find similar packages
					similarPckC = similarArtifacts.calculateSimilarityForPackages(projectArtifacts, i, typeAnalysis);

					// Calculate Optimal Class
					OptimalArtifactC optimalClass = new OptimalArtifactC();
					optimalClass.calculateOptimalClass(similarClC, i);

					OptimalArtifactC optimalPackage = new OptimalArtifactC();
					optimalPackage.calculateOptimalPackage(similarPckC, i); 
				}
			}

			else if (typeAnalysis == 2)
			{
				// Get metrics from Database from previous version
				GetAnalysisDataJava classMetrics = new GetAnalysisDataJava();
				classMetrics.getAnalysisDataC(projectName, "FIL", versionsNum - 2);
				classMetrics.getAnalysisDataBPTC(projectName, "FIL", versionsNum - 2);
				
				GetAnalysisDataJava packageMetrics = new GetAnalysisDataJava();				
				packageMetrics.getAnalysisDataC(projectName, "DIR", versionsNum - 2);
				packageMetrics.getAnalysisDataBPTC(projectName, "DIR", versionsNum - 2);
				
				dbCall.getDirectoriesForProject(projectName, dbCall.getProjectsKees().get(versionsNum - 1));
				dbCall.getClassesForProject(projectName, dbCall.getProjectsKees().get(versionsNum - 1));
				
				Versions v = new Versions();
				v.setProjectName(projectName);
				v.setVersionId(versionsNum-1);	

				// Get metrics from Sonar API for every level
				ApiCall metricsFromSonarClassLevel = new ApiCall(server);

				metricsFromSonarClassLevel.getMetricsFromApiSonarClassLevel(dbCall.getClassesId(), language, path);
				metricsFromSonarClassLevel.getTDFromApiSonar(dbCall.getClassesId());
				metricsFromSonarClassLevel.getMetricCommentsDensityFromApi(dbCall.getClassesId());

				ApiCall metricsFromSonarPackageLevel = new ApiCall(server);
				metricsFromSonarPackageLevel.getMetricsFromSonarPackageLevel(dbCall.getPackagesId(), language, path);
				metricsFromSonarPackageLevel.getTDFromApiSonar(dbCall.getPackagesId());
				metricsFromSonarPackageLevel.getMetricCommentsDensityFromApi(dbCall.getPackagesId());
				
				for (String pc : dbCall.getPackagesId())
				{
					//System.out.println("check before: " + pc);
					if (pc.contains("test"))
						continue;
					if (pc.contains("res"))
						continue;
					if (pc.contains("tools"))
						continue;
					//String[] tempPackage = pc.split("src/main/java/");
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
				calcAverageAllLevels.setMetricsToClassLevel(metricsFromSonarClassLevel, projectName, versionsNum-1);
				calcAverageAllLevels.setClassToPackageLevel(artifactLongNamesPackage,projectName, versionsNum-1, metricsFromSonarPackageLevel);

				v.setPackageInProjectC(calcAverageAllLevels.getObjectsPackageMetrics());
				artifactLongNamesPackage.clear();

				projectArtifacts.setVersion(v);
				dbCall.clearData();
				
				// TODO change this
				
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

				OptimalArtifactC optimalPackage = new OptimalArtifactC();
				optimalPackage.calculateOptimalPackage(similarPckC, versionsNum-1); 


			}

			

			/*	// CHECK DATA PRINT
						for (int i = 0; i < versionsNum; i++)
						{
							System.out.println("Project ID: " + projectArtifacts.getProjectName());
							System.out.println("Version ID: " + projectArtifacts.getVersions().get(i).getVersionId());
							for (int j = 0; j < projectArtifacts.getVersions().get(i).getPackagesC().size(); j++)
							{
								System.out.println("Package: " +  projectArtifacts.getVersions().get(i).getPackagesC().get(j).getPackageName());
								System.out.println("Size1: " +  projectArtifacts.getVersions().get(i).getPackagesC().get(j).getNcloc());
								System.out.println("TD: " +  projectArtifacts.getVersions().get(i).getPackagesC().get(j).getTD());
								System.out.println("Commends Density: " +  projectArtifacts.getVersions().get(i).getPackagesC().get(j).getCommentsDensity());


								for (int x = 0; x < projectArtifacts.getVersions().get(i).getPackagesC().get(j).getClassInProject().size(); x++)
								{
									System.out.println("Class : " + projectArtifacts.getVersions().get(i).getPackagesC().get(j).getClassInProject().get(x).getClassName());
									System.out.println("Size1 : " + projectArtifacts.getVersions().get(i).getPackagesC().get(j).getClassInProject().get(x).getNcloc());
									System.out.println("TD : " + projectArtifacts.getVersions().get(i).getPackagesC().get(j).getClassInProject().get(x).getTD());
									System.out.println("Commends Density : " + projectArtifacts.getVersions().get(i).getPackagesC().get(j).getClassInProject().get(x).getCommentsDensity());
								}
							}
							System.out.println("---------");
						}*/


			
		}
		else
		{
			System.out.println("Programming language does not supported. Execute the software again and choose one of the available options.");
		}
	}

	public static void sonarQube (String exec, String projectName, int versions, String projectPath, String language, String serverUrl, String username, String password) throws FileNotFoundException, InterruptedException
	{
		String fileName = "sonar-project.properties";
		for (int i = 0; i < versions; i++) 
		{
			// Create sonar-properties file
			File newFile = new File(projectPath + File.separator + projectName + File.separator + projectName+ i + File.separator + fileName);
			String key = projectName + i;

			System.out.println("Execute version: " + i);
			PrintWriter printWriter = new PrintWriter (newFile);
			printWriter.println ("# Required metadata");
			printWriter.println ("sonar.projectKey=" + key); 
			printWriter.println ("sonar.projectName=" + projectName);
			printWriter.println ("sonar.projectVersion=" + i);		    
			printWriter.println ("sonar.projectDate=2019-08-25");
			printWriter.println ();

			printWriter.println ("# Comma-separated paths to directories with sources (required)");
			printWriter.println ("sonar.sources=.");
			printWriter.println ();

			printWriter.println ("# Language");
			printWriter.println ("sonar.language=" + language.toLowerCase());
			printWriter.println ();

			printWriter.println ("# Encoding of the source files");		    
			printWriter.println ("sonar.sourceEncoding=UTF-8");
			printWriter.println ("sonar.java.binaries=.");
			printWriter.println ("sonar.projectBaseDir=" + projectPath + File.separator + projectName + File.separator + projectName+ i + File.separator);

			// Default : http://localhost:9000
			printWriter.println ("# Server Configuration");
			printWriter.println ("sonar.host.url=" + serverUrl); 
			printWriter.println ("sonar.login=" + username);
			// Password is blank if token is used
			printWriter.println ("sonar.password=" + password);
			printWriter.close ();

			// Execute SonarQube
			try { 
				int ch; 
				System.out.println("inside try");
				ProcessBuilder pb = new ProcessBuilder(exec); 
				pb.directory(new File(projectPath + File.separator + projectName + File.separator + projectName+ i + File.separator)); 

				System.out.println("File: " + projectPath + File.separator + projectName + File.separator + projectName+ i + File.separator );
				Process shellProcess = pb.start();  
				shellProcess.waitFor(); 

				InputStreamReader myIStreamReader = new 
						InputStreamReader(shellProcess.getInputStream()); 

				while ((ch = myIStreamReader.read()) != -1) { 
					System.out.print((char)ch); 
				} 
			} catch (IOException anIOException) { 
				System.out.println(anIOException); 
			} catch(Exception e) { 
				e.printStackTrace(); 

			} 

			System.out.println("execution done");

		}
	}

	//TODO delete all files and folders from external tools
	public static void deleteFilesandFolders()
	{

	}

}
