/**
 * 
 */
package eu.sdk4ed.uom.td.webService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorJava;
import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestSummaryJava;
import eu.sdk4ed.uom.td.webService.controller.response.entity.LineChartBreakingPointTDDivide;
import eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterest;
import eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterestProbability;
import eu.sdk4ed.uom.td.webService.persistence.JavaMetricsRepository;

@Service
public class JavaMetricsServiceBean implements JavaMetricsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMetricsRepository javaMetricsRepository;

	@Override
	public InterestSummaryJava interestSummary(String projectID) {
		logger.info("> interestSummary: {}", projectID);

		InterestSummaryJava interestSummary = javaMetricsRepository.interestSummary(projectID);

		logger.info("< interestSummary: {}", projectID);
		return interestSummary;
	}

	@Override
	public List<InterestIndicatorJava> getInterestIndicatorByProjectName(String projectID) {
		logger.info("> getInterestIndicatorByProjectName: {}", projectID);

		List<InterestIndicatorJava> interestIndicatorsJava = javaMetricsRepository.getInterestIndicatorByProjectName(projectID);

		logger.info("< getInterestIndicatorByProjectName: {}", projectID);
		return interestIndicatorsJava;
	}

	@Override
	public double getInterestProbabilityByProjectName(String projectID) {
		logger.info("> getInterestProbabilityByProjectName: {}", projectID);

		List<ProjectAVGInterestProbability> projectAVGInterestProbabilities = javaMetricsRepository.getAVGInterestProbabilityByProjectName();
		double rank = 0;

		for (int i = 0; i < projectAVGInterestProbabilities.size(); i++)
			if (Objects.equals(projectAVGInterestProbabilities.get(i).getProjectName(), projectID)) {
				rank = (i + 1) / (double) projectAVGInterestProbabilities.size();
				break;
			}

		logger.info("< getInterestProbabilityByProjectName: {}", projectID);
		return rank;
	}

	@Override
	public double getInterestRankingByProjectName(String projectID) {
		logger.info("> getInterestRankingByProjectName: {}", projectID);

		List<ProjectAVGInterest> projectAVGInterests = javaMetricsRepository.getAVGInterestByProjectName();
		double rank = 0;

		for (int i = 0; i < projectAVGInterests.size(); i++)
			if (Objects.equals(projectAVGInterests.get(i).getProjectName(), projectID)) {
				rank = (i + 1) / (double) projectAVGInterests.size();
				break;
			}

		logger.info("< getInterestRankingByProjectName: {}", projectID);
		return rank;
	}

	@Override
	public List<Double> getLineChartInterestTD(String projectID) {
		logger.info("> getLineChartInterestTD: {}", projectID);

		List<Double> javaLineChartInterestTDs = javaMetricsRepository.getLineChartInterestTD(projectID);

		logger.info("< getLineChartInterestTD: {}", projectID);
		return javaLineChartInterestTDs;
	}

	@Override
	public List<Double> getLineChartPrincipalTD(String projectID) {
		logger.info("> getLineChartPrincipalTD: {}", projectID);

		List<Double> lineChartPrincipalTDs = javaMetricsRepository.getLineChartPrincipalTD(projectID);

		logger.info("< getLineChartPrincipalTD: {}", projectID);
		return lineChartPrincipalTDs;
	}

	@Override
	public List<LineChartBreakingPointTDDivide> getLineChartBreakingPointTD(String projectID) {
		logger.info("> getLineChartBreakingPointTD: {}", projectID);

		List<LineChartBreakingPointTDDivide> lineChartBreakingPointTD = javaMetricsRepository.getLineChartBreakingPointTD(projectID);

		logger.info("< getLineChartBreakingPointTD: {}", projectID);
		return lineChartBreakingPointTD;
	}

	@Override
	public List<Double> getCumulativeInterestLineChart(String projectID) {
		logger.info("> getCumulativeInterestLineChart: {}", projectID);

		List<Double> lineChartInterestTDs = this.getLineChartInterestTD(projectID);
		List<Double> returnedValues = new ArrayList<>();

		if (!lineChartInterestTDs.isEmpty()) {
			returnedValues.add(lineChartInterestTDs.get(0));
			for (int i = 1; i < lineChartInterestTDs.size(); i++)
				returnedValues.add(returnedValues.get(returnedValues.size() - 1) + lineChartInterestTDs.get(i));
		}

		logger.info("< getCumulativeInterestLineChart: {}", projectID);
		return returnedValues;
	}

}
