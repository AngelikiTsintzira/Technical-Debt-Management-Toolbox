package main.java.breakingPointTool.calculations;

import java.sql.SQLException;
import java.util.ArrayList;

import main.java.breakingPointTool.artifact.FileMetricsC;
import main.java.breakingPointTool.artifact.PackageMetricsC;
import main.java.breakingPointTool.database.DatabaseGetData;
import main.java.breakingPointTool.database.DatabaseSaveDataC;

public class ResultsC 
{
	private static final double MINUTES_IN_HOUR = 60.0;
    private static final double HOURLY_LINES_OF_CODE = 25.0;
    private static final double HOURLY_WAGE = 45.81;
    
	private double interest;
	private double principal;
	private double breakingPoint;
	
	private double fitnessValueLinesOfCode;
	private double fitnessValueComplexity;
	private double fitnessValueNumOfFunctions;
	private double fitnessValueCommentsDensity;
	
	public ResultsC()
	{
		this.interest = 0;
		this.principal = 0;
		this.breakingPoint = 0;
	}
	
	 public void calculateInterest(FileMetricsC investigatedClass, OptimalArtifactC optimalClass, int version) throws SQLException
	    {
	    	double k = investigatedClass.getAverageNocChange();

	    	this.fitnessValueLinesOfCode = calculateFitnessValueMin(optimalClass.getLinesOfCode(), investigatedClass.getNcloc());
	    	this.fitnessValueComplexity = calculateFitnessValueMin(optimalClass.getComplexity(), investigatedClass.getComplexity());
	    	this.fitnessValueNumOfFunctions = calculateFitnessValueMin(optimalClass.getNumOfFunctions(), investigatedClass.getNumOfFunctions());
	    	this.fitnessValueCommentsDensity = calculateFitnessValueMax(optimalClass.getCommentsDensity(), investigatedClass.getCommentsDensity());
	    	
	    	this.interest = this.fitnessValueLinesOfCode + this.fitnessValueComplexity + this.fitnessValueNumOfFunctions + this.fitnessValueCommentsDensity;
	    	//System.out.println("gchsgd: " + this.interest);
	    	System.out.println("k: " + k);
	    	/*System.out.println("Interest test: " + this.fitnessValueDit + " " + this.fitnessValueNocc +" "+ this.fitnessValueMpc +" "+ this.fitnessValueRfc + " "+this.fitnessValueLcom + " "+
	    			this.fitnessValueDac +" "+ this.fitnessValueNom +" "+ this.fitnessValueWmpc +" "+ this.fitnessValueSize1 +" "+ this.fitnessValueSize2);*/
	    	this.interest = this.interest / 4;
	    	this.interest = 1  - this.interest ;
	    	System.out.println("Interest is: " + interest);
	    	this.interest = this.interest * k;  	
	    	this.interest = (this.interest / HOURLY_LINES_OF_CODE) * HOURLY_WAGE;
	    	System.out.println("Interest is in money: " + interest);
	    	
	    	calculatePrincipal(investigatedClass);
	    	calculateBreakingPoint();
	    	
	    	if (Double.isInfinite(this.breakingPoint))
	    	{
	    		// It means infinity
	    		this.breakingPoint = -1;
	    	}
	    	else if (Double.isNaN(breakingPoint))
	    	{
	    		// It means it is not defined
	    		this.breakingPoint = -2;
	    	}
	    	
	    	double rate = calculateInterestProbability(investigatedClass.getClassName(), version);
	    	DatabaseSaveDataC  saveDataInDatabase = new DatabaseSaveDataC ();
	    	System.out.println("Before saved in database: " + investigatedClass.getClassName() + " " + version + " " +
	    	this.breakingPoint + " " + this.principal + " " + this.interest + " " + k + " " + rate);
	    	saveDataInDatabase.saveBreakingPointInDatabase(investigatedClass.getClassName(), version, this.breakingPoint, this.principal, this.interest, k, rate);
	    	saveDataInDatabase.updatePrincipal(investigatedClass.getClassName(), version, this.principal);

	    }
	 
