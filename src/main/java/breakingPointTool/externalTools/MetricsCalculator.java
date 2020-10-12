package main.java.breakingPointTool.externalTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetricsCalculator 
{
<<<<<<< HEAD
	public boolean executeOneVersion(String jarName, int version, String path, String projectName) throws IOException, InterruptedException
=======
	public void executeAllVersions (String jarName, int versionNum, String path, String projectName) throws IOException, InterruptedException
	{
		int exitval;

		for (int i = 0; i < versionNum; i++)
		{
			// Check if jar file exists
			File f = new File(path + File.separator + projectName + File.separator + jarName + i + ".jar");
			if(f.exists() && !f.isDirectory()) 
			{ 
				Process metricsAnalysisProcess = Runtime.getRuntime()
						.exec("java -jar externalTools/metrics_calculator.jar " + path + File.separator + projectName + File.separator + jarName + i + ".jar" + " output" + i + ".csv");
				metricsAnalysisProcess.waitFor();
				// If exit value is 0 then execution was successful
				exitval = metricsAnalysisProcess.exitValue();

				if (exitval != 0) 
				{
					System.out.println("An error occured during execution.The project didn't analyzed.");	
				}
				else
				{
					System.out.println("Metrics Calculation Tool executed successfully!");
				}
			}
			else
			{
				System.out.println("Metrics Calculator does not exist. The program will terminate.");
				System.exit(0);
			}
		}
	}

	public void executeOneVersion(String jarName, int version, String path, String projectName) throws IOException, InterruptedException
>>>>>>> parent of d825483... Refactorings and automation. Bug with java analysis
	{
		int exitval;
		System.out.println("----- Start Metrics Calculator -----");

		// Jar file if you execute this tool as jar file, eclipse and server
<<<<<<< HEAD
		File f = new File(path + File.separator + jarName + version + ".jar");

=======
		File f = new File(path + File.separator + projectName + File.separator + jarName + version + ".jar");
		
		// Run On Docker in Server Command
		//String javaRunningDirectory = System.getProperty("user.dir");
		//File f = new File(javaRunningDirectory + File.separator + projectName + File.separator + jarName + version + ".jar");
		
		// Jar file if you execute this tool from eclipse
		//File f = new File(System.getProperty("user.dir") + "/jars/" +jarName + version + ".jar");
		
>>>>>>> parent of d825483... Refactorings and automation. Bug with java analysis
		System.out.println(f);

		if(f.exists() && !f.isDirectory()) 
		{ 

			ArrayList<String> command = new ArrayList<String>();
			command.add("java");
			command.add("-jar");
<<<<<<< HEAD
			command.add("metrics_calculator.jar");
			command.add(path + File.separator + jarName + version + ".jar");
=======
			command.add("externalTools/metrics_calculator.jar");
			command.add(path + File.separator + projectName + File.separator + jarName + version + ".jar");
>>>>>>> parent of d825483... Refactorings and automation. Bug with java analysis
			command.add("output" + version + ".csv");

			ProcessBuilder processB = new ProcessBuilder();

			processB.command(command);

			try {

				Process process = processB.start();

				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					//System.out.println(line);
				}

				int exitCode = process.waitFor();
				System.out.println("\nExited with error code : " + exitCode);

				process.destroy();
				if (process.isAlive()) {
					process.destroyForcibly();
				}

			} catch (IOException e) {
				Logger logger = Logger.getAnonymousLogger();
				logger.log(Level.SEVERE, "Exception was thrown: ", e);
			} catch (InterruptedException e) {
				Logger logger = Logger.getAnonymousLogger();
				logger.log(Level.SEVERE, "Exception was thrown: ", e);
			}
			
			return true;

		}
		else
		{
			System.out.println("Jar file does not exist. Interest calculation cannot be done without a jar file. The program will terminate.");
			System.exit(0);
			return false;
		}
	}

}
