package main.java.breakingPointTool.calculations;

import java.sql.SQLException;
import java.util.ArrayList;

public class OptimalArtifactC 
{
	private double optimalComplexity;
    private double optimalNumOfFunctions;
    private double optimalLinesOfCode;
    private double optimalCommentsDensity;
    
    public void calculateOptimalClass(ArrayList<FindSimilarArtifactsC> similarClasses, int version) throws SQLException
    {
    	ArrayList<OptimalArtifactC > optimalClassesList = new ArrayList<OptimalArtifactC >();
    	for (int i = 0; i < similarClasses.size(); i++)
    	{
    		OptimalArtifactC  optimClass = new OptimalArtifactC ();
    		// Values from considered class
    		double complexity = similarClasses.get(i).getName().getComplexity();
    		double functions = similarClasses.get(i).getName().getNumOfFunctions();
    		double linesOfCode = similarClasses.get(i).getName().getNcloc();
    		double commentsDensity = similarClasses.get(i).getName().getCommentsDensity();
    		
    		//System.out.println("Arxikopoihsh optimal: " + complexity + " " + functions + " " + linesOfCode + " " + commentsDensity);
    		
    		// Values from similar classes
    		for (int j = 0; j < similarClasses.get(i).getSimilarClasses().size(); j++)
    		{
    			//System.out.println("Class name: " + similarClasses.get(i).getSimilarClasses().get(j).getClassName() );
    			/*System.out.println("Ypoloipes times: " + similarClasses.get(i).getSimilarClasses().get(j).getComplexity() + " " + 
    					similarClasses.get(i).getSimilarClasses().get(j).getNumOfFunctions() + " "+ similarClasses.get(i).getSimilarClasses().get(j).getNcloc()
    					+ " " + similarClasses.get(i).getSimilarClasses().get(j).getCommentsDensity());
    			*/
    			if (similarClasses.get(i).getSimilarClasses().get(j).getComplexity() + similarClasses.get(i).getSimilarClasses().get(j).getNumOfFunctions() 
    					+ similarClasses.get(i).getSimilarClasses().get(j).getNcloc() + similarClasses.get(i).getSimilarClasses().get(j).getCommentsDensity() == 0)
    			{
    				continue;
    			}
    			
    			if (similarClasses.get(i).getSimilarClasses().get(j).getCommentsDensity() == 100.0)
    			{
    				continue;
    			}
    			
    			if (similarClasses.get(i).getSimilarClasses().get(j).getComplexity() < complexity)
    				complexity = similarClasses.get(i).getSimilarClasses().get(j).getComplexity();
    			if (similarClasses.get(i).getSimilarClasses().get(j).getNumOfFunctions() < functions)
    				functions = similarClasses.get(i).getSimilarClasses().get(j).getNumOfFunctions();
    			if (similarClasses.get(i).getSimilarClasses().get(j).getNcloc() < linesOfCode)
    				linesOfCode = similarClasses.get(i).getSimilarClasses().get(j).getNcloc();
    			if (similarClasses.get(i).getSimilarClasses().get(j).getCommentsDensity() > commentsDensity)
    			     commentsDensity = similarClasses.get(i).getSimilarClasses().get(j).getCommentsDensity();
    			
    			/*System.out.println("Clasas name:" + similarClasses.get(i).getSimilarClasses().get(j).getClassName());
    			System.out.println("try to find optimal: " + similarClasses.get(i).getSimilarClasses().get(j).getDit() + " " + similarClasses.get(i).getSimilarClasses().get(j).getNocc() + " " +
    					similarClasses.get(i).getSimilarClasses().get(j).getMpc() + " " + similarClasses.get(i).getSimilarClasses().get(j).getRfc() + " " + similarClasses.get(i).getSimilarClasses().get(j).getLcom() + 
        				" " + similarClasses.get(i).getSimilarClasses().get(j).getDac()  + " " + similarClasses.get(i).getSimilarClasses().get(j).getWmpc() + " " +
        				similarClasses.get(i).getSimilarClasses().get(j).getCc() + " " + similarClasses.get(i).getSimilarClasses().get(j).getSize1() + " " + similarClasses.get(i).getSimilarClasses().get(j).getSize2());*/

    		}
    		
    		optimClass.setOptimalClassValues(complexity, functions,linesOfCode, commentsDensity);
    		
			/*
			 * System.out.println("For class: " +
			 * similarClasses.get(i).getName().getClassName());
			 * System.out.println("With metrics: " +
			 * similarClasses.get(i).getName().getComplexity() + " " +
			 * similarClasses.get(i).getName().getNumOfFunctions() + " " +
			 * similarClasses.get(i).getName().getLinesOfCode() + " " +
			 * similarClasses.get(i).getName().getCommentsDensity());
			 * System.out.println("The optimal class is: " + complexity + " " + functions +
			 * " " + linesOfCode +" "+ commentsDensity);
			 */
    		ResultsC rs = new ResultsC();
    		optimalClassesList.add(optimClass);
    		rs.calculateInterest(similarClasses.get(i).getName(), optimClass, version);
    	}
	}
    
