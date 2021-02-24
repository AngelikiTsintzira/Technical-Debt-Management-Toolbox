/**
 * 
 */
package eu.sdk4ed.uom.td.webService.service;

import java.util.List;

import eu.sdk4ed.uom.td.webService.controller.response.entity.MethodOpportunities;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public interface OpportunitiesService {

	List<MethodOpportunities> extractMethodOpportunitiesByProjectName(String projectName);

}
