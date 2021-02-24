/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@JsonPropertyOrder({ "name", "tdInMinutes", "tdInCurrency", "bugs", "vulnerabilities", "duplCode", "codeSmells" })
public class PrincipalIndicator {

	private String projectName;
	private float tdMinutes;
	private double principal;
	private int bugs;
	private int vulnerabilities;
	private float duplicatedLinesDensity;
	private int codeSmells;

	public PrincipalIndicator() { }

	public PrincipalIndicator(String projectName, float tdMinutes, double principal, int bugs, int vulnerabilities, float duplicatedLinesDensity, int codeSmells) {
		this.projectName = projectName;
		this.tdMinutes = tdMinutes;
		this.principal = principal;
		this.bugs = bugs;
		this.vulnerabilities = vulnerabilities;
		this.duplicatedLinesDensity = duplicatedLinesDensity;
		this.codeSmells = codeSmells;
	}

	@JsonProperty("name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@JsonProperty("tdInMinutes")
	public float getTdMinutes() {
		return tdMinutes;
	}

	public void setTdMinutes(float tdMinutes) {
		this.tdMinutes = tdMinutes;
	}

	@JsonProperty("tdInCurrency")
	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	@JsonProperty("bugs")
	public int getBugs() {
		return bugs;
	}

	public void setBugs(int bugs) {
		this.bugs = bugs;
	}

	@JsonProperty("vulnerabilities")
	public int getVulnerabilities() {
		return vulnerabilities;
	}

	public void setVulnerabilities(int vulnerabilities) {
		this.vulnerabilities = vulnerabilities;
	}

	@JsonProperty("duplCode")
	public float getDuplicatedLinesDensity() {
		return duplicatedLinesDensity;
	}

	public void setDuplicatedLinesDensity(float duplicatedLinesDensity) {
		this.duplicatedLinesDensity = duplicatedLinesDensity;
	}

	@JsonProperty("codeSmells")
	public int getCodeSmells() {
		return codeSmells;
	}

	public void setCodeSmells(int codeSmells) {
		this.codeSmells = codeSmells;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bugs;
		result = prime * result + codeSmells;
		result = prime * result + Float.floatToIntBits(duplicatedLinesDensity);
		long temp;
		temp = Double.doubleToLongBits(principal);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + Float.floatToIntBits(tdMinutes);
		result = prime * result + vulnerabilities;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrincipalIndicator other = (PrincipalIndicator) obj;
		if (bugs != other.bugs)
			return false;
		if (codeSmells != other.codeSmells)
			return false;
		if (Float.floatToIntBits(duplicatedLinesDensity) != Float.floatToIntBits(other.duplicatedLinesDensity))
			return false;
		if (Double.doubleToLongBits(principal) != Double.doubleToLongBits(other.principal))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (Float.floatToIntBits(tdMinutes) != Float.floatToIntBits(other.tdMinutes))
			return false;
		if (vulnerabilities != other.vulnerabilities)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PrincipalIndicator [projectName=" + projectName + ", tdMinutes=" + tdMinutes + ", principal=" + principal + ", bugs=" + bugs + ", vulnerabilities=" + vulnerabilities + ", duplicatedLinesDensity=" + duplicatedLinesDensity + ", codeSmells=" + codeSmells + "]";
	}

}
