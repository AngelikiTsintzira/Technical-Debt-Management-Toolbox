package eu.sdk4ed.uom.td.analysis.calculations;

import java.sql.SQLException;
import java.util.ArrayList;

import eu.sdk4ed.uom.td.analysis.artifact.ArtifactMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.OptimalArtifact;
import eu.sdk4ed.uom.td.analysis.database.DatabaseGetData;
import eu.sdk4ed.uom.td.analysis.database.DatabaseSaveData;
import eu.sdk4ed.uom.td.analysis.database.DatabaseSaveDataC;

public class TechnicalDebt 
{
	private static final double MINUTES_IN_HOUR = 60.0;
    private static final double HOURLY_LINES_OF_CODE = 25.0;
    private static final double HOURLY_WAGE = 37.50;
    
	private double interest;
	private double principal;
	private double breakingPoint;
	private double interestProbability;
	private double changeRate;
	
	private double fitnessValueDit;
	private double fitnessValueNocc;
	private double fitnessValueMpc;
	private double fitnessValueRfc;
	private double fitnessValueLcom;
	private double fitnessValueDac;
	private double fitnessValueNom;
	private double fitnessValueWmpc;
	private double fitnessValueSize1;
	private double fitnessValueSize2;
	
	private double fitnessValueLinesOfCode;
	private double fitnessValueComplexity;
	private double fitnessValueCoupling;
	private double fitnessValueCohesion;
	
	public TechnicalDebt()
	{
		this.interest = 0;
		this.principal = 0;
		this.breakingPoint = 0;
		this.interestProbability = 0;
		this.changeRate = 0;
	}

	public void calculateInterestProbabilityMetrics(ArtifactMetrics artifact)
	{
		DatabaseGetData db = new DatabaseGetData();

		ArrayList<Double> locs = new ArrayList<Double>();
		locs.addAll(db.getLocForArtifactPerVersion(artifact.getArtifactName(), artifact.getVersion(), artifact.getLanguage(), artifact.getProjectName()));
		
		this.changeRate  = this.getChangeRate(locs);
		this.interestProbability = this.calculateInterestProbability(locs);
	}
	
	public void calculateInterestProbabilityMetricsPackage(ArrayList<ArtifactMetrics> artifactPerPackage)
	{
		DatabaseGetData db = new DatabaseGetData();
		
		double changeRateSum = 0;
		double interestProbabilitySum = 0;

		for (int j = 0; j < artifactPerPackage.size(); j ++)
		{					
			ArrayList<Double> locs = new ArrayList<Double>();
			locs.addAll(db.getLocForArtifactPerVersion(artifactPerPackage.get(j).getArtifactName(), artifactPerPackage.get(j).getVersion(), artifactPerPackage.get(j).getLanguage(), artifactPerPackage.get(j).getProjectName()));
			
			this.changeRate  = this.getChangeRate(locs);
			this.interestProbability = this.calculateInterestProbability(locs);
						
			changeRateSum = changeRateSum + this.changeRate;
			interestProbabilitySum = interestProbabilitySum  + this.interestProbability;
		}
		
		this.changeRate = changeRateSum  / artifactPerPackage.size();
		this.interestProbability = interestProbabilitySum / artifactPerPackage.size();
	}
	
	public void calcucateInterestJava(ArtifactMetrics investigatedClass, OptimalArtifact optimalClass) throws SQLException
	{	
		this.fitnessValueDit = calculateFitnessValueMin(optimalClass.getDit(), investigatedClass.getDit());
    	this.fitnessValueNocc = calculateFitnessValueMin(optimalClass.getNocc(), investigatedClass.getNocc());
    	this.fitnessValueMpc = calculateFitnessValueMin(optimalClass.getMpc(), investigatedClass.getMpc());
    	this.fitnessValueRfc = calculateFitnessValueMin(optimalClass.getRfc(), investigatedClass.getRfc());
    	this.fitnessValueLcom = calculateFitnessValueMin(optimalClass.getLcom(), investigatedClass.getLcom());
    	this.fitnessValueDac = calculateFitnessValueMin(optimalClass.getDac(), investigatedClass.getDac());
    	this.fitnessValueNom = calculateFitnessValueMin(optimalClass.getNom(), investigatedClass.getNom());
    	this.fitnessValueWmpc = calculateFitnessValueMin(optimalClass.getWmpc(), investigatedClass.getWmpc());
    	this.fitnessValueSize1 = calculateFitnessValueMin(optimalClass.getLoc(), investigatedClass.getLoc());
    	this.fitnessValueSize2 = calculateFitnessValueMin(optimalClass.getNop(), investigatedClass.getNop());
    	
    	this.interest = this.fitnessValueDit + this.fitnessValueNocc + this.fitnessValueMpc + this.fitnessValueRfc + this.fitnessValueLcom + 
    			this.fitnessValueDac + this.fitnessValueNom + this.fitnessValueWmpc + this.fitnessValueSize1 + this.fitnessValueSize2;
    	
    	System.out.println("VERSION: " + investigatedClass.getVersion());
    	System.out.println("Artifact: " + investigatedClass.getArtifactName());

    	this.interest = this.interest / 10;
    	this.interest = 1  - this.interest ;
    	this.interest = this.interest * this.changeRate;  	
    	this.interest = (this.interest / HOURLY_LINES_OF_CODE) * HOURLY_WAGE;
    	
    	calculatePrincipal(investigatedClass.getTechnicalDebt());
    	calculateBreakingPoint();
    	
    	if (Double.isInfinite(this.principal) || Double.isNaN(this.principal))
    	{
    		this.principal = 0;
    	}
    	
    	if (Double.isInfinite(this.interest) || Double.isNaN(this.interest))
    	{
    		this.interest = 0;
    	}
    	
    	if (Double.isInfinite(this.breakingPoint) || Double.isNaN(this.breakingPoint))
    	{
    		this.breakingPoint = 0;
    	}
    	
    	if (Double.isInfinite(this.interestProbability) || Double.isNaN(this.interestProbability))
    	{
    		this.interestProbability = 0;
    	}
    	
    	if (Double.isInfinite(this.changeRate) || Double.isNaN(this.changeRate))
    	{
    		this.changeRate = 0;
    	}
    	
    	System.out.println("Change Rate: " + this.changeRate);
    	System.out.println("Interest is: " + this.interest);
    	System.out.println("Interest Probability is: " + this.interestProbability);
    	System.out.println("Breaking point is: " + this.breakingPoint );
    	System.out.println("Principal is: " + this.principal);
    	
    	DatabaseSaveData saveDataInDatabase = new DatabaseSaveData();
    	saveDataInDatabase.saveBreakingPointInDatabase(investigatedClass.getArtifactName(), investigatedClass.getVersion(), this.breakingPoint, this.principal, this.interest, this.changeRate, this.interestProbability, investigatedClass.getProjectName());

    	saveDataInDatabase.updatePrincipal(investigatedClass.getArtifactName(), investigatedClass.getVersion(), this.principal, investigatedClass.getProjectName());
	}
	
