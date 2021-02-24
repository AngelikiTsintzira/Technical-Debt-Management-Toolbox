package eu.sdk4ed.uom.td.analysis.externalTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Sonarqube 
{
	public Sonarqube() { }

	// Function that creates sonar-project.properties file and executes SonarQube
	public void sonarQube (String exec, String projectName, int versions, String projectPath, String language, String serverUrl, String username, String password, int typeAnalysis) throws FileNotFoundException, InterruptedException
	{
		String fileName = "sonar-project.properties";
		//LocalDate localDate = LocalDate.now();
		for (int i = 0; i < versions; i++) 
		{
			if (typeAnalysis == 2)
			{
				i = versions - 1;
				versions = 1;
			}
			// Create sonar-properties file
			File newFile = new File(projectPath + File.separator + projectName + File.separator + projectName+ i + File.separator + fileName);
			System.out.println(newFile);
			String key = projectName + i;

			System.out.println("Execute version: " + i);
			PrintWriter printWriter = new PrintWriter (newFile);
			printWriter.println ("# Required metadata");
			printWriter.println ("sonar.projectKey=" + key); 
			printWriter.println ("sonar.projectName=" + projectName);
			printWriter.println ("sonar.projectVersion=" + i);		    
			//printWriter.println ("sonar.projectDate=" + DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate));
			printWriter.println ();

			printWriter.println ("# Comma-separated paths to directories with sources (required)");
			printWriter.println ("sonar.sources=.");
			printWriter.println ();

			printWriter.println ("# Language");
			printWriter.println ("sonar.language=" + language.toLowerCase());
			if (!language.equals("Java"))
				printWriter.println("sonar.cxx.suffixes.sources=cpp,c,cxx,h,hxx,hpp");
			printWriter.println ();

			printWriter.println ("# Encoding of the source files");		    
			printWriter.println ("sonar.sourceEncoding=UTF-8");
			printWriter.println ("sonar.java.binaries=.");
			//printWriter.println ("sonar.projectBaseDir=" + projectPath + File.separator + projectName + File.separator + projectName+ i + File.separator);

			// Default : http://localhost:9000
			printWriter.println ("# Server Configuration");
			printWriter.println ("sonar.host.url=" + serverUrl); 
			//printWriter.println ("sonar.login=" + username);
			// Password is blank if token is used
			//printWriter.println ("sonar.password=" + password);
			printWriter.close ();

			// Execute SonarQube
			try { 
				int ch; 
				System.out.println("inside try");
				ProcessBuilder pb = new ProcessBuilder(exec); 
				pb.directory(new File(projectPath +  projectName + File.separator + projectName + i + File.separator)); 
				System.out.println("File: " + projectPath +  projectName + File.separator + projectName + i + File.separator);
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

			System.out.println("SonarQube Execution Done");

		}
	}

}
