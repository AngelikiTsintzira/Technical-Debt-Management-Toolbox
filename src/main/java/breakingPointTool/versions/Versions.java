package eu.sdk4ed.uom.td.analysis.versions;

import java.util.ArrayList;

import eu.sdk4ed.uom.td.analysis.artifact.PackageMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.PackageMetricsC;

public class Versions 
{
	private int versionId;
	private String projectName;
	private ArrayList<PackageMetrics> packages;
	private ArrayList<PackageMetricsC> packagesC;
	
	public Versions()
	{
		this.packages = new ArrayList<PackageMetrics>();
		this.packagesC = new ArrayList<PackageMetricsC>();
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
	
	public void setPackageInProject(ArrayList<PackageMetrics> p )
	{
		this.packages.addAll(p);
	}
	
	public void setPackageInProjectC(ArrayList<PackageMetricsC> p )
	{
		this.packagesC.addAll(p);
	}
	
	public int getVersionId()
	{
		return this.versionId;
	}
	
	public String getProjectName()
	{
		return this.projectName;
	}
	
	public ArrayList<PackageMetrics> getPackages()
	{
		return this.packages;
	}
	
	public ArrayList<PackageMetricsC> getPackagesC()
	{
		return this.packagesC;
	}

}
