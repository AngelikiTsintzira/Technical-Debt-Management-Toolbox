package eu.sdk4ed.uom.td.analysis.artifact;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import eu.sdk4ed.uom.td.analysis.database.DatabaseGetData;

public class ArtifactMetrics 
{
	private String projectName;
	private String className;
	private String language;
	private String commitSha;
	private int version;
	private String artifactType;

	// SonarQube Metrics 
	private double classes;
	private double complexity;
	private double functions;
	private double nloc;
	private double statements;	
	private double technicalDebt;
	private double commentLinesDensity;

	// Principal Metrics
	private double bugs;
	private double codeSmells;
	private double vulnerabilities;
	private double duplicatedLinesDensity;

	// Maintainability OOP Metrics
	private double mpc;	
	private double dit;
	private double nocc;
	private double rfc;
	private double lcom;
	private double wmpc;
	private double dac;
	private double nom;
	private double loc;
	private double nop;
	
	// Coupling and Cohesion
	private double coupling;
	private double cohesion;

	// Technical Debt Metrics
	private double changeRate;
	private double interestProbability;
	
	// Files in package
	private ArrayList<ArtifactMetrics> filesInPackage;
	
	public ArtifactMetrics(String projectName, String className, String language, String commitSha, int version, String artifactType)
	{
		this.className = className;	
		this.projectName = projectName;
		this.language = language;
		this.commitSha = commitSha;
		this.version = version;
		this.filesInPackage = new ArrayList<ArtifactMetrics>();
		this.artifactType = artifactType;
		
		this.classes = 0;
		this.complexity = 0;
		this.functions = 0;
		this.nloc = 0;
		this.statements = 0;		
		this.technicalDebt = 0;
		this.commentLinesDensity =  0;
		
		this.bugs = 0;
		this.codeSmells = 0;
		this.vulnerabilities = 0;
		this.duplicatedLinesDensity = 0;
		
		this.mpc = 0;
		this.dit = 0;
		this.nocc = 0;
		this.rfc = 0;
		this.lcom = 0;
		this.wmpc = 0; 
		this.dac = 0;
		this.nom = 0;
		this.loc = 0;
		this.nop = 0;
		
		this.coupling = 0;
		this.cohesion = 0;

		this.interestProbability = 0;
		this.changeRate = 0;	
	}
	
	public void setMetricsfromSonar(double classes, double complexity, double functions,double nloc, double statements, double TD, double codeSmells, double bugs, double vulnerabilities, 
			double duplicated_lines_density, double commentLinesDensity) 
	{
		this.classes = classes;
		this.complexity = complexity;
		this.functions = functions;
		this.nloc = nloc;
		this.statements = statements;
		this.technicalDebt = TD;
		this.commentLinesDensity =  commentLinesDensity;
		
		this.bugs = bugs;
		this.codeSmells = codeSmells;
		this.vulnerabilities = vulnerabilities;
		this.duplicatedLinesDensity = duplicated_lines_density;
	}
	
	public void setCouplingAndCohesion(String fileName, int version)
	{
		DatabaseGetData db = new DatabaseGetData();
		ArrayList<Double> list = new ArrayList<Double>();
		list = db.getCouplingCohesion(fileName, version);
		
		this.setCoupling(list.get(0));
		this.setCohesion(list.get(1));
	}
	
	public void metricsfromMetricsCalculator(double MPC, double NOM, double DIT, double NOCC, double RFC, double LCOM,
											 double WMPC, double DAC, double LOC, double NOP)
	{
		this.mpc =  MPC; 
		this.dit = DIT;
		this.nocc = NOCC;
		this.rfc = RFC;
		this.lcom = LCOM;
		this.wmpc = WMPC;
		this.dac = DAC;
		this.nom = NOM;
		this.loc = LOC;
		this.nop = NOP;
	}
	
	public void calculateMetricsPackageLevelJava() throws NumberFormatException, SQLException, IOException
	{
		int numberOfFilesinPackage = filesInPackage.size();
		
		double mpc = 0, nocc = 0, rfc = 0, lcom = 0, wmpc = 0, dac = 0;	
		double nom = 0, loc = 0, nop = 0;
		double value = 0;
		
		for  (int i = 0; i < filesInPackage.size(); i++)
		{
			nom = nom + filesInPackage.get(i).getNom();
			loc = loc + filesInPackage.get(i).getLoc();
			nop = nop + filesInPackage.get(i).getNop();
			
			mpc = mpc + filesInPackage.get(i).getMpc();
			nocc = nocc + filesInPackage.get(i).getNocc();
			rfc= rfc + filesInPackage.get(i).getRfc();
			lcom = lcom + filesInPackage.get(i).getLcom();
			wmpc = wmpc + filesInPackage.get(i).getWmpc();
			dac = dac + filesInPackage.get(i).getDac();
			
			if (filesInPackage.get(i).getDit() > value)
			{
				value = filesInPackage.get(i).getDit();
			}
		}
		
		this.nom = nom;
		this.loc = loc;
		this.nop = nop;
		this.dit = value;
		
		this.mpc = isNan(mpc / numberOfFilesinPackage);
		this.nocc = isNan(nocc /numberOfFilesinPackage);
		this.rfc = isNan(rfc / numberOfFilesinPackage);
		this.lcom = isNan(lcom / numberOfFilesinPackage);
		this.wmpc = isNan(wmpc / numberOfFilesinPackage);
		this.dac = isNan(dac / numberOfFilesinPackage);
		
		System.out.println("Package metrics: " + numberOfFilesinPackage + " loc: " + this.loc);
	}
	
