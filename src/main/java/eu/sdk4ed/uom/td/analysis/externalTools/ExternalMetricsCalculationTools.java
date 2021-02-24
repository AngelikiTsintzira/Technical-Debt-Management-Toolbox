package eu.sdk4ed.uom.td.analysis.externalTools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExternalMetricsCalculationTools 
{
	static final String LONG_METHOD_MOVE_CLASS_REFACTORINGS_TOOL = "longMethodRefactorings.jar"; 
	static final String MOVE_CLASS_REFACTORINGS_TOOL = "moveClassRefactorings.jar"; 
	static final String COUPLING_COHESION_NOOOP_TOOL = "metrics_calculator_noOop.jar";
	static final String METRICS_CALCULATOR_TOOL = "MetricsCalculator.jar";
	
	public void executeAnalysis(String projectName, String language, String pathProject, String credFile, int version, int toolID) throws InterruptedException, IOException
	{
		ArrayList<String> command = new ArrayList<String>();
		
		if (language.equals("C"))
		{
			language = "c";
		}
		else if (language.equals("C++"))
		{
			language = "cpp";
		}
		else if(language.equals("Java"))
		{
			language = "java";
		}

		if (toolID == 1)
		{
			System.out.println("----- Extract Long Method Refactoring -----");
			command  = getExtractLongMethodCommand(projectName, language, pathProject, credFile, version);
		}
		else if (toolID == 2)
		{
			System.out.println("----- Move Class Refactoring -----");
			command  = getMoveClassCommand(projectName, language, pathProject, credFile, version);
		}
		else if (toolID == 3)
		{
			System.out.println("----- Coupling and Cohesion for C/C++ -----");
			command  = getNoOOPMetricsCommand(projectName, language, pathProject, credFile, version);
		}
		else if (toolID == 4)
		{
			System.out.println("----- Metrics Calculator -----");
			command  = getMetricsCalculatorCommand(pathProject, version);
		}

		File f = new File(pathProject);

		if(f.exists()) 
		{ 
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
		}
		else
		{
			System.out.println("ERROR -- Refactorings not executed.");
		}

	}

	public ArrayList<String> getExtractLongMethodCommand(String projectName, String language, String pathProject, String credFile, int version)
	{
		ArrayList<String> command = new ArrayList<String>();

		command.add("java");
		command.add("-jar");
		command.add(LONG_METHOD_MOVE_CLASS_REFACTORINGS_TOOL);
		command.add(language);
		command.add(projectName);
		command.add(String.valueOf(version));
		command.add(pathProject);
		command.add(credFile);

		return command;
	}

	public ArrayList<String> getMoveClassCommand(String projectName, String language, String pathProject, String credFile, int version)
	{
		ArrayList<String> command = new ArrayList<String>();

		command.add("java");
		command.add("-jar");
		command.add(MOVE_CLASS_REFACTORINGS_TOOL);
		command.add(language);
		command.add(projectName);
		command.add(pathProject);
		command.add(credFile);

		return command;
	}
	
	public ArrayList<String> getNoOOPMetricsCommand(String projectName, String language, String pathProject, String credFile, int version)
	{
		ArrayList<String> command = new ArrayList<String>();

		command.add("java");
		command.add("-jar");
		command.add(COUPLING_COHESION_NOOOP_TOOL);
		command.add(language);
		command.add(projectName);
		command.add(String.valueOf(version));
		command.add(pathProject);
		command.add(credFile);

		return command;
	}
	
	public ArrayList<String> getMetricsCalculatorCommand(String path, int version)
	{
		ArrayList<String> command = new ArrayList<String>();

		command.add("java");
		command.add("-jar");
		command.add(METRICS_CALCULATOR_TOOL);
		command.add(path);
		command.add("output" + version + ".csv");

		return command;
	}
}
