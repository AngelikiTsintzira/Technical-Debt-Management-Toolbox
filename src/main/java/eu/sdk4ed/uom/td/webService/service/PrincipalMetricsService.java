/**
 * 
 */
package eu.sdk4ed.uom.td.webService.service;

import java.util.List;
import java.util.Set;

import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalMetricsIndicators;
import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalSummary;
import eu.sdk4ed.uom.td.webService.controller.response.entity.Project;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public interface PrincipalMetricsService {

	Set<Project> languageForProject(String projectID);

	List<PrincipalMetricsIndicators> getPrincipalIndicatorsByProjectName(String projectID);

	PrincipalSummary getPrincipalSummaryByProjectName(String projectID);

}
