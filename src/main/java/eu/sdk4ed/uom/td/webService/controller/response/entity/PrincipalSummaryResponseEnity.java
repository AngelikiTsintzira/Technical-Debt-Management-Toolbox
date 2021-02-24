package eu.sdk4ed.uom.td.webService.controller.response.entity;

public class PrincipalSummaryResponseEnity {

	private PrincipalSummary principalSummary;

	public PrincipalSummaryResponseEnity() { }

	public PrincipalSummaryResponseEnity(PrincipalSummary principalSummary) {
		this.principalSummary = principalSummary;
	}

	public PrincipalSummary getPrincipalSummary() {
		return principalSummary;
	}

	public void setPrincipalSummary(PrincipalSummary principalSummary) {
		this.principalSummary = principalSummary;
	}

}