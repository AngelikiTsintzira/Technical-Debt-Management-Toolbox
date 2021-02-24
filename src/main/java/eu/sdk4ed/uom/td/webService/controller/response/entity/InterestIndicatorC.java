/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import eu.sdk4ed.uom.td.webService.domain.CMetrics;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@JsonPropertyOrder({ "name", "Interest", "IP", "LOC", "CC", "NoF", "CD", "Coupling", "Cohesion" })
public class InterestIndicatorC extends InterestIndicatorAbstract {

	private String name;
	private double LOC;
	private double CC;
	private double NoF;
	private double CD;
	private Double IP;
	private Double Interest;
	private Double Coupling;
	private Double Cohesion;

	public InterestIndicatorC() { }

	public InterestIndicatorC(String name, double lOC, double cC, double noF, double cD, Double iP, Double interest, Double coupling, Double cohesion) {
		this.name = name;
		LOC = lOC;
		CC = cC;
		NoF = noF;
		CD = cD;
		IP = iP;
		Interest = interest;
		Coupling = coupling;
		Cohesion = cohesion;
	}

	public InterestIndicatorC(CMetrics cMetric) {
		this.name = cMetric.getClassName();
		this.LOC = cMetric.getLoc();
		this.CC = cMetric.getCyclomaticComplexity();
		this.NoF = cMetric.getNumberOfFunctions();
		this.CD = cMetric.getCommentsDensity();
		this.IP = cMetric.getInterestProbability();
		this.Interest = cMetric.getInterest();
		this.Coupling = cMetric.getCoupling();
		this.Cohesion = cMetric.getCohesion();
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("LOC")
	public double getLOC() {
		return LOC;
	}

	public void setLOC(double LOC) {
		this.LOC = LOC;
	}

	@JsonProperty("CC")
	public double getCC() {
		return CC;
	}

	public void setCC(double cC) {
		CC = cC;
	}

	@JsonProperty("NoF")
	public double getNoF() {
		return NoF;
	}

	public void setNoF(double noF) {
		NoF = noF;
	}

	@JsonProperty("CD")
	public double getCD() {
		return CD;
	}

	public void setCD(double cD) {
		CD = cD;
	}

	@JsonProperty("IP")
	public Double getIP() {
		return IP;
	}

	public void setIP(Double iP) {
		IP = iP;
	}

	@JsonProperty("Interest")
	public Double getInterest() {
		return Interest;
	}

	public void setInterest(Double interest) {
		Interest = interest;
	}

	@JsonProperty("Coupling")
	public Double getCoupling() {
		return Coupling;
	}

	public void setCoupling(Double coupling) {
		Coupling = coupling;
	}

	@JsonProperty("Cohesion")
	public Double getCohesion() {
		return Cohesion;
	}

	public void setCohesion(Double cohesion) {
		Cohesion = cohesion;
	}

	@Override
	public String toString() {
		return "InterestIndicatorC [name=" + name + ", LOC=" + LOC + ", CC=" + CC + ", NoF=" + NoF + ", CD=" + CD + ", IP=" + IP + ", Interest=" + Interest + ", Coupling=" + Coupling + ", Cohesion=" + Cohesion + "]";
	}

}
