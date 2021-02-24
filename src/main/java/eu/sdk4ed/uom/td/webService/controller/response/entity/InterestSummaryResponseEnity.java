package eu.sdk4ed.uom.td.webService.controller.response.entity;

public class InterestSummaryResponseEnity {

	private InterestSummaryAbstract interestSummary;

	public InterestSummaryResponseEnity() { }

	public InterestSummaryResponseEnity(InterestSummaryAbstract interestSummary) {
		this.interestSummary = interestSummary;
	}

	public InterestSummaryAbstract getInterestSummary() {
		return interestSummary;
	}

	public void setInterestSummary(InterestSummaryAbstract interestSummary) {
		this.interestSummary = interestSummary;
	}

}