/**
 * 
 */
package eu.sdk4ed.uom.td.webService.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalMetricsIndicators;
import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalSummary;
import eu.sdk4ed.uom.td.webService.controller.response.entity.Project;
import eu.sdk4ed.uom.td.webService.persistence.PrincipalMetricsRepository;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Service
public class PrincipalMetricsServiceBean implements PrincipalMetricsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PrincipalMetricsRepository principalMetricsRepository;

	@Override
	public Set<Project> languageForProject(String projectID) {
		logger.info("> findAllProjects");

		Set<Project> projects = principalMetricsRepository.languageForProject(projectID);

		logger.info("< findAllProjects");
		return projects;
	}

	@Override
	public List<PrincipalMetricsIndicators> getPrincipalIndicatorsByProjectName(String projectID) {
		logger.info("> getPrincipalIndicatorsByProjectName: {}", projectID);

		List<PrincipalMetricsIndicators> principalIndicatorsByProjectName = principalMetricsRepository.getPrincipalIndicatorsByProjectName(projectID);

		logger.info("< getPrincipalIndicatorsByProjectName: {}", projectID);
		return principalIndicatorsByProjectName;
	}

	@Override
	public PrincipalSummary getPrincipalSummaryByProjectName(String projectID) {
		logger.info("> getPrincipalSummaryByProjectName: {}", projectID);

		PrincipalSummary principalSummary = principalMetricsRepository.getPrincipalSummaryByProjectName(projectID);

		logger.info("< getPrincipalSummaryByProjectName: {}", projectID);
		return principalSummary;
	}

}
