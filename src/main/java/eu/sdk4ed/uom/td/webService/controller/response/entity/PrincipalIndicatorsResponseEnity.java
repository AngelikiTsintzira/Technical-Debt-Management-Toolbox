package eu.sdk4ed.uom.td.webService.controller.response.entity;

public class PrincipalIndicatorsResponseEnity {

	private PrincipalIndicatorsColumnsRows principalIndicators;

	public PrincipalIndicatorsResponseEnity() { }

	public PrincipalIndicatorsResponseEnity(PrincipalIndicatorsColumnsRows principalIndicators) {
		super();
		this.principalIndicators = principalIndicators;
	}

	public PrincipalIndicatorsColumnsRows getPrincipalIndicators() {
		return principalIndicators;
	}

	public void setPrincipalIndicators(PrincipalIndicatorsColumnsRows principalIndicators) {
		this.principalIndicators = principalIndicators;
	}

}
