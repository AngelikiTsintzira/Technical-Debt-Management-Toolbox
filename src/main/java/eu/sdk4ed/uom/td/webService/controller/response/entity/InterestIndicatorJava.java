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

@JsonPropertyOrder({ "name", "Interest", "IP", "MPC", "DIT", "NOCC", "RFC", "LCOM", "WMC", "DAC", "NOM", "LOC", "NOP" })
public class InterestIndicatorJava extends InterestIndicatorAbstract {
	
	private String name;
	private double MPC;
	private double DIT;
	private double NOCC;
	private double RFC;
	private double LCOM;
	private double WMC;
	private double DAC;
	private double NOM;
	private double LOC;
	private double NOP;
	private Double IP;
	private Double Interest;
	//private Double REM;
	//private Double CPM;

	public InterestIndicatorJava() {}

	public InterestIndicatorJava(String name, double mPC, double dIT, double nOCC, double rFC, double lCOM, double wMC, double dAC, double nOM, double lOC, double nOP, Double iP, Double interest) {
		this.name = name;
		MPC = mPC;
		DIT = dIT;
		NOCC = nOCC;
		RFC = rFC;
		LCOM = lCOM;
		WMC = wMC;
		DAC = dAC;
		NOM = nOM;
		LOC = lOC;
		NOP = nOP;
		IP = iP;
		Interest = interest;
		//REM = rEM;
		//CPM = cPM;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("MPC")
	public double getMPC() {
		return MPC;
	}

	public void setMPC(double mPC) {
		MPC = mPC;
	}

	@JsonProperty("DIT")
	public double getDIT() {
		return DIT;
	}

	public void setDIT(double dIT) {
		DIT = dIT;
	}

	@JsonProperty("NOCC")
	public double getNOCC() {
		return NOCC;
	}

	public void setNOCC(double nOCC) {
		NOCC = nOCC;
	}

	@JsonProperty("RFC")
	public double getRFC() {
		return RFC;
	}

	public void setRFC(double rFC) {
		RFC = rFC;
	}

	@JsonProperty("LCOM")
	public double getLCOM() {
		return LCOM;
	}

	public void setLCOM(double lCOM) {
		LCOM = lCOM;
	}

	@JsonProperty("WMC")
	public double getWMC() {
		return WMC;
	}

	public void setWMC(double wMC) {
		WMC = wMC;
	}

	@JsonProperty("DAC")
	public double getDAC() {
		return DAC;
	}

	public void setDAC(double dAC) {
		DAC = dAC;
	}

	@JsonProperty("NOM")
	public double getNOM() {
		return NOM;
	}

	public void setNOM(double nOM) {
		NOM = nOM;
	}

	@JsonProperty("LOC")
	public double getLOC() {
		return LOC;
	}

	public void setLOC(double lOC) {
		LOC = lOC;
	}

	@JsonProperty("NOP")
	public double getNOP() {
		return NOP;
	}

	public void setNOP(double nOP) {
		NOP = nOP;
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
	
	/*
	@JsonProperty("REM")
	public Double getREM() {
		return REM;
	}

	public void setREM(Double rEM) {
		REM = rEM;
	}

	@JsonProperty("CPM")
	public Double getCPM() {
		return CPM;
	}

	public void setCPM(Double cPM) {
		CPM = cPM;
	}
	*/


	@Override
	public String toString() {
		return "InterestIndicatorJava [name=" + name + ", MPC=" + MPC + ", DIT=" + DIT + ", NOCC=" + NOCC + ", RFC=" + RFC + ", LCOM=" + LCOM + ", WMC=" + WMC + ", DAC=" + DAC + ", NOM=" + NOM + ", LOC=" + LOC + ", NOP=" + NOP + ", IP=" + IP + ", Interest=" + Interest + "]";
	}

}
