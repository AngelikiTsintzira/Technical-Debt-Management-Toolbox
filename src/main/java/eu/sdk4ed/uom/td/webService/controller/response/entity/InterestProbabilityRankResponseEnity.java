/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class InterestProbabilityRankResponseEnity {

	private Double interestProbabilityRank;

	public InterestProbabilityRankResponseEnity() { }

	public InterestProbabilityRankResponseEnity(Double interestProbabilityRank) {
		this.interestProbabilityRank = interestProbabilityRank;
	}

	public Double getInterestProbabilityRank() {
		return interestProbabilityRank;
	}

	public void setInterestProbabilityRank(Double interestProbabilityRank) {
		this.interestProbabilityRank = interestProbabilityRank;
	}

}