	public void calculateInterestC(ArtifactMetrics investigatedClass, OptimalArtifact optimalClass) throws SQLException
	{
		this.fitnessValueLinesOfCode = calculateFitnessValueMin(optimalClass.getLinesOfCode(), investigatedClass.getNcloc());
		this.fitnessValueComplexity = calculateFitnessValueMin(optimalClass.getComplexity(), investigatedClass.getComplexity());
		this.fitnessValueCohesion = calculateFitnessValueMin(optimalClass.getCohesion(), investigatedClass.getCohesion());
		this.fitnessValueCoupling = calculateFitnessValueMin(optimalClass.getCoupling(), investigatedClass.getCoupling());

		this.interest = this.fitnessValueLinesOfCode + this.fitnessValueComplexity + this.fitnessValueCohesion +  this.fitnessValueCoupling;
		System.out.println("VERSION: " + investigatedClass.getVersion());
    	System.out.println("Artifact: " + investigatedClass.getArtifactName());
    	
		this.interest = this.interest / 4;
		this.interest = 1  - this.interest ;
    	this.interest = this.interest * this.changeRate;  	
    	this.interest = (this.interest / HOURLY_LINES_OF_CODE) * HOURLY_WAGE;

		calculatePrincipal(investigatedClass.getTechnicalDebt());
		calculateBreakingPoint();

    	if (Double.isInfinite(this.principal) || Double.isNaN(this.principal))
    	{
    		this.principal = 0;
    	}
    	
    	if (Double.isInfinite(this.interest) || Double.isNaN(this.interest))
    	{
    		this.interest = 0;
    	}
    	
    	if (Double.isInfinite(this.breakingPoint) || Double.isNaN(this.breakingPoint))
    	{
    		this.breakingPoint = 0;
    	}
    	
    	if (Double.isInfinite(this.interestProbability) || Double.isNaN(this.interestProbability))
    	{
    		this.interestProbability = 0;
    	}
    	
    	if (Double.isInfinite(this.changeRate) || Double.isNaN(this.changeRate))
    	{
    		this.changeRate = 0;
    	}
    	
    	System.out.println("Change Rate: " + this.changeRate);
    	System.out.println("Interest is: " + this.interest);
    	System.out.println("Interest Probability is: " + this.interestProbability);
    	System.out.println("Breaking point is: " + this.breakingPoint );
    	System.out.println("Principal is: " + this.principal);
    	
		DatabaseSaveDataC  saveDataInDatabase = new DatabaseSaveDataC ();
		saveDataInDatabase.saveBreakingPointInDatabase(investigatedClass.getArtifactName(), investigatedClass.getVersion(), this.breakingPoint, this.principal, this.interest, this.changeRate, this.interestProbability);
		saveDataInDatabase.updatePrincipal(investigatedClass.getArtifactName(), investigatedClass.getVersion(), this.principal, investigatedClass.getProjectName());

	}
	
	public double getChangeRate(ArrayList<Double> locs)
	{
		double rate = 0;
		int aboveZero = 0;
		
		System.out.println("----------- Size of locs: " + locs.size());

				
		if (locs.size() == 1)
		{
			rate = 0; 
			return rate;
		}
		else if (locs.size() == 2)
		{
			rate = Math.abs(locs.get(1) - locs.get(0));
			return rate;
		}

		for (int i = 1 ; i < locs.size(); i++)
		{
			double diff = Math.abs(locs.get(i) - locs.get(i-1));
			rate = rate + diff;
			aboveZero++;
			
		}
		rate = rate / aboveZero;
		
		return rate;
	}
	
    public double calculateInterestProbability(ArrayList<Double> locs)
    {
    	double flag = 0.0;

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
	
	public void calculatePrincipal(double td)
    {
    	this.principal = (td / MINUTES_IN_HOUR ) * HOURLY_WAGE;
    }
    
    public void calculateBreakingPoint()
    {
    	this.breakingPoint = this.principal / this.interest; 	
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
