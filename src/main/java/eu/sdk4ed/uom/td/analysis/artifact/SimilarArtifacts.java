package eu.sdk4ed.uom.td.analysis.artifact;

import java.util.Collections;
import java.util.TreeMap;

public class SimilarArtifacts 
{
	private ArtifactMetrics artifact;
	//HashMap<Double, ArtifactMetrics> similarArtifacts = new HashMap<Double, ArtifactMetrics>();
	private TreeMap<Double, ArtifactMetrics> similarArtifacts = new TreeMap<Double, ArtifactMetrics>(Collections.reverseOrder());

	
	public SimilarArtifacts()
	{
		this.artifact = null;
		this.similarArtifacts = new TreeMap<Double, ArtifactMetrics>(Collections.reverseOrder());
	}
	
	public void setArtifact(ArtifactMetrics a)
	{
		this.artifact = a;
	}
	
	public ArtifactMetrics getArtifact()
	{
		return this.artifact;
	}
	
	public void setSimilarArtifacts(Double similarity , ArtifactMetrics  s)
	{
		this.similarArtifacts.put(similarity, s);
	}
	
	public void updateSimilarArtifact(TreeMap<Double, ArtifactMetrics>  s)
	{
		this.similarArtifacts = s;
	}
	
	public TreeMap<Double, ArtifactMetrics>  getSimilarArtifacts()
	{
		return (TreeMap<Double, ArtifactMetrics>) this.similarArtifacts;
	}	

}