    public void calculateOptimalPackage(ArrayList<FindSimilarArtifactsC> similarPackages, int version) throws SQLException
    {
    	ArrayList<OptimalArtifactC> optimalClassesList = new ArrayList<OptimalArtifactC>();
    	for (int i = 0; i < similarPackages.size(); i++)
    	{
    		OptimalArtifactC optimClass = new OptimalArtifactC();    		
    		// Values from considered class
    		double complexity = similarPackages.get(i).getPackage().getComplexity();
    		double functions = similarPackages.get(i).getPackage().getNumOfFunctions();
    		double linesOfCode = similarPackages.get(i).getPackage().getNcloc();
    		double commentsDensity = similarPackages.get(i).getPackage().getCommentsDensity();
    		
    		// Values from similar classes
    		for (int j = 0; j < similarPackages.get(i).getSimilarPackages().size(); j++)
    		{  			
    			if (similarPackages.get(i).getSimilarPackages().get(j).getComplexity() < complexity)
    				complexity = similarPackages.get(i).getSimilarPackages().get(j).getComplexity();
    			if (similarPackages.get(i).getSimilarPackages().get(j).getNumOfFunctions() < functions)
    				functions = similarPackages.get(i).getSimilarPackages().get(j).getNumOfFunctions();
    			if (similarPackages.get(i).getSimilarPackages().get(j).getNcloc() < linesOfCode)
    				linesOfCode = similarPackages.get(i).getSimilarPackages().get(j).getNcloc();
    			if (similarPackages.get(i).getSimilarPackages().get(j).getCommentsDensity() > commentsDensity)
    			     commentsDensity = similarPackages.get(i).getSimilarPackages().get(j).getCommentsDensity();
    			/*System.out.println("Clasas name:" + similarClasses.get(i).getSimilarClasses().get(j).getClassName());
    			System.out.println("try to find optimal: " + similarClasses.get(i).getSimilarClasses().get(j).getDit() + " " + similarClasses.get(i).getSimilarClasses().get(j).getNocc() + " " +
    					similarClasses.get(i).getSimilarClasses().get(j).getMpc() + " " + similarClasses.get(i).getSimilarClasses().get(j).getRfc() + " " + similarClasses.get(i).getSimilarClasses().get(j).getLcom() + 
        				" " + similarClasses.get(i).getSimilarClasses().get(j).getDac()  + " " + similarClasses.get(i).getSimilarClasses().get(j).getWmpc() + " " +
        				similarClasses.get(i).getSimilarClasses().get(j).getCc() + " " + similarClasses.get(i).getSimilarClasses().get(j).getSize1() + " " + similarClasses.get(i).getSimilarClasses().get(j).getSize2());*/

    		}
    		
    		optimClass.setOptimalClassValues(complexity, functions,linesOfCode, commentsDensity);
    		
			/*
			 * System.out.println("For package: " +
			 * similarPackages.get(i).getPackage().getPackageName());
			 * System.out.println("With metrics: " +
			 * similarPackages.get(i).getPackage().getComplexity() + " " +
			 * similarPackages.get(i).getPackage().getNumOfFunctions() + " " +
			 * similarPackages.get(i).getPackage().getNcloc() + " " +
			 * similarPackages.get(i).getPackage().getCommentsDensity());
			 * System.out.println("The optimal class is: " + complexity + " " + functions +
			 * " " + linesOfCode +" "+ commentsDensity);
			 */
    		ResultsC rs = new ResultsC();
    		optimalClassesList.add(optimClass);
    		rs.calculateInterestPackage(similarPackages.get(i).getPackage(), optimClass, version);
    	}
	}

    public void setOptimalClassValues(double complexity, double functions, double linesOfCode, double commentsDensity)
    {
    	this.optimalComplexity = complexity;
        this.optimalNumOfFunctions = functions;
        this.optimalLinesOfCode = linesOfCode;
        this.optimalCommentsDensity = commentsDensity; 	
    }
    
    public double getComplexity()
    {
    	return this.optimalComplexity;
    }
    
    public double getNumOfFunctions()
    {
    	return this.optimalNumOfFunctions;
    }
    
    public double getLinesOfCode()
    {
    	return this.optimalLinesOfCode;
    }
    
    public double getCommentsDensity()
    {
    	return this.optimalCommentsDensity;
    }

}