	public void calculateMetricsPackageLevelC() throws NumberFormatException, SQLException, IOException
	{
		int numberOfFilesinPackage = this.filesInPackage.size();
		
		double lines_of_codeTemp = 0, complexityTemp = 0, functionsTemp = 0;
		double comment_lines_density = 0;
		double couplingTemp = 0, cohesionTemp = 0;
		
		for  (int i = 0; i < this.filesInPackage.size(); i++)
		{
			lines_of_codeTemp = lines_of_codeTemp + this.filesInPackage.get(i).getNcloc();
			complexityTemp = complexityTemp + this.filesInPackage.get(i).getComplexity();
			functionsTemp = functionsTemp + this.filesInPackage.get(i).getFunctions();
			comment_lines_density = comment_lines_density + this.filesInPackage.get(i).getCommentsDensity();
			couplingTemp = couplingTemp + this.filesInPackage.get(i).getCoupling();
			cohesionTemp = cohesionTemp + this.filesInPackage.get(i).getCohesion();
			
		}
		
		this.nloc = lines_of_codeTemp;
		this.commentLinesDensity = isNan(comment_lines_density/numberOfFilesinPackage);
		this.complexity = isNan(complexityTemp/numberOfFilesinPackage);
		this.functions = isNan(functionsTemp/numberOfFilesinPackage);
		this.coupling = isNan(couplingTemp / numberOfFilesinPackage);
		this.cohesion = isNan(cohesionTemp / numberOfFilesinPackage);
	}
	
	public double isNan(double num)
	{
		if (Double.isNaN(num))
			return 0;
		
		return num;
	}
	
	public void couplingAndCohesionMetrics(String fileName, int version)
	{
		DatabaseGetData db = new DatabaseGetData();
		ArrayList<Double> list = new ArrayList<Double>();
		list = db.getCouplingCohesion(fileName, version);
		
		this.setCoupling(list.get(0));
		this.setCohesion(list.get(1));
	}
	
	public String getArtifactType()
	{
		return this.artifactType;
	}
	
	public String getLanguage()
	{
		return this.language;
	}
	
	public String getCommitSha()
	{
		return this.commitSha;
	}
	
	public int getVersion()
	{
		return this.version;
	}
	
	public void setfilesInPackage(ArtifactMetrics artifact)
	{
		this.filesInPackage.add(artifact);
	}
	
	public ArrayList<ArtifactMetrics> getFilesInPackage()
	{
		return this.filesInPackage;
	}
	
	public void setCoupling(double c) 
	{
		this.coupling = c;
	}
	
	public void setCohesion(double c) 
	{
		this.cohesion = c;
	}
	
	public double getCoupling()
	{
		return this.coupling;
	}
	
	public double getCohesion() 
	{
		return this.cohesion;
	}
	
	public void setInterestProbability(double ip)
	{
		this.interestProbability = ip;
	}
	
	public double getInterestProbability()
	{
		return this.interestProbability;
	}
	
	public void setBugs(double bugs)
	{
		this.bugs = bugs;
	}
	
	public void setDuplicationsDensity(double duplications)
	{
		this.duplicatedLinesDensity = duplications;
	}
	
	public void setCodeSmells(double codeSmells)
	{
		this.codeSmells = codeSmells;
	}
	
	public void setVulnerabilities(double vulnerabilities)
	{
		this.vulnerabilities = vulnerabilities;
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
		return this.duplicatedLinesDensity;
	}
	
	public void setArtifactName(String className)
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
	
	public String getArtifactName()
	{
		return this.className;
	}
	
	public void setAverageLOCChange(double av)
	{
		this.changeRate = av;
	}
	
	public double getAverageLocChange()
	{
		return this.changeRate;
	}
	
	public double getNocc()
	{
		return this.nocc;
	}
	
	public double getTechnicalDebt()
	{
		return this.technicalDebt;
	}
	
	public void setTechnicalDebt(double td)
	{
		this.technicalDebt = td;
	}
	
	public double getNumOfClasses()
	{
		return this.classes;
	}
	
	public void setNumOfClasses(double cl)
	{
		this.classes = cl;
	}
	
	public void setComplexity(double c)
	{
		this.complexity = c;
	}
	
	public double getComplexity()
	{
		return this.complexity;
	}
	
	public double getFunctions()
	{
		return this.functions;
	}
	
	public double getNcloc()
	{
		return this.nloc;
	}
	
	public double getStatements()
	{
		return this.statements;
	}
	
	public double getDit()
	{
		return this.dit;
	}
	
	public void setDit(double dit)
	{
		this.dit  = dit;
	}
	
	public double getMpc()
	{
		return this.mpc;
	}
	
	public double getRfc()
	{
		return this.rfc;
	}
	
	public double getLcom()
	{
		return this.lcom;
	}
	
	public double getDac()
	{
		return this.dac;
	}
	
	public double getNom()
	{
		return this.nom;
	}
	
	public double getWmpc()
	{
		return this.wmpc;
	}
	
	public double getNop()
	{
		return this.nop;
	}
	
	public double getLoc()
	{
		return this.loc;
	}
	
	public void setCommentsDensity(double comment_lines_density)
	{
		this.commentLinesDensity = comment_lines_density;
	}
	
	public double getCommentsDensity()
	{
		return this.commentLinesDensity;
	}

	public void setStatements(Double state) 
	{
		this.statements = state;
	}

	public void setFunctions(Double functions) 
	{
		this.functions = functions;	
	}

	public void setNcloc(Double nloc) 
	{
		this.nloc  = nloc; 
		
	}
}
