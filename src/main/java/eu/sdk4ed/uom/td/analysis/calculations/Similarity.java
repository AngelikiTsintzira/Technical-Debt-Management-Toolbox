package eu.sdk4ed.uom.td.analysis.calculations;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import eu.sdk4ed.uom.td.analysis.artifact.ArtifactMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.SimilarArtifacts;

public class Similarity 
{	
	private ArrayList<SimilarArtifacts> similarArtifacts = new ArrayList<SimilarArtifacts>();

	public void calculateSimilarityBetweenArtifacts(ConcurrentHashMap<String, ArtifactMetrics> artifacts)
	{
		similarArtifacts = new ArrayList<SimilarArtifacts>();

		for (Entry<String, ArtifactMetrics> currentArtifact : artifacts.entrySet()) 
		{
			SimilarArtifacts similar = new SimilarArtifacts();

			similar.setArtifact(currentArtifact.getValue());
			
			for (Entry<String, ArtifactMetrics> investigatedArtifact : artifacts.entrySet()) 
			{
				if (!currentArtifact.getValue().getArtifactName().equals(investigatedArtifact.getValue().getArtifactName()))
				{
					double numOfClassSimilarity = calculateSimilarityBetweenMetrics(currentArtifact.getValue().getNumOfClasses(), investigatedArtifact.getValue().getNumOfClasses());
					double complexitySimilarity = calculateSimilarityBetweenMetrics(currentArtifact.getValue().getComplexity(), investigatedArtifact.getValue().getComplexity());
					double numfOfFunctionsSimilarity = calculateSimilarityBetweenMetrics(currentArtifact.getValue().getFunctions(), investigatedArtifact.getValue().getFunctions());
					double nclocSimilarity = calculateSimilarityBetweenMetrics(currentArtifact.getValue().getNcloc(), investigatedArtifact.getValue().getNcloc());
					double statementsSimilarity = calculateSimilarityBetweenMetrics(currentArtifact.getValue().getStatements(), investigatedArtifact.getValue().getStatements());
					double technicalDebtSimilarity = calculateSimilarityBetweenMetrics(currentArtifact.getValue().getTechnicalDebt(),investigatedArtifact.getValue().getTechnicalDebt());
					double similarityValue = numOfClassSimilarity + complexitySimilarity + numfOfFunctionsSimilarity + nclocSimilarity
							+ statementsSimilarity + technicalDebtSimilarity;

					similarityValue = similarityValue / 6;		
					similar.setSimilarArtifacts(similarityValue,investigatedArtifact.getValue());
				}
			}

			this.similarArtifacts.add(similar);
		}
	}

	private double calculateSimilarityBetweenMetrics(double metric1, double metric2) 
	{
		double Similarity = 0;
		if (metric1 != 0 && metric2 != 0) 
		{
			Similarity = 100 - (Math.abs(metric1 - metric2) / Math.max(metric1, metric2) * 100);
		}
		return Similarity;
	}

	/*
	public void findMostSimilarArtifacts()
	{
		for (int i = 0; i < this.similarArtifacts.size(); i++) 
		{
			Map<Double, ArtifactMetrics> reverseSortedMap = new TreeMap<Double, ArtifactMetrics>(Collections.reverseOrder());
			reverseSortedMap.putAll(similarArtifacts.get(i).getSimilarArtifacts());
			HashMap<Double, ArtifactMetrics> sorted = new HashMap<Double, ArtifactMetrics>(reverseSortedMap);
	        similarArtifacts.get(i).updateSimilarArtifact(sorted);	        
		}	
	}
	*/
	
	public ArrayList<SimilarArtifacts> getSimilarArtifacts()
	{
		return this.similarArtifacts;
	}

}
