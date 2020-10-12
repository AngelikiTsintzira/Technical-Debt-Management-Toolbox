package main.java.breakingPointTool.artifact;

import java.util.ArrayList;

import main.java.breakingPointTool.database.DatabaseGetData;

public class FileMetricsC 
{
	private String projectName;
	private String className;
	
	// SonarQube Metrics
	private double classes;
	private double complexity;
	private double functions;
	private double nloc;
	private double statements;	
	private double TD;
	private double comment_lines_density;
	
	// Principal Metrics
	private double bugs;
	private double codeSmells;
	private double vulnerabilities;
	private double duplicated_lines_density;
	
	//Couling and Cohesion
	private double coupling;
	private double cohesion;

	// Breaking Point Tool Metrics
	private ArrayList<Double> locChange;
	private double averageLocChange;
	
	public FileMetricsC (String projectName, String className)
	{
		this.locChange = new ArrayList<Double>();
		this.className = className;	
		this.projectName =projectName;
		this.classes = 0;
		this.complexity = 0;
		this.functions = 0;
		this.nloc = 0;
		this.statements = 0;		
		this.TD = 0;
		this.comment_lines_density = 0;
		
		this.bugs = 0;
		this.codeSmells = 0;
		this.vulnerabilities = 0;
		this.duplicated_lines_density = 0;
		
		this.coupling = 0;
		this.cohesion = 0;
	}
	
	public void metricsfromSonar(double classes, double complexity, double functions,double nloc, double statements, double TD, double comment_lines_density, double codeSmells, double bugs, double vulnerabilities, double duplicated_lines_density) 
	{
		this.classes = classes;
		this.complexity = complexity;
		this.functions = functions;
		this.nloc = nloc;
		this.statements = statements;
		this.TD = TD;
		this.comment_lines_density = comment_lines_density;
		
		this.bugs = bugs;
		this.codeSmells = codeSmells;
		this.vulnerabilities = vulnerabilities;
		this.duplicated_lines_density = duplicated_lines_density;
	}
	
	public void metricsfromMetricsCalculator(String fileName, int version)
	{
		DatabaseGetData db = new DatabaseGetData();
		ArrayList<Double> list = new ArrayList<Double>();
		list = db.getCouplingCohesion(fileName, version);
		
		this.setCoupling(list.get(0));
		this.setCohesion(list.get(1));
	}
	
	public void setCoupling(double c) 
	{
		this.coupling = c;
	}
	
	public void setCohesion(double c) 
	{
		this.cohesion = c;
	}
	
	public void setBugs(double bugs)
	{
		this.bugs = bugs;
	}
	
	public void setDuplicationsDensity(double duplications)
	{
		this.duplicated_lines_density = duplications;
	}
	
	public void setCodeSmells(double codeSmells)
	{
		this.codeSmells = codeSmells;
	}
	
	public void setVulnerabilities(double vulnerabilities)
	{
		this.vulnerabilities = vulnerabilities;
	}
	
	public double getNcloc()
	{
		return this.nloc;
	}
	
	public double getStatements()
	{
		return this.statements;
	}
	
	public double getTD()
	{
		return this.TD;
	}
	
	public double getNumOfClasses()
	{
		return this.classes;
	}
	
	public double getNumOfFunctions()
	{
		return this.functions;
	}
	
	public double getComplexity()
	{
		return this.complexity;
	}
	
	public void setCommentsDensity(double comment_lines_density)
	{
		this.comment_lines_density = comment_lines_density;
	}
	
	public double getCommentsDensity()
	{
		return this.comment_lines_density;
	}
	
	public void addSizeInArraylist(double s)
	{
		this.locChange.add(s);
	}
	
	public void setAverageInterest(double av)
	{
		this.averageLocChange = av;
	}
	
	public void setClassName(String className)
	{
		this.className = className;
	}
	
	public void setProjectName(String projName)
	{
		this.projectName = projName;
	}
	
	public String getProjectName()
	{
		return this.projectName;
	}
	
	public String getClassName()
	{
		return this.className;
	}
	
	public ArrayList<Double> getArraySize1()
	{
		return this.locChange;
	}
	
	public double getAverageNocChange()
	{
		return this.averageLocChange;
	}
	
	public double getBugs()
	{
		return this.bugs;
	}
	
	public double getCodeSmells()
	{
		return this.codeSmells;
	}
	
	public double getVulnerabilities()
	{
		return this.vulnerabilities;
	}
	
	public double getDuplications()
	{
		return this.duplicated_lines_density;
	}
	
	public double getCoupling()
	{
		return this.coupling;
	}
	
	public double getCohesion() 
	{
		return this.cohesion;
	}
}
