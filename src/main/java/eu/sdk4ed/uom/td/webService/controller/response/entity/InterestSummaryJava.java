/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

public class InterestSummaryJava extends InterestSummaryAbstract {

	private String name;
	private Double totalInterest;
	private Double totalPrincipal;
	private Double breakingPoint;
	private Double interestProbability;

	public InterestSummaryJava() { }

	public InterestSummaryJava(String name, Double totalInterest, Double totalPrincipal, Double interestProbability) {
		this.name = name;
		this.totalInterest = totalInterest;
		this.totalPrincipal = totalPrincipal;
		this.breakingPoint = totalPrincipal / totalInterest;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breakingPoint == null) ? 0 : breakingPoint.hashCode());
		result = prime * result + ((interestProbability == null) ? 0 : interestProbability.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((totalInterest == null) ? 0 : totalInterest.hashCode());
		result = prime * result + ((totalPrincipal == null) ? 0 : totalPrincipal.hashCode());
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
		InterestSummaryJava other = (InterestSummaryJava) obj;
		if (breakingPoint == null) {
			if (other.breakingPoint != null)
				return false;
		} else if (!breakingPoint.equals(other.breakingPoint))
			return false;
		if (interestProbability == null) {
			if (other.interestProbability != null)
				return false;
		} else if (!interestProbability.equals(other.interestProbability))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (totalInterest == null) {
			if (other.totalInterest != null)
				return false;
		} else if (!totalInterest.equals(other.totalInterest))
			return false;
		if (totalPrincipal == null) {
			if (other.totalPrincipal != null)
				return false;
		} else if (!totalPrincipal.equals(other.totalPrincipal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (breakingPoint == null)
			breakingPoint = 0.0;
		return "InterestSummaryJava [name=" + name + ", totalInterest=" + totalInterest + ", totalPrincipal=" + totalPrincipal + ", breakingPoint=" + breakingPoint + ", interestProbability=" + interestProbability + "]";
	}

}
