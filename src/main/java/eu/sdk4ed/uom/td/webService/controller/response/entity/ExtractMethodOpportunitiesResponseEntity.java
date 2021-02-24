/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import java.util.List;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class ExtractMethodOpportunitiesResponseEntity {

	private List<MethodOpportunities> opportunities;

	public ExtractMethodOpportunitiesResponseEntity() { }

	public ExtractMethodOpportunitiesResponseEntity(List<MethodOpportunities> opportunities) {
		this.opportunities = opportunities;
	}

	public List<MethodOpportunities> getOpportunities() {
		return opportunities;
	}

	public void setOpportunities(List<MethodOpportunities> opportunities) {
		this.opportunities = opportunities;
	}


}
