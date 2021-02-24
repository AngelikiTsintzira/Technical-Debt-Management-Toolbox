/**
 * 
 */
package eu.sdk4ed.uom.td.webService.persistence;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalMetricsIndicators;
import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalSummary;
import eu.sdk4ed.uom.td.webService.controller.response.entity.Project;
import eu.sdk4ed.uom.td.webService.domain.PrincipalMetrics;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Repository
public interface PrincipalMetricsRepository extends JpaRepository<PrincipalMetrics, Integer> {

	@Query(value = "SELECT DISTINCT new eu.sdk4ed.uom.td.webService.controller.response.entity.Project(p.projectName, p.language) FROM PrincipalMetrics p WHERE p.projectName = :projectID")
	Set<Project> languageForProject(String projectID);

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalMetricsIndicators(p.className, p.tdMinutes, p.principal, p.bugs, p.vulnerabilities, p.duplicatedLinesDensity, p.codeSmells) FROM PrincipalMetrics p WHERE p.projectName = :projectID AND p.version = (SELECT MAX(p.version) FROM PrincipalMetrics p WHERE p.projectName = :projectID)")
	List<PrincipalMetricsIndicators> getPrincipalIndicatorsByProjectName(String projectID);

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalSummary(p.projectName, SUM(p.tdMinutes), SUM(p.principal), SUM(p.bugs), SUM(p.vulnerabilities), SUM(p.duplicatedLinesDensity), SUM(p.codeSmells)) FROM PrincipalMetrics p WHERE p.projectName = :projectID AND p.scope = 'FIL' AND p.version = (SELECT MAX(p.version) FROM PrincipalMetrics p WHERE p.projectName = :projectID)")
	PrincipalSummary getPrincipalSummaryByProjectName(String projectID);

}
