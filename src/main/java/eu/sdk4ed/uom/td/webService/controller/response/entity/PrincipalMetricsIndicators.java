/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import eu.sdk4ed.uom.td.webService.domain.PrincipalMetrics;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class PrincipalMetricsIndicators {

	private String name;
	private float tdInMinutes;
	private double tdInCurrency;
	private int bugs;
	private int vulnerabilities;
	private float duplCode;
	private int codeSmells;

	public PrincipalMetricsIndicators() { }

	public PrincipalMetricsIndicators(String name, float tdInMinutes, double tdInCurrency, int bugs, int vulnerabilities, float duplCode, int codeSmells) {
		this.name = name;
		this.tdInMinutes = tdInMinutes;
		this.tdInCurrency = tdInCurrency;
		this.bugs = bugs;
		this.vulnerabilities = vulnerabilities;
		this.duplCode = duplCode;
		this.codeSmells = codeSmells;
	}

	public PrincipalMetricsIndicators(PrincipalMetrics pm) {
		this.name = pm.getClassName();
		this.tdInMinutes = pm.getTdMinutes();
		this.tdInCurrency = pm.getPrincipal();
		this.bugs = pm.getBugs();
		this.vulnerabilities = pm.getVulnerabilities();
		this.duplCode = pm.getDuplicatedLinesDensity();
		this.codeSmells = pm.getCodeSmells();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getTdInMinutes() {
		return tdInMinutes;
	}

	public void setTdInMinutes(float tdInMinutes) {
		this.tdInMinutes = tdInMinutes;
	}

	public double getTdInCurrency() {
		return tdInCurrency;
	}

	public void setTdInCurrency(double tdInCurrency) {
		this.tdInCurrency = tdInCurrency;
	}

	public int getBugs() {
		return bugs;
	}

	public void setBugs(int bugs) {
		this.bugs = bugs;
	}

	public int getVulnerabilities() {
		return vulnerabilities;
	}

	public void setVulnerabilities(int vulnerabilities) {
		this.vulnerabilities = vulnerabilities;
	}

	public float getDuplCode() {
		return duplCode;
	}

	public void setDuplCode(float duplCode) {
		this.duplCode = duplCode;
	}

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
		result = prime * result + Float.floatToIntBits(duplCode);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(tdInCurrency);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + Float.floatToIntBits(tdInMinutes);
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
		PrincipalMetricsIndicators other = (PrincipalMetricsIndicators) obj;
		if (bugs != other.bugs)
			return false;
		if (codeSmells != other.codeSmells)
			return false;
		if (Float.floatToIntBits(duplCode) != Float.floatToIntBits(other.duplCode))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(tdInCurrency) != Double.doubleToLongBits(other.tdInCurrency))
			return false;
		if (Float.floatToIntBits(tdInMinutes) != Float.floatToIntBits(other.tdInMinutes))
			return false;
		if (vulnerabilities != other.vulnerabilities)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PrincipalMetricsIndicators [name=" + name + ", tdInMinutes=" + tdInMinutes + ", tdInCurrency="
				+ tdInCurrency + ", bugs=" + bugs + ", vulnerabilities=" + vulnerabilities + ", duplCode=" + duplCode
				+ ", codeSmells=" + codeSmells + "]";
	}

}