	 public void calculateInterestPackage(PackageMetricsC investigatedPackage, OptimalArtifactC optimalClass, int version) throws SQLException
	    {
	    	double k = investigatedPackage.getAverageNocChange();
	    	
	    	this.fitnessValueLinesOfCode = calculateFitnessValueMin(optimalClass.getLinesOfCode(), investigatedPackage.getNcloc());
	    	this.fitnessValueComplexity = calculateFitnessValueMin(optimalClass.getComplexity(), investigatedPackage.getComplexity());
	    	this.fitnessValueNumOfFunctions = calculateFitnessValueMin(optimalClass.getNumOfFunctions(), investigatedPackage.getNumOfFunctions());
	    	this.fitnessValueCommentsDensity = calculateFitnessValueMax(optimalClass.getCommentsDensity(), investigatedPackage.getCommentsDensity());
	    	
	    	this.interest = this.fitnessValueLinesOfCode + this.fitnessValueComplexity + this.fitnessValueNumOfFunctions + this.fitnessValueCommentsDensity;

	    	System.out.println("k: " + k);
	    	System.out.println("Interest Package: " + this.fitnessValueLinesOfCode + " " + this.fitnessValueComplexity +" "+ this.fitnessValueNumOfFunctions +" "+ this.fitnessValueCommentsDensity );
	    	this.interest = this.interest / 4;
	    	this.interest = 1  - this.interest ;
	    	System.out.println("Interest is: " + interest);
	    	this.interest = this.interest * k;  	
	    	this.interest = (this.interest / HOURLY_LINES_OF_CODE) * HOURLY_WAGE;
	    	System.out.println("Interest is in money: " + interest);
	    	
	    	calculatePrincipalPackage(investigatedPackage);
	    	calculateBreakingPoint();
	    	
	    	if (Double.isInfinite(this.breakingPoint))
	    	{
	    		// It means infinity
	    		this.breakingPoint = -1;
	    	}
	    	else if (Double.isNaN(breakingPoint))
	    	{
	    		// It means it is not defined
	    		this.breakingPoint = -2;
	    	}
	    	
	    	double rate = calculateInterestProbability(investigatedPackage.getPackageName(), version);
	    	DatabaseSaveDataC saveDataInDatabase = new DatabaseSaveDataC();
	    	System.out.println("Before saved in database: " + investigatedPackage.getPackageName() + " " + version + " " +
	    	this.breakingPoint + " " + this.principal + " " + this.interest + " " + k + " " + rate);
	    	saveDataInDatabase.saveBreakingPointInDatabase(investigatedPackage.getPackageName(), version, this.breakingPoint, this.principal, this.interest, k, rate);
	    	saveDataInDatabase.updatePrincipal(investigatedPackage.getPackageName(), version, this.principal);

	    }
	 
	 public double calculateInterestProbability(String artifact, int version)
	 {
		 	ArrayList<Double> locs = new ArrayList<Double>();
	    	double flag = 0.0;
	    	// Get k values from database
	    	DatabaseGetData dbCall = new DatabaseGetData();
	    	locs.addAll(dbCall.getLoCForArtifactC(artifact, version));
	    	
	    	// bug when version are less than full
	    	
	    	if (locs.size() == 0 || locs.size() == 1)
	    		return 0.0;
	    	
	    	if (locs.size() == 2)
	    	{
	    		double diff = locs.get(1)  - locs.get(0);
	    		
	    		if (diff != 0)
	    			return 1.0;  		
	    		else
	    			return 0.0;
	    	}
	    	
	    	for (int i = 0; i < locs.size()-1; i++ )
	    	{
	    		double diff = locs.get(i + 1) - locs.get(i);
	    		
	    		if (diff != 0)
	    		{
	    			flag++;
	    		}
	    	}
	 	
	    	return flag / (locs.size()-1);
		
	    }
	    
	    public void calculatePrincipalPackage(PackageMetricsC testedClass)
	    {
	    	this.principal = (testedClass.getTD() / MINUTES_IN_HOUR ) * HOURLY_WAGE;
	    	System.out.println("Principal is: " + principal);
	    }
	    
	    public void calculatePrincipal(FileMetricsC testedClass)
	    {
	    	this.principal = (testedClass.getTD() / MINUTES_IN_HOUR ) * HOURLY_WAGE;
	    	System.out.println("Principal is: " + principal);
	    }
	    
	    public void calculateBreakingPoint()
	    {
	    	//TODO maybe i should change NaN to a number, lets say -1
	    	this.breakingPoint = this.principal / this.interest;
	    	System.out.println("Breaking point is: " + breakingPoint + "\n");
	    }
	    
	    public double  calculateFitnessValueMin (double optimal, double actual)
	    {
	    	if (optimal == 00)
	    		return 0;
	    	else 
	    		return optimal / actual;
	    }
	    
	    public double  calculateFitnessValueMax (double optimal, double actual)
	    {
	    	if (optimal == 00)
	    		return 0;
	    	else 
	    		return actual / optimal;
	    }
	    
	    public double getPrincipal()
	    {
	    	return this.principal;
	    }
	    
	    public double getInterest()
	    {
	    	return this.interest;
	    }
	    
	    public double getBreakingPoint()
	    {
	    	return this.breakingPoint;
	    }
}
