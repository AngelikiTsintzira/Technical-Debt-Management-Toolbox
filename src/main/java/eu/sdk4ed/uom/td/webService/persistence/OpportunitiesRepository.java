/**
 * 
 */
package eu.sdk4ed.uom.td.webService.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.sdk4ed.uom.td.webService.domain.Opportunities;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Repository
public interface OpportunitiesRepository extends JpaRepository<Opportunities, Integer> {

	@Query(value = "SELECT o FROM Opportunities o WHERE o.projectName = :projectName")
	List<Opportunities> findByProjectName(String projectName);

}
