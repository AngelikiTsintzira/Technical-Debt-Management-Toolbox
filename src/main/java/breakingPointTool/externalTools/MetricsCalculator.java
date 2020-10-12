package eu.sdk4ed.uom.td.analysis.externalTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetricsCalculator 
{
	public boolean executeOneVersion(String jarName, int version, String path, String projectName) throws IOException, InterruptedException
	{
		System.out.println("----- Start Metrics Calculator -----");

		// Jar file if you execute this tool as jar file, eclipse and server
		File f = new File(path + File.separator + jarName + version + ".jar");

		System.out.println(f);

		if(f.exists() && !f.isDirectory()) 
		{ 

			ArrayList<String> command = new ArrayList<String>();
			command.add("java");
			command.add("-jar");
			command.add("metrics_calculator.jar");
			command.add(path + File.separator + jarName + version + ".jar");
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
