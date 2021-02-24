/**
 * 
 */
package eu.sdk4ed.uom.td.webService.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sdk4ed.uom.td.webService.controller.response.entity.MethodOpportunities;
import eu.sdk4ed.uom.td.webService.persistence.OpportunitiesRepository;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Service
public class OpportunitiesServiceBean implements OpportunitiesService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OpportunitiesRepository opportunitiesRepository;

	@Override
	public List<MethodOpportunities> extractMethodOpportunitiesByProjectName(String projectName) {
		logger.info("> extractMethodOpportunitiesByProjectName: {}", projectName);
		
		List<MethodOpportunities> methodOpportunities = opportunitiesRepository.findByProjectName(projectName).stream().map(MethodOpportunities::new).collect(Collectors.toList());
		
		logger.info("< extractMethodOpportunitiesByProjectName: {}", projectName);
		return methodOpportunities;
	}

}
