/**
 * 
 */
package eu.sdk4ed.uom.td.webService.service;

import java.util.List;

import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorJava;
import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestSummaryJava;
import eu.sdk4ed.uom.td.webService.controller.response.entity.LineChartBreakingPointTDDivide;

public interface JavaMetricsService {

	InterestSummaryJava interestSummary(String projectID);

	List<InterestIndicatorJava> getInterestIndicatorByProjectName(String projectID);

	double getInterestProbabilityByProjectName(String projectID);

	double getInterestRankingByProjectName(String projectID);

	List<Double> getLineChartInterestTD(String projectID);

	List<Double> getLineChartPrincipalTD(String projectID);

	List<LineChartBreakingPointTDDivide> getLineChartBreakingPointTD(String projectID);

	List<Double> getCumulativeInterestLineChart(String projectID);

}
