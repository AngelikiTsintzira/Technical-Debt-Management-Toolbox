package eu.sdk4ed.uom.td.analysis.calculations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import eu.sdk4ed.uom.td.analysis.artifact.ArtifactMetrics;
import eu.sdk4ed.uom.td.analysis.database.DatabaseSaveData;
import eu.sdk4ed.uom.td.analysis.database.DatabaseSaveDataC;
import eu.sdk4ed.uom.td.analysis.externalTools.ExternalMetricsCalculationTools;
import eu.sdk4ed.uom.td.analysis.oopMaintainabilityMetrics.calculator.MetricsCalculator;

public class InterestMetricsCalculation 
{
	private BufferedReader br;

	public ConcurrentHashMap<String, ArtifactMetrics> setMetricsToClassLevelJava(String path, ConcurrentHashMap<String, ArtifactMetrics> ArtifactMetrics, int version) throws NumberFormatException, IOException, SQLException, InterruptedException
	{
		String line;
		// in case there duplications
		ArrayList<String> projectsVersionClassNames = new ArrayList<String>();

		// Calculate metrics from source code
		//MetricsCalculator.start(path);
		//br = new BufferedReader(MetricsCalculator.printResults());	
		
		ConcurrentHashMap<String, ArtifactMetrics> editedArtifactMetrics = new ConcurrentHashMap<String, ArtifactMetrics>();
		DatabaseSaveData saveInDataBase = new DatabaseSaveData();
		
		ExternalMetricsCalculationTools moveClass = new ExternalMetricsCalculationTools();
		moveClass.executeAnalysis("", "", path, "", version, 4);
		
		File f = new File("output" + version + ".csv");
		if(!f.exists()) 
		{ 
		    return editedArtifactMetrics;
		}
	
		br = new BufferedReader(new FileReader("output" + version + ".csv"));

		while ((line = br.readLine()) != null) 
		{
			if (line.contains(".")) 
			{
				if (!line.contains("test") && !line.contains("Test")) 
				{
					String[] parts = line.split(";");
					String className = parts[0].replaceAll("\\.", "/");
					
					String fullPathName  = "";
					
					for (Entry<String, ArtifactMetrics> artifactObject : ArtifactMetrics.entrySet()) 
					{
						if (artifactObject.getKey().contains(className))
						{
							fullPathName = artifactObject.getKey();
							break;
						}
					}
					
					if (fullPathName.equals(""))
						continue;
					
					ArtifactMetrics artifact = ArtifactMetrics.get(fullPathName);
					
					// SonarQube and Metrics Calculator may return different artifacts
					// We only keep the artifacts we have metrics from both tools
					if (artifact == null) 
						continue;
						 
					if (projectsVersionClassNames.contains(className)) 
						continue;
					else
						projectsVersionClassNames.add(className);
					
					// wmc = nom
					double mpc = Double.parseDouble(parts[13]);
					double wmc = Double.parseDouble(parts[1]);
					double dit = Double.parseDouble(parts[2]);
					double nocc = Double.parseDouble(parts[9]);
					double rfc = Double.parseDouble(parts[5]);
					double lcom = Double.parseDouble(parts[6]);
					double wmpc = parts[7].contains("-1000") ? 0 : Double.parseDouble(parts[7]);	
					double dac = Double.parseDouble(parts[14]);
					double loc = Double.parseDouble(parts[15]);
					double nop = Double.parseDouble(parts[16]);
					
					artifact.metricsfromMetricsCalculator(mpc, wmc, dit, nocc, rfc, lcom, wmpc, dac, loc, nop);
					artifact.setArtifactName(className);
					
					editedArtifactMetrics.put(className, artifact);

					saveInDataBase.saveMetricsInDatabase(artifact);

					saveInDataBase.savePrincipalMetrics(className, artifact.getProjectName(), artifact.getVersion(), 
							artifact.getTechnicalDebt(), 0, artifact.getCodeSmells(),artifact.getBugs(),
							artifact.getVulnerabilities(),artifact.getDuplications(), artifact.getArtifactType(), 
							artifact.getNumOfClasses(), artifact.getComplexity(), artifact.getFunctions(), artifact.getNcloc(), 
							artifact.getStatements(), artifact.getCommentsDensity(), artifact.getLanguage());
				}
			}
		}
		System.out.println("Size of new hashMap Files: " + editedArtifactMetrics.size());
		return editedArtifactMetrics;
	}

