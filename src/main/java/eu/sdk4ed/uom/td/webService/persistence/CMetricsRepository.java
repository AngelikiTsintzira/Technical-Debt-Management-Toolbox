/**
 * 
 */
package eu.sdk4ed.uom.td.webService.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorC;
import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestSummaryC;
import eu.sdk4ed.uom.td.webService.controller.response.entity.LineChartBreakingPointTDDivide;
import eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterest;
import eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterestProbability;
import eu.sdk4ed.uom.td.webService.domain.CMetrics;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Repository
public interface CMetricsRepository extends JpaRepository<CMetrics, Integer> {

	@Query(value = "SELECT new java.lang.Double(SUM(c.interest)) FROM CMetrics c WHERE c.projectName = :projectID AND c.scope = 'FIL' GROUP BY c.version")
	List<Double> getLineChartInterestTD(String projectID);

	@Query(value = "SELECT new java.lang.Double(SUM(c.principal)) FROM CMetrics c WHERE c.projectName = :projectID AND c.scope = 'FIL' GROUP BY c.version")
	List<Double> getLineChartPrincipalTD(String projectID);

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterest(AVG(c.interest), c.projectName) FROM CMetrics c WHERE c.version IN (SELECT MAX(cm.version) FROM CMetrics cm GROUP BY cm.projectName) AND c.scope = 'FIL' GROUP BY c.projectName ORDER BY AVG(c.interest) DESC")
	List<ProjectAVGInterest> getAVGInterestByProjectName();

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorC(c.className, c.loc, c.cyclomaticComplexity, c.numberOfFunctions, c.commentsDensity, ROUND(c.interestProbability, 2), ROUND(c.interest, 2), c.coupling, c.cohesion) FROM CMetrics c WHERE c.projectName = :projectID AND c.version = (SELECT MAX(c.version) FROM CMetrics c WHERE c.projectName = :projectID)")
	List<InterestIndicatorC> getInterestIndicatorByProjectName(String projectID);

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.InterestSummaryC(c.projectName, SUM(c.interest), SUM(c.principal), (SUM(c.principal) / SUM(c.interest)), AVG(c.interestProbability) ) FROM CMetrics c WHERE c.projectName = :projectID AND c.scope = 'FIL' AND c.version = (SELECT MAX(c.version) FROM CMetrics c WHERE c.projectName = :projectID)")
	InterestSummaryC interestSummary(String projectID);

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterestProbability(AVG(c.interestProbability), c.projectName) FROM CMetrics c WHERE c.version IN (SELECT MAX(cm.version) FROM CMetrics cm GROUP BY cm.projectName) AND c.scope = 'FIL' GROUP BY c.projectName ORDER BY AVG(c.interestProbability) DESC")
	List<ProjectAVGInterestProbability> getAVGInterestProbabilityByProjectName();

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.LineChartBreakingPointTDDivide(SUM(c.principal), SUM(c.interest)) FROM CMetrics c WHERE c.projectName = :projectID AND c.scope = 'FIL' GROUP BY c.version")
	List<LineChartBreakingPointTDDivide> getLineChartBreakingPointTD(String projectID);
}
