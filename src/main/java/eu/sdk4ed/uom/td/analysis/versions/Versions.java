package eu.sdk4ed.uom.td.analysis.versions;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import eu.sdk4ed.uom.td.analysis.artifact.ArtifactMetrics;

public class Versions 
{
	private int versionId;
	private String projectName;
	private ConcurrentHashMap<String, ArtifactMetrics> artifacts;
	
	public Versions()
	{
		this.artifacts = new ConcurrentHashMap<String, ArtifactMetrics>();
		this.versionId = 0;
		this.projectName = null;
	}
	
	public void setVersionId(int id)
	{
		this.versionId = id;
	}
	
	public void setProjectName(String pName)
	{
		this.projectName = pName;
	}
	
	public void setArtifacts(ConcurrentHashMap<String, ArtifactMetrics> artifacts)
	{
		this.artifacts  =  artifacts;
	}
	
	public int getVersionId()
	{
		return this.versionId;
	}
	
	public String getProjectName()
	{
		return this.projectName;
	}
	
	public ConcurrentHashMap<String, ArtifactMetrics> getArtifacts()
	{
		return this.artifacts;
	}

}
