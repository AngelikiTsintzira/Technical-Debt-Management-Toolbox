package breakingPointTool.externalTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class RippleEffectChangeProneness 
{
	public void ExtractJar(String jarName, int version, String path, String projectName)
			throws IOException, InterruptedException {
		// path =
		// "/Users/angelikitsintzira/eclipse-workspace/BreakingPointTool/jars/JavaGame0.jar";

		// path = path + File.separator + projectName + File.separator + jarName +
		// version + ".jar";

		String javaRunningDirectory = System.getProperty("user.dir");
		path = javaRunningDirectory + "/jars/" + jarName + version + ".jar";
		String project = jarName + version;

		// Check if jar file exists
		File f = new File(path);
		if (f.exists() && !f.isDirectory()) 
		{
			// Create a folder
			File file = new File(javaRunningDirectory + "/" + project);
			if (!file.exists()) {
				if (file.mkdir()) {
					System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			}

			// Execute unzip of jar file
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.command("jar", "-xvf", path);
			System.out.println("******* : " + javaRunningDirectory + "/" + project);
			processBuilder.directory(file);

			try {

				Process process = processBuilder.start();

				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}

				int exitCode = process.waitFor();
				System.out.println("\nExited with error code : " + exitCode);

				process.destroy();
				if (process.isAlive()) {
					process.destroyForcibly();
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("Jar file does not exist. The program will terminate.");
			System.exit(0);
		}

		// Execute REM
		executeREM(project);

		// Delete directory after used
		File directory = new File(javaRunningDirectory + "/" + project);

		// make sure directory exists
		if (!directory.exists()) {

			System.out.println("Directory does not exist.");
			System.exit(0);

		} else {

			try {

				delete(directory);

			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}

		System.out.println("Done");

	}

	public static void executeREM(String project) throws InterruptedException, IOException 
	{
		ProcessBuilder processB = new ProcessBuilder();
		processB.command("java", "-jar", "externalTools/interest_probability.jar",
				System.getProperty("user.dir") + "/" + project);
		// System.out.println("^^^^^^^ : " + System.getProperty("user.dir") + project);
		// processB.directory(file);

		try {

			Process process = processB.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}

			int exitCode = process.waitFor();
			System.out.println("\nExited with error code : " + exitCode);

			process.destroy();
			if (process.isAlive()) {
				process.destroyForcibly();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//int exitval;
		System.out.println("------ Process REM started -----");
		//System.out.println(
		//		"java -jar externalTools/interest_probability.jar " + System.getProperty("user.dir") + "/" + project);

		/*ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("java", " -jar", " externalTools/interest_probability.jar",
				System.getProperty("user.dir") + "/" + project);
		Process process = processBuilder.start();
		int exitCode = process.waitFor();
		System.out.println("\nExited with error code : " + exitCode);*/

		/*
		 * Process remProcess = Runtime.getRuntime()
		 * .exec("java -jar externalTools/interest_probability.jar " +
		 * System.getProperty("user.dir") + "/" + project); //remProcess.waitFor(); //
		 * If exit value is 0 then execution was successful
		 * 
		 * if(!remProcess.waitFor(1, TimeUnit.MINUTES)) { //timeout - kill the process.
		 * return; // consider using destroyForcibly instead } if(!remProcess.waitFor(1,
		 * TimeUnit.MINUTES)) { //timeout - kill the process. remProcess.destroy(); //
		 * consider using destroyForcibly instead }
		 * 
		 * System.out.println("----- End of execution REM -----"); exitval =
		 * remProcess.exitValue();
		 * 
		 * if (exitval != 0) { System.out.
		 * println("An error occured during execution.The project didn't analyzed."); }
		 * else { System.out.println("REM Tool executed successfully!"); }
		 * 
		 * remProcess.destroy(); if (remProcess.isAlive()) {
		 * remProcess.destroyForcibly(); }
		 */

	}

	public static void delete(File file) throws IOException {
		boolean result = true;
		
		if (file.isDirectory()) 
		{
			// If directory is empty, then delete it
			if (file.list().length == 0) 
			{
				result = file.delete();
				if (result)
					System.out.println("File is deleted : " + file.getAbsolutePath());
				else
					System.out.println("Error with the deletion of file");
				
			} 
			else 
			{
				// List all the directory contents
				String[] files = file.list();

				for (String temp : files) 
				{
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) 
				{
					result = file.delete();
					
					if (result)
						System.out.println("File is deleted : " + file.getAbsolutePath());
					else
						System.out.println("Error with the deletion of file");
				}
			}

		} 
		else 
		{
			// if file, then delete it
			result = file.delete();
			
			if (result)
				System.out.println("File is deleted : " + file.getAbsolutePath());
			else
				System.out.println("Error with the deletion of file");
		}
	}

}
