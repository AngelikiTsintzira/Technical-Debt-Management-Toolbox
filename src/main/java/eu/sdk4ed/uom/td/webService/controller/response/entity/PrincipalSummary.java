/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

public class PrincipalSummary {

	private String name;
	private Double tdInMinutes;
	private Double tdInCurrency;
	private Long bugs;
	private Long vulnerabilities;
	private Double duplCode;
	private Long codeSmells;

	public PrincipalSummary() { }

	public PrincipalSummary(String name, Double tdInMinutes, Double tdInCurrency, Long bugs, Long vulnerabilities, Double duplCode, Long codeSmells) {
		this.name = name;
		this.tdInMinutes = tdInMinutes;
		this.tdInCurrency = tdInCurrency;
		this.bugs = bugs;
		this.vulnerabilities = vulnerabilities;
		this.duplCode = duplCode;
		this.codeSmells = codeSmells;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getTdInMinutes() {
		return tdInMinutes;
	}

	public void setTdInMinutes(Double tdInMinutes) {
		this.tdInMinutes = tdInMinutes;
	}

	public Double getTdInCurrency() {
		return tdInCurrency;
	}

	public void setTdInCurrency(Double tdInCurrency) {
		this.tdInCurrency = tdInCurrency;
	}

	public Long getBugs() {
		return bugs;
	}

	public void setBugs(Long bugs) {
		this.bugs = bugs;
	}

	public Long getVulnerabilities() {
		return vulnerabilities;
	}

	public void setVulnerabilities(Long vulnerabilities) {
		this.vulnerabilities = vulnerabilities;
	}

	public Double getDuplCode() {
		return duplCode;
	}

	public void setDuplCode(Double duplCode) {
		this.duplCode = duplCode;
	}

	public Long getCodeSmells() {
		return codeSmells;
	}

	public void setCodeSmells(Long codeSmells) {
		this.codeSmells = codeSmells;
	}

	@Override
	public String toString() {
		return "PrincipalSummary [name=" + name + ", tdInMinutes=" + tdInMinutes + ", tdInCurrency=" + tdInCurrency + ", bugs=" + bugs + ", vulnerabilities=" + vulnerabilities + ", duplCode=" + duplCode + ", codeSmells=" + codeSmells + "]";
	}



}
