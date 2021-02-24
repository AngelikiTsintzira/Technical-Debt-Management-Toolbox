/**
 * 
 */
package eu.sdk4ed.uom.td.webService.service;

import java.util.List;

import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorC;
import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestSummaryC;
import eu.sdk4ed.uom.td.webService.controller.response.entity.LineChartBreakingPointTDDivide;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public interface CMetricsService {

	List<Double> getLineChartInterestTD(String projectID);

	List<Double> getLineChartPrincipalTD(String projectID);

	List<Double> getCumulativeInterestLineChart(String projectID);

	double getInterestRankingByProjectName(String projectID);

	List<InterestIndicatorC> getInterestIndicatorByProjectName(String projectID);

	InterestSummaryC interestSummary(String projectID);

	double getInterestProbabilityByProjectName(String projectID);

	List<LineChartBreakingPointTDDivide> getLineChartBreakingPointTD(String projectID);

}
