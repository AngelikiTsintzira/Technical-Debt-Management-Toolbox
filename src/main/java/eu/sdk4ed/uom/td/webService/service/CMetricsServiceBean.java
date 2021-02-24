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

import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorC;
import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestSummaryC;
import eu.sdk4ed.uom.td.webService.controller.response.entity.LineChartBreakingPointTDDivide;
import eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterest;
import eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterestProbability;
import eu.sdk4ed.uom.td.webService.persistence.CMetricsRepository;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Service
public class CMetricsServiceBean implements CMetricsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CMetricsRepository cMetricsRepository;

	@Override
	public List<Double> getLineChartInterestTD(String projectID) {
		logger.info("> getLineChartInterestTD: {}", projectID);

		List<Double> cLineChartInterestTDs = cMetricsRepository.getLineChartInterestTD(projectID);

		logger.info("< getLineChartInterestTD: {}", projectID);
		return cLineChartInterestTDs;
	}

	@Override
	public List<Double> getLineChartPrincipalTD(String projectID) {
		logger.info("> getLineChartPrincipalTD: {}", projectID);

		List<Double> cLineChartPrincipalTDs = cMetricsRepository.getLineChartPrincipalTD(projectID);

		logger.info("< getLineChartPrincipalTD: {}", projectID);
		return cLineChartPrincipalTDs;
	}

	@Override
	public List<Double> getCumulativeInterestLineChart(String projectID) {
		logger.info("> getCumulativeInterestLineChart: {}", projectID);

		List<Double> cLineChartInterestTDs = this.getLineChartInterestTD(projectID);
		List<Double> returnedValues = new ArrayList<>();

		if (!cLineChartInterestTDs.isEmpty()) {
			returnedValues.add(cLineChartInterestTDs.get(0));
			for (int i = 1; i < cLineChartInterestTDs.size(); i++)
				returnedValues.add(returnedValues.get(returnedValues.size() - 1) + cLineChartInterestTDs.get(i));
		}

		logger.info("< getCumulativeInterestLineChart: {}", projectID);
		return returnedValues;
	}

	@Override
	public double getInterestRankingByProjectName(String projectID) {
		logger.info("> getInterestRankingByProjectName: {}", projectID);

		List<ProjectAVGInterest> projectAVGInterests = cMetricsRepository.getAVGInterestByProjectName();
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
	public List<InterestIndicatorC> getInterestIndicatorByProjectName(String projectID) {
		logger.info("> getInterestIndicatorByProjectName: {}", projectID);

		List<InterestIndicatorC> interestIndicatorsC = cMetricsRepository.getInterestIndicatorByProjectName(projectID);

		logger.info("< getInterestIndicatorByProjectName: {}", projectID);
		return interestIndicatorsC;
	}

	@Override
	public InterestSummaryC interestSummary(String projectID) {
		logger.info("> interestSummary: {}", projectID);

		InterestSummaryC interestSummary = cMetricsRepository.interestSummary(projectID);

		logger.info("< interestSummary: {}", projectID);
		return interestSummary;
	}

	@Override
	public double getInterestProbabilityByProjectName(String projectID) {
		logger.info("> getInterestProbabilityByProjectName: {}", projectID);

		List<ProjectAVGInterestProbability> projectAVGInterestProbabilities = cMetricsRepository.getAVGInterestProbabilityByProjectName();
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
	public List<LineChartBreakingPointTDDivide> getLineChartBreakingPointTD(String projectID) {
		logger.info("> getLineChartBreakingPointTD: {}", projectID);

		List<LineChartBreakingPointTDDivide> lineChartBreakingPointTD = cMetricsRepository.getLineChartBreakingPointTD(projectID);

		logger.info("< getLineChartBreakingPointTD: {}", projectID);
		return lineChartBreakingPointTD;
	}

}
