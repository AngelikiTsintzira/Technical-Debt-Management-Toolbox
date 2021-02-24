/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class InterestIndicatorsResponseEnity {

	private InterestIndicatorsColumnsRows interestIndicators;

	public InterestIndicatorsResponseEnity() { }

	public InterestIndicatorsResponseEnity(InterestIndicatorsColumnsRows interestIndicators) {
		this.interestIndicators = interestIndicators;
	}

	public InterestIndicatorsColumnsRows getInterestIndicators() {
		return interestIndicators;
	}

	public void setInterestIndicators(InterestIndicatorsColumnsRows interestIndicators) {
		this.interestIndicators = interestIndicators;
	}

}
