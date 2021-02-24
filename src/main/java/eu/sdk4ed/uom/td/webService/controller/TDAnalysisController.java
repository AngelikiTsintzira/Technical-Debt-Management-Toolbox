package eu.sdk4ed.uom.td.webService.controller;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.json.JSONException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import eu.sdk4ed.uom.td.analysis.GitClone.GitCloneProject;
import eu.sdk4ed.uom.td.analysis.api.CommitShas;
import eu.sdk4ed.uom.td.analysis.api.SonarQubeArtifacts;
import eu.sdk4ed.uom.td.analysis.api.SonarQubeMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.ArtifactMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.ProjectArtifact;
import eu.sdk4ed.uom.td.analysis.artifact.SimilarArtifacts;
import eu.sdk4ed.uom.td.analysis.artifact.OptimalArtifact;
import eu.sdk4ed.uom.td.analysis.calculations.InterestMetricsCalculation;
import eu.sdk4ed.uom.td.analysis.calculations.Similarity;
import eu.sdk4ed.uom.td.analysis.database.DatabaseSaveData;
import eu.sdk4ed.uom.td.analysis.database.TablesCreation;
import eu.sdk4ed.uom.td.analysis.externalTools.ExternalMetricsCalculationTools;
import eu.sdk4ed.uom.td.analysis.externalTools.Sonarqube;
import eu.sdk4ed.uom.td.analysis.preperation.ConfigurationFile;
import eu.sdk4ed.uom.td.analysis.preperation.deletePreAnalysedData;
import eu.sdk4ed.uom.td.analysis.versions.Versions;

