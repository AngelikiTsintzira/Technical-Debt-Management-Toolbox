/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class InterestSummaryC extends InterestSummaryAbstract {

	private String name;
	private Double totalInterest;
	private Double totalPrincipal;
	private Double breakingPoint;
	private Double interestProbability;

	public InterestSummaryC() { }

	public InterestSummaryC(String name, Double totalInterest, Double totalPrincipal, Double breakingPoint, Double interestProbability) {
		this.name = name;
		this.totalInterest = totalInterest;
		this.totalPrincipal = totalPrincipal;
		this.breakingPoint = breakingPoint;
		this.interestProbability = interestProbability;
		if (this.breakingPoint  == null)
			this.breakingPoint  = 0.0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getTotalInterest() {
		return totalInterest;
	}

	public void setTotalInterest(Double totalInterest) {
		this.totalInterest = totalInterest;
	}

	public Double getTotalPrincipal() {
		return totalPrincipal;
	}

	public void setTotalPrincipal(Double totalPrincipal) {
		this.totalPrincipal = totalPrincipal;
	}

	public Double getBreakingPoint() {
		return breakingPoint;
	}

	public void setBreakingPoint(Double breakingPoint) {
		this.breakingPoint = breakingPoint;
	}

	public Double getInterestProbability() {
		return interestProbability;
	}

	public void setInterestProbability(Double interestProbability) {
		this.interestProbability = interestProbability;
	}

	@Override
	public String toString() {
		if (breakingPoint  == null)
			breakingPoint = 0.0;
		return "InterestSummaryC [name=" + name + ", totalInterest=" + totalInterest + ", totalPrincipal=" + totalPrincipal + ", breakingPoint=" + breakingPoint + ", interestProbability=" + interestProbability + "]";
	}

}
