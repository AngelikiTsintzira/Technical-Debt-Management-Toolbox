/**
 * 
 */
package eu.sdk4ed.uom.td.webService.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorJava;
import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestSummaryJava;
import eu.sdk4ed.uom.td.webService.controller.response.entity.LineChartBreakingPointTDDivide;
import eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterest;
import eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterestProbability;
import eu.sdk4ed.uom.td.webService.domain.JavaMetrics;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Repository
public interface JavaMetricsRepository extends JpaRepository<JavaMetrics, Integer> {

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.InterestSummaryJava(j.projectName, SUM(j.interest), SUM(j.principal), AVG(j.interestProbability)) FROM JavaMetrics j WHERE j.projectName = :projectID AND j.scope = 'FIL' AND j.version = (SELECT MAX(j.version) FROM JavaMetrics j WHERE j.projectName = :projectID)")
	InterestSummaryJava interestSummary(String projectID);

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorJava(j.className, j.mpc, j.dit, j.nocc, j.rfc, j.lcom, j.wmcDec, j.dac, j.nom, j.loc, j.numberOfProperties, ROUND(j.interestProbability, 2), ROUND(j.interest, 2)) FROM JavaMetrics j WHERE j.projectName = :projectID AND j.version = (SELECT MAX(j.version) FROM JavaMetrics j WHERE j.projectName = :projectID)")
	List<InterestIndicatorJava> getInterestIndicatorByProjectName(String projectID);

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterestProbability(AVG(j.interestProbability), j.projectName) FROM JavaMetrics j WHERE j.version IN (SELECT MAX(j.version) FROM JavaMetrics j GROUP BY j.projectName) AND j.scope = 'FIL' GROUP BY j.projectName ORDER BY AVG(j.interestProbability) ASC")
	List<ProjectAVGInterestProbability> getAVGInterestProbabilityByProjectName();

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.ProjectAVGInterest(AVG(j.interest), j.projectName) FROM JavaMetrics j WHERE j.version IN (SELECT MAX(j.version) FROM JavaMetrics j GROUP BY j.projectName) AND j.scope = 'FIL' GROUP BY j.projectName ORDER BY AVG(j.interest) ASC")
	List<ProjectAVGInterest> getAVGInterestByProjectName();

	@Query(value = "SELECT new java.lang.Double(SUM(j.interest)) FROM JavaMetrics j WHERE j.projectName = :projectID AND j.scope = 'FIL' GROUP BY j.version")
	List<Double> getLineChartInterestTD(String projectID);

	@Query(value = "SELECT new java.lang.Double(SUM(j.principal)) FROM JavaMetrics j WHERE j.projectName = :projectID AND j.scope = 'FIL' GROUP BY j.version")
	List<Double> getLineChartPrincipalTD(String projectID);

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.LineChartBreakingPointTDDivide(SUM(j.principal), SUM(j.interest)) FROM JavaMetrics j WHERE j.projectName = :projectID AND j.scope = 'FIL' GROUP BY j.version")
	List<LineChartBreakingPointTDDivide> getLineChartBreakingPointTD(String projectID);

}