@RestController
@RequestMapping(value = "/tdanalysis")
public class TDAnalysisController extends BaseController {

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/execute")
	public void execute(@RequestParam(value = "language", required = true) String language,
			@RequestParam(value = "typeAnalysis", required = true) int typeAnalysis,
			@RequestParam(value = "gitUrl", required = true) String gitUrl,
			@RequestParam(value = "gitUsername", required = true) String gitUsername,
			@RequestParam(value = "gitPassword", required = true) String gitPassword,
			@RequestParam(value = "moveClassRefactoring", required = true) boolean mcr,
			@RequestParam(value = "extractMethodRefactoring", required = true) boolean extractMethod) throws SQLException, IOException, InterruptedException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException, JSONException, SAXException, ParserConfigurationException, URISyntaxException {
		logger.info("> execute language: {}, typeAnalysis: {}, gitUrl: {}",
				language, typeAnalysis, gitUrl);

		deletePreAnalysedData del = new deletePreAnalysedData();

		ProjectArtifact projectArtifacts = new ProjectArtifact();	

		ConfigurationFile conFile = new ConfigurationFile ();

		// Set Git repo name as a common unique project identifier
		String project_name = gitUrl.replace(".git","");
		int sepPos = project_name.lastIndexOf("/");

		if (sepPos == -1) 
		{
			System.out.println("Bad Git URL. Please try again!");
			return;
		}

		String projectName = project_name.substring(sepPos + 1, project_name.length());
		System.out.println("Project Name from GIT is = "+ projectName);

		// Get SHAS from commits
		ArrayList<String> shas = new ArrayList<String>();

		CommitShas commitShas = new CommitShas();
		shas = commitShas.getShas(gitUrl, gitUsername, gitPassword);
		
		if (projectName.equals("arassistance"))
		{
			shas.clear();
			shas.add("34d5774fde42f84c5a481cb943ea5aec8c7e7aec");
			shas.add("362186c0ec757582a747728db4ea941e233eb474");
			shas.add("57142d6744b3865e6ea9c67a08a15d99de536730");
			shas.add("ecdc1fda10bf0dce126e7c597be060606247af3c");
			shas.add("a86fb0388c03a64c2aa1b96c4134f0e83b5faa3f");
			shas.add("a607a2a44fef4805be563dcad1441c11495c82c9");
			shas.add("d0b4593db0b7ed44f0464518c15408d307655719");
			shas.add("639c50fc048bf37e17e7f97e09440236b5011cf2");
			shas.add("f99406c24958859260c7d6651310df504609e5e0");
			shas.add("4db0a26467735ba435fcb021a45b42f95542b7f1");
		}
		
		if (projectName.equals("sdk4ed-healthcare-usecase-neu"))
		{
			shas.clear();
			shas.add("476e311ea0653759ca3696a92c14e6e47cc64d82");
			shas.add("f024614b08804d00d187dd247ecbdb0eafef7147");
			shas.add("13e9adaee684caa0d196e76a03451d0ac55079c2");
			shas.add("3ade432f15eae1e52388ae90677dc9773ccfedd7");
			shas.add("bdb97cd35a7990f01605e8ce2a94c9cf1930c7c7");
			shas.add("69795940ce0e14ed0d4648d9fcdfe119fb1e29d7");
			shas.add("12a3f6d3e16d6aa488401c0a71ad282c104928a2");
			shas.add("4817e682478561d53ac036ccc8252608757de805");
			shas.add("100ba48b97e4dba57144451b19410eca7adb792a");
			shas.add("ef0abac266e439bdbe200580eed4f8a4732d040f");
			
		}
		
		if (projectName.equals("kameleon-sdk4ed"))
		{
			shas.clear();
			shas.add("c3d18b78e981f39375d3aa2c19605276b5f9d054");
			shas.add("f8c49ccdf7d927c3742ede88667fca07792a6bb4");
			shas.add("459b7ef81bf5c7494765a2fb2f399727495a03a2");
			shas.add("1897c6afbb6ef0def98653a67554963de4a6ff14");
			shas.add("0b560bcbe38704c187fa39a97b797a322e4e0f18");
		}

		int versionsNum = shas.size();
		System.out.println("Shas number: " + versionsNum);

		// Keep only last commit
		if (typeAnalysis == 2)
		{
			String s = shas.get(shas.size() - 1); 
			shas.clear();
			shas.add(s);
		}

		projectArtifacts.setprojectName(projectName);
		projectArtifacts.setNumOfVersions(versionsNum);
		projectArtifacts.setLanguage(language);

		// Configuration File, get all data from file and make the jar works in every machine
		String projectPath = "";

		String jarLocation = System.getProperty("user.dir");
		String credentials = System.getProperty("user.dir") + "/configurations.txt";

		if (!conFile.readConfigurationFile())
		{
			System.out.println("Missing config file. Program will terminate");
			return;
		}
		
		GitCloneProject git = new GitCloneProject();

		git.cloneCommits(jarLocation, gitUsername, gitPassword, shas, gitUrl, projectName, versionsNum);

		projectPath = git.getProjectPath();
		projectPath = projectPath.replace(projectName, "");

		// Execute SonarQube
		Sonarqube sonar = new Sonarqube();
		sonar.sonarQube(conFile.getSonarQubeExec(), projectName, versionsNum, projectPath, language, conFile.getSonarUrl(), conFile.getSQUsername(), conFile.getSQPassword(), typeAnalysis); 
		// Create database tables
		TablesCreation tables = new TablesCreation();
		tables.createDatabaseTables();

		// If the project has records on DB delete them
		if (typeAnalysis == 1) 
			del.DeleteFromDBAlreadyAnalysedProject(projectName, language);
		else
			del.DeleteFromDBAlreadyAnalysedVersion(projectName,language, versionsNum-1);

		String javaRunningDirectory = System.getProperty("user.dir");
		String cloneDirectoryPath = javaRunningDirectory + "/Projects/" + projectName;

		if (language.equals("Java"))
		{
			if (typeAnalysis == 1) {

				DatabaseSaveData dbSave = new DatabaseSaveData();
				for (int i = 0; i < versionsNum; i ++)
				{	
					dbSave.saveTimestamp(projectName, i);
				}

				for (int i = 0; i < versionsNum; i ++)
				{
					// Databases call for every level		
					SonarQubeArtifacts artifacts = new SonarQubeArtifacts(conFile.getSonarUrl());

					artifacts.getArtifactsName(projectName + i, "FIL");
					artifacts.getArtifactsName(projectName + i, "DIR");

					System.out.println("Sonar File: " + artifacts.getClassesId().size());
					System.out.println("Sonar Folders: " + artifacts.getPackagesId().size());

					Versions v = new Versions();
					v.setProjectName(projectName);
					v.setVersionId(i);			

					// Get metrics from Sonar API for file and folder level
					SonarQubeMetrics metricsFromSonarClassLevel = new SonarQubeMetrics(conFile.getSonarUrl());
					metricsFromSonarClassLevel.getPrincipalMetricsFromSonarqube(artifacts.getClassesId(), "FIL", projectName, language, shas.get(i),  i);

					SonarQubeMetrics metricsFromSonarPackageLevel = new SonarQubeMetrics(conFile.getSonarUrl());
					metricsFromSonarPackageLevel.getPrincipalMetricsFromSonarqube(artifacts.getPackagesId(), "DIR", projectName, language, shas.get(i), i);

					// Set metrics from metrics calculator at class and package level
					String projectFullPath = projectPath + projectName +  File.separator + projectName + i ;

					InterestMetricsCalculation interestMetrics = new InterestMetricsCalculation();
					ConcurrentHashMap<String, ArtifactMetrics> fileMetrics = interestMetrics.setMetricsToClassLevelJava(projectFullPath, metricsFromSonarClassLevel.getArtifacts(), i);
					ConcurrentHashMap<String, ArtifactMetrics> packageMetrics = interestMetrics.setMetricsToPackageLevel(fileMetrics, metricsFromSonarPackageLevel.getArtifacts());

					v.setArtifacts(packageMetrics);
					projectArtifacts.setVersion(v);

					Similarity similarityFiles = new Similarity();
					similarityFiles.calculateSimilarityBetweenArtifacts(fileMetrics);
					ArrayList<SimilarArtifacts> similarFiles = similarityFiles.getSimilarArtifacts();
					OptimalArtifact optimalFiles = new OptimalArtifact();
					optimalFiles.calculateOptimalArtifactJava(similarFiles);

					Similarity similarityPackages = new Similarity();
					similarityPackages.calculateSimilarityBetweenArtifacts(packageMetrics);
					ArrayList<SimilarArtifacts> similarPackages = similarityPackages.getSimilarArtifacts();
					OptimalArtifact optimalPackages = new OptimalArtifact();
					optimalPackages.calculateOptimalArtifactJava(similarPackages);

					dbSave.deleteTimestamp(projectName, i);
				} 
			}

			else if (typeAnalysis == 2)
			{
				DatabaseSaveData dbSave = new DatabaseSaveData();
				dbSave.saveTimestamp(projectName, versionsNum - 1);

				// Databases call for every level		
				SonarQubeArtifacts artifacts = new SonarQubeArtifacts(conFile.getSonarUrl());

				// Get Artifacts for the new version	
				artifacts.getArtifactsName(projectName + (versionsNum - 1), "FIL");
				artifacts.getArtifactsName(projectName + (versionsNum - 1), "DIR");

				// Get metrics from Sonar API for every level - NEW VERSION
				SonarQubeMetrics metricsFromSonarClassLevel = new SonarQubeMetrics(conFile.getSonarUrl());
				metricsFromSonarClassLevel.getPrincipalMetricsFromSonarqube(artifacts.getClassesId(), "FIL", projectName, language, shas.get(0),  versionsNum - 1);

				SonarQubeMetrics metricsFromSonarPackageLevel = new SonarQubeMetrics(conFile.getSonarUrl());
				metricsFromSonarPackageLevel.getPrincipalMetricsFromSonarqube(artifacts.getPackagesId(), "DIR", projectName, language, shas.get(0),  versionsNum - 1);

				// Set metrics from metrics calculator at class and package level
				String projectFullPath = projectPath + projectName +  File.separator + projectName + (versionsNum-1) ;

				InterestMetricsCalculation interestMetrics = new InterestMetricsCalculation();
				ConcurrentHashMap<String, ArtifactMetrics> fileMetrics = interestMetrics.setMetricsToClassLevelJava(projectFullPath, metricsFromSonarClassLevel.getArtifacts(), 0);
				ConcurrentHashMap<String, ArtifactMetrics> packageMetrics = interestMetrics.setMetricsToPackageLevel(fileMetrics, metricsFromSonarPackageLevel.getArtifacts());

				Versions v = new Versions();
				v.setProjectName(projectName);
				v.setVersionId(versionsNum-1);

				projectArtifacts.setVersion(v);
				artifacts.clearData();

				Similarity similarityFiles = new Similarity();
				similarityFiles.calculateSimilarityBetweenArtifacts(fileMetrics);
				ArrayList<SimilarArtifacts> similarFiles = similarityFiles.getSimilarArtifacts();
				OptimalArtifact optimalFiles = new OptimalArtifact();
				optimalFiles.calculateOptimalArtifactJava(similarFiles);

				Similarity similarityPackages = new Similarity();
				similarityPackages.calculateSimilarityBetweenArtifacts(packageMetrics);
				ArrayList<SimilarArtifacts> similarPackages = similarityPackages.getSimilarArtifacts();
				OptimalArtifact optimalPackages = new OptimalArtifact();
				optimalPackages.calculateOptimalArtifactJava(similarPackages);
				
				dbSave.deleteTimestamp(projectName, versionsNum - 1);
				
			}
		}
		else if (language.equals("C"))
		{
			if (typeAnalysis == 1) 
			{
				DatabaseSaveData dbSave = new DatabaseSaveData();
				for (int i = 0; i < versionsNum; i ++)
				{	
					dbSave.saveTimestamp(projectName, i);
				}

				for (int i = 0; i < versionsNum; i ++)
				{	
					// Databases call for every level		
					SonarQubeArtifacts artifacts = new SonarQubeArtifacts(conFile.getSonarUrl());

					artifacts.getArtifactsName(projectName + i, "FIL");
					artifacts.getArtifactsName(projectName + i, "DIR");

					System.out.println("Sonar File: " + artifacts.getClassesId().size());
					System.out.println("Sonar Folders: " + artifacts.getPackagesId().size());

					Versions v = new Versions();
					v.setProjectName(projectName);
					v.setVersionId(i);			

					// Get metrics from Sonar API for file and folder level
					SonarQubeMetrics metricsFromSonarClassLevel = new SonarQubeMetrics(conFile.getSonarUrl());
					metricsFromSonarClassLevel.getPrincipalMetricsFromSonarqube(artifacts.getClassesId(), "FIL", projectName, language, shas.get(i),  i);

					SonarQubeMetrics metricsFromSonarPackageLevel = new SonarQubeMetrics(conFile.getSonarUrl());
					metricsFromSonarPackageLevel.getPrincipalMetricsFromSonarqube(artifacts.getPackagesId(), "DIR", projectName, language, shas.get(i), i);

					// Set metrics from metrics calculator at class level
					String projectFullPath = projectPath + projectName +  File.separator + projectName + i ;

					InterestMetricsCalculation interestMetrics = new InterestMetricsCalculation();
					interestMetrics.setMetricsToFileLevelC(metricsFromSonarClassLevel.getArtifacts());

					// Coupling and cohesion at file level
					ExternalMetricsCalculationTools moveClass = new ExternalMetricsCalculationTools();
					moveClass.executeAnalysis(projectName, language, projectFullPath, credentials, i, 3);	

					// Set metrics from metrics calculator at package level
					ConcurrentHashMap<String, ArtifactMetrics> packageMetrics = interestMetrics.setMetricsToPackageLevel(metricsFromSonarClassLevel.getArtifacts(), metricsFromSonarPackageLevel.getArtifacts());

					v.setArtifacts(packageMetrics);
					projectArtifacts.setVersion(v);

					Similarity similarityFiles = new Similarity();
					similarityFiles.calculateSimilarityBetweenArtifacts(metricsFromSonarClassLevel.getArtifacts());
					ArrayList<SimilarArtifacts> similarFiles = similarityFiles.getSimilarArtifacts();
					OptimalArtifact optimalFiles = new OptimalArtifact();
					optimalFiles.calculateOptimalArtifactC(similarFiles);

					Similarity similarityPackages = new Similarity();
					similarityPackages.calculateSimilarityBetweenArtifacts(packageMetrics);
					ArrayList<SimilarArtifacts> similarPackages = similarityPackages.getSimilarArtifacts();
					OptimalArtifact optimalPackages = new OptimalArtifact();
					optimalPackages.calculateOptimalArtifactC(similarPackages);

					dbSave.deleteTimestamp(projectName, i);		
				} 
			}

			else if (typeAnalysis == 2)
			{
				DatabaseSaveData dbSave = new DatabaseSaveData();
				dbSave.saveTimestamp(projectName, versionsNum - 1);

				// Databases call for every level		
				SonarQubeArtifacts artifacts = new SonarQubeArtifacts(conFile.getSonarUrl());

				artifacts.getArtifactsName(projectName + (versionsNum - 1), "FIL");
				artifacts.getArtifactsName(projectName + (versionsNum - 1), "DIR");

				// Get metrics from Sonar API for every level - NEW VERSION
				SonarQubeMetrics metricsFromSonarClassLevel = new SonarQubeMetrics(conFile.getSonarUrl());
				metricsFromSonarClassLevel.getPrincipalMetricsFromSonarqube(artifacts.getClassesId(), "FIL", projectName, language, shas.get(0),  versionsNum - 1);

				SonarQubeMetrics metricsFromSonarPackageLevel = new SonarQubeMetrics(conFile.getSonarUrl());
				metricsFromSonarPackageLevel.getPrincipalMetricsFromSonarqube(artifacts.getPackagesId(), "DIR", projectName, language, shas.get(0),  versionsNum - 1);

				// Set metrics from metrics calculator at class level
				String projectFullPath = projectPath + projectName +  File.separator + projectName + (versionsNum-1) ;

				InterestMetricsCalculation interestMetrics = new InterestMetricsCalculation();
				interestMetrics.setMetricsToFileLevelC(metricsFromSonarClassLevel.getArtifacts());

				// Coupling and cohesion at file level
				ExternalMetricsCalculationTools moveClass = new ExternalMetricsCalculationTools();
				moveClass.executeAnalysis(projectName, language, projectFullPath, credentials, versionsNum-1, 3);	

				// Set metrics from metrics calculator at package level
				ConcurrentHashMap<String, ArtifactMetrics> packageMetrics = interestMetrics.setMetricsToPackageLevel(metricsFromSonarClassLevel.getArtifacts(), metricsFromSonarPackageLevel.getArtifacts());

				Versions v = new Versions();
				v.setProjectName(projectName);
				v.setVersionId(versionsNum-1);

				projectArtifacts.setVersion(v);
				artifacts.clearData();

				Similarity similarityFiles = new Similarity();
				similarityFiles.calculateSimilarityBetweenArtifacts(metricsFromSonarClassLevel.getArtifacts());
				ArrayList<SimilarArtifacts> similarFiles = similarityFiles.getSimilarArtifacts();
				OptimalArtifact optimalFiles = new OptimalArtifact();
				optimalFiles.calculateOptimalArtifactC(similarFiles);

				Similarity similarityPackages = new Similarity();
				similarityPackages.calculateSimilarityBetweenArtifacts(packageMetrics);
				ArrayList<SimilarArtifacts> similarPackages = similarityPackages.getSimilarArtifacts();
				OptimalArtifact optimalPackages = new OptimalArtifact();
				optimalPackages.calculateOptimalArtifactC(similarPackages);
				
				dbSave.deleteTimestamp(projectName, versionsNum - 1);
			}
		}
		else
		{
			System.out.println("Programming language does not supported. Execute the software again and choose one of the available options.");
		}

		// Design Refactorings - Extract Long Method
		if (extractMethod == true)
		{
			ExternalMetricsCalculationTools longMethod = new ExternalMetricsCalculationTools();
			longMethod.executeAnalysis(projectName, language, cloneDirectoryPath + File.separator + projectName + (versionsNum -1), credentials, versionsNum -1, 1);
		}

		// Design Refactorings - Move Class Refactorings
		if (mcr == true)
		{
			ExternalMetricsCalculationTools moveClass = new ExternalMetricsCalculationTools();
			moveClass.executeAnalysis(projectName, language, cloneDirectoryPath + File.separator + projectName + (versionsNum -1), credentials, versionsNum -1, 2);
		}

		del.deleteFile(versionsNum);

		// Delete directory after used
		File directory = new File(cloneDirectoryPath);
		del.deleteSourceCode(directory);

		logger.info("< execute projectName: {}, language: {}, versionsNum: {}, typeAnalysis: {}, gitUrl: {}, shas: {}",
				projectName, language, versionsNum, typeAnalysis, gitUrl, shas);
	}
}
