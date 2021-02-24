package eu.sdk4ed.uom.td.analysis.artifact;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import eu.sdk4ed.uom.td.analysis.calculations.TechnicalDebt;

public class OptimalArtifact 
{
	private double optimalDit;
	private double optimalNocc;
	private double optimalMpc;
	private double optimalRfc;
	private double optimalLcom;
	private double optimalDac;
	private double optimalNom;
	private double optimalWmpc;
	private double optimalLoc;
	private double optimalNop;

	private double optimalComplexity;
	private double optimalLinesOfCode;
	private double optimalCoupling;
	private double optimalCohesion;

	public void setOptimalClassValues(double Dit, double Nocc, double Mpc, double Rfc, double Lcom, double Dac, double Nom, double Wmpc,
			double Loc, double Nop)
	{
		this.optimalDit = Dit;
		this.optimalNocc = Nocc;
		this.optimalMpc = Mpc;
		this.optimalRfc = Rfc;
		this.optimalLcom = Lcom;
		this.optimalDac = Dac;
		this.optimalNom = Nom;
		this.optimalWmpc = Wmpc;
		this.optimalLoc = Loc;
		this.optimalNop = Nop;  	
	}

	public void setOptimalClassValues(double complexity, double linesOfCode,  double cohesion, double coupling)
	{
		this.optimalComplexity = complexity;
		this.optimalLinesOfCode = linesOfCode;
		this.optimalCoupling = coupling;
		this.optimalCohesion = cohesion;
	}

	public void calculateOptimalArtifactJava(ArrayList<SimilarArtifacts> similarArtifacts) throws SQLException 
	{
		for (int i = 0;  i < similarArtifacts.size(); i ++)
		{
			OptimalArtifact optimalArtifact = new OptimalArtifact();

			ArtifactMetrics artifact = similarArtifacts.get(i).getArtifact();

			double Dit = artifact.getDit();
			double Nocc = artifact.getNocc();
			double Mpc = artifact.getMpc();
			double Rfc = artifact.getRfc();
			double Lcom = artifact.getLcom();
			double Dac = artifact.getDac();
			double Nom = artifact.getNom();
			double Wmpc = artifact.getWmpc();
			double Loc = artifact.getLoc();
			double Nop = artifact.getNop();
			
			System.out.println("Check artifacts value: " + artifact.getLcom() + " " + artifact.getLoc());

			// Check only the 5 or less most similar
			int sizeOfArtifacts = similarArtifacts.get(i).getSimilarArtifacts().size();

			if (sizeOfArtifacts > 5)
				sizeOfArtifacts = 5;
			Map<Double, ArtifactMetrics> similarArt = similarArtifacts.get(i).getSimilarArtifacts();

			for (Entry<Double, ArtifactMetrics> artifactObject : similarArt.entrySet()) 
			{				
				if (sizeOfArtifacts  == 0)
					break;
				sizeOfArtifacts = sizeOfArtifacts - 1;

				if (artifactObject.getValue().getDit() < Dit)
					Dit = artifactObject.getValue().getDit();
				if (artifactObject.getValue().getNocc() < Nocc)
					Nocc = artifactObject.getValue().getNocc();
				if (artifactObject.getValue().getMpc() < Mpc)
					Mpc = artifactObject.getValue().getMpc();
				if (artifactObject.getValue().getRfc() < Rfc)
					Rfc = artifactObject.getValue().getRfc();
				if (artifactObject.getValue().getLcom() < Lcom)
					Lcom = artifactObject.getValue().getLcom();
				if (artifactObject.getValue().getDac() < Dac)
					Dac =artifactObject.getValue().getDac();
				if (artifactObject.getValue().getNom() < Nom)
					Nom = artifactObject.getValue().getNom();
				if (artifactObject.getValue().getWmpc() < Wmpc)
					Wmpc = artifactObject.getValue().getWmpc();
				if (artifactObject.getValue().getLoc() < Loc)
					Loc = artifactObject.getValue().getLoc();
				if (artifactObject.getValue().getNop() < Nop)
					Nop = artifactObject.getValue().getNop();
			}
			
			if (artifact.getArtifactType().equals("DIR"))
			{
				optimalArtifact.setOptimalClassValues(Dit, Nocc, Mpc, Rfc, Lcom, Dac, Nom, Wmpc, Loc, Nop);
				TechnicalDebt td = new TechnicalDebt();
				td.calculateInterestProbabilityMetricsPackage(artifact.getFilesInPackage());
				td.calcucateInterestJava(artifact, optimalArtifact);		
			}
			else 
			{
				optimalArtifact.setOptimalClassValues(Dit, Nocc, Mpc, Rfc, Lcom, Dac, Nom, Wmpc, Loc, Nop);
				TechnicalDebt td = new TechnicalDebt();
				td.calculateInterestProbabilityMetrics(artifact);
				td.calcucateInterestJava(artifact, optimalArtifact);	
			}

		}

	}

