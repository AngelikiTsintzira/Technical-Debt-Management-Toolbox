/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class InterestRankResponseEnity {

	private Double interestRank;

	public InterestRankResponseEnity() {}

	public InterestRankResponseEnity(Double interestRank) {
		this.interestRank = interestRank;
	}

	public Double getInterestRank() {
		return interestRank;
	}

	public void setInterestRank(Double interestRank) {
		this.interestRank = interestRank;
	}

}