	public ConcurrentHashMap<String, ArtifactMetrics> setMetricsToPackageLevel(ConcurrentHashMap<String, ArtifactMetrics> fileMetrics, ConcurrentHashMap<String, ArtifactMetrics> packageMetrics) throws NumberFormatException, SQLException, IOException
	{		
		DatabaseSaveData saveInDataBase = new DatabaseSaveData();
		
		DatabaseSaveDataC saveInDataBaseC = new DatabaseSaveDataC();
		
		ConcurrentHashMap<String, ArtifactMetrics> editedArtifactMetrics = new ConcurrentHashMap<String, ArtifactMetrics>();
		
		for (Entry<String, ArtifactMetrics> packageObject : packageMetrics.entrySet()) 
		{
			String packageName = packageObject.getKey();
			ArtifactMetrics packageArtifact = packageObject.getValue();
			System.out.println("Package Name: " + packageName );
		
			int flag = 0;
			String filePackageName = "";
			
			for (Entry<String, ArtifactMetrics> fileOblect : fileMetrics.entrySet()) 
			{					
				int index = fileOblect.getValue().getArtifactName().lastIndexOf("/");

				if (index >= 0)
				{
					filePackageName =  fileOblect.getValue().getArtifactName().substring(0,index);
					
					System.out.println("File's Package Name: " +  filePackageName);
					
					if (packageName.endsWith(filePackageName))
					{
						flag = 1;
						
						if (fileOblect.getValue().getLanguage().equals("C"))
							fileOblect.getValue().couplingAndCohesionMetrics(fileOblect.getValue().getArtifactName(), fileOblect.getValue().getVersion());
						
						packageArtifact.setArtifactName(filePackageName);
						System.out.println("New name: " + packageArtifact.getArtifactName());
						packageArtifact.setfilesInPackage(fileOblect.getValue());
					}
				}
			}
			
			if (flag == 0)
				continue;
			
			if (packageArtifact.getLanguage().equals("Java"))
			{
				packageArtifact.calculateMetricsPackageLevelJava();

				saveInDataBase.saveMetricsInDatabase(packageArtifact);

				saveInDataBase.savePrincipalMetrics(packageArtifact.getArtifactName(), packageArtifact.getProjectName(), packageArtifact.getVersion(), 
						packageArtifact.getTechnicalDebt(), 0,
						packageArtifact.getCodeSmells(),packageArtifact.getBugs(),
						packageArtifact.getVulnerabilities(),packageArtifact.getDuplications(), 
						packageArtifact.getArtifactType(), packageArtifact.getNumOfClasses(), 
						packageArtifact.getComplexity(), packageArtifact.getFunctions(), 
						packageArtifact.getNcloc(), packageArtifact.getStatements(),
						packageArtifact.getCommentsDensity(), packageArtifact.getLanguage());
			}
			else 
			{
				packageArtifact.calculateMetricsPackageLevelC();
								
				saveInDataBaseC.saveMetricsInDatabase(packageArtifact.getProjectName(), packageArtifact.getVersion(), packageArtifact.getArtifactName(), 
						packageArtifact.getArtifactType(), packageArtifact.getNcloc(), 
						packageArtifact.getComplexity(), packageArtifact.getFunctions(), 
						packageArtifact.getCommentsDensity(), 0, 0 , packageArtifact.getCommitSha());

				saveInDataBaseC.savePrincipalMetrics(packageArtifact.getArtifactName(), packageArtifact.getProjectName(),packageArtifact.getVersion(), 
						packageArtifact.getTechnicalDebt(), 0,
						packageArtifact.getCodeSmells(),packageArtifact.getBugs(),
						packageArtifact.getVulnerabilities(),packageArtifact.getDuplications(), 
						packageArtifact.getArtifactType(), packageArtifact.getNumOfClasses(), 
						packageArtifact.getComplexity(), packageArtifact.getFunctions(), 
						packageArtifact.getNcloc(), packageArtifact.getStatements(),
						packageArtifact.getCommentsDensity(), packageArtifact.getLanguage());
			}
				
			System.out.println("----------------- Package Saved:  " + filePackageName);
			editedArtifactMetrics.put(packageArtifact.getArtifactName(), packageArtifact);
		}
		
		System.out.println("Size of new hashMap Packages: " + editedArtifactMetrics.size());
		return editedArtifactMetrics;
	}

	public void setMetricsToFileLevelC(ConcurrentHashMap<String, ArtifactMetrics> ArtifactMetrics) throws NumberFormatException, IOException, SQLException, InterruptedException
	{
		for (Entry<String, ArtifactMetrics> artifactObject : ArtifactMetrics.entrySet()) 
		{
			DatabaseSaveDataC saveInDataBase = new DatabaseSaveDataC();
			saveInDataBase.saveMetricsInDatabase(artifactObject.getValue().getProjectName(), artifactObject.getValue().getVersion(), artifactObject.getValue().getArtifactName(), 
					artifactObject.getValue().getArtifactType(), artifactObject.getValue().getNcloc(), 
					artifactObject.getValue().getComplexity(), artifactObject.getValue().getFunctions(), 
					artifactObject.getValue().getCommentsDensity(), 0, 0 , artifactObject.getValue().getCommitSha());

			saveInDataBase.savePrincipalMetrics(artifactObject.getValue().getArtifactName(), artifactObject.getValue().getProjectName(), artifactObject.getValue().getVersion(), 
					artifactObject.getValue().getTechnicalDebt(), 0,
					artifactObject.getValue().getCodeSmells(),artifactObject.getValue().getBugs(),
					artifactObject.getValue().getVulnerabilities(),artifactObject.getValue().getDuplications(), 
					artifactObject.getValue().getArtifactType(), artifactObject.getValue().getNumOfClasses(), 
					artifactObject.getValue().getComplexity(), artifactObject.getValue().getFunctions(), 
					artifactObject.getValue().getNcloc(), artifactObject.getValue().getStatements(),
					artifactObject.getValue().getCommentsDensity(), artifactObject.getValue().getLanguage());
		}
	}
}