	public void calculateOptimalArtifactC(ArrayList<SimilarArtifacts> similarArtifacts) throws SQLException 
	{
		for (int i = 0;  i < similarArtifacts.size(); i ++)
		{
			OptimalArtifact optimalArtifact = new OptimalArtifact();

			ArtifactMetrics artifact = similarArtifacts.get(i).getArtifact();

			// Values from considered class
			double complexity = artifact.getComplexity();
			double linesOfCode = artifact.getNcloc();
			double coupling = artifact.getCoupling();
			double cohesion = artifact.getCohesion();

			// Check only the 5 or less most similar
			int sizeOfArtifacts = similarArtifacts.get(i).getSimilarArtifacts().size();

			if (sizeOfArtifacts > 5)
				sizeOfArtifacts = 5;
			
			TreeMap<Double, ArtifactMetrics> similarArt = similarArtifacts.get(i).getSimilarArtifacts();

			for (Entry<Double, ArtifactMetrics> artifactObject : similarArt.entrySet()) 
			{
				if (sizeOfArtifacts  == 0)
					break;
				sizeOfArtifacts = sizeOfArtifacts - 1;

				if (artifactObject.getValue().getComplexity() < complexity)
					complexity = artifactObject.getValue().getComplexity();
				if (artifactObject.getValue().getNcloc() < linesOfCode)
					linesOfCode = artifactObject.getValue().getNcloc();
				if (artifactObject.getValue().getCohesion() < cohesion)
					cohesion = artifactObject.getValue().getCohesion();
				if (artifactObject.getValue().getCoupling() < coupling)
					coupling = artifactObject.getValue().getCoupling();
			}

			if (artifact.getArtifactType().equals("DIR"))
			{
				optimalArtifact.setOptimalClassValues(complexity, linesOfCode, cohesion, coupling);
				TechnicalDebt td = new TechnicalDebt();
				td.calculateInterestProbabilityMetricsPackage(artifact.getFilesInPackage());
				td.calculateInterestC(artifact, optimalArtifact);		
			}
			else 
			{
				optimalArtifact.setOptimalClassValues(complexity, linesOfCode, cohesion, coupling);
				TechnicalDebt td = new TechnicalDebt();
				td.calculateInterestProbabilityMetrics(artifact);
				td.calculateInterestC(artifact, optimalArtifact);	
			}
		}

	}

	public double getDit()
	{
		return this.optimalDit;
	}

	public double getNocc()
	{
		return this.optimalNocc;
	}

	public double getMpc()
	{
		return this.optimalMpc;
	}

	public double getRfc()
	{
		return this.optimalRfc;
	}

	public double getLcom()
	{
		return this.optimalLcom;
	}

	public double getDac()
	{
		return this.optimalDac;
	}

	public double getNom()
	{
		return this.optimalNom;
	}

	public double getWmpc()
	{
		return this.optimalWmpc;
	}

	public double getLoc()
	{
		return this.optimalLoc;
	}

	public double getNop()
	{
		return this.optimalNop;
	}

	public double getComplexity()
	{
		return this.optimalComplexity;
	}

	public double getLinesOfCode()
	{
		return this.optimalLinesOfCode;
	}

	public double getCohesion()
	{
		return this.optimalCohesion;
	}

	public double getCoupling()
	{
		return this.optimalCoupling;
	}
}
