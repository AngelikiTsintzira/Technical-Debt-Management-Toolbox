/**
 * 
 */
package eu.sdk4ed.uom.td.webService.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import eu.sdk4ed.uom.td.webService.controller.response.entity.ClassPackageProjectName;
import eu.sdk4ed.uom.td.webService.controller.response.entity.moveClassRefactoringsMetrics;
import eu.sdk4ed.uom.td.webService.domain.GeaClasses;

@Repository
public interface GeaClassesRepository extends JpaRepository<GeaClasses, Long> {

	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.ClassPackageProjectName(gc.name, gp.name, gc.projectName) FROM GeaClasses gc LEFT JOIN GeaPackages gp ON gc.packageID=gp.id WHERE gc.isNew = :isNew AND gc.projectName = :projectName ORDER BY gp.name ASC")
	List<ClassPackageProjectName> getMoveClassRefactoringsByProjectNameAndIsNew(String projectName, boolean isNew);
	
	
	@Query(value = "SELECT new eu.sdk4ed.uom.td.webService.controller.response.entity.moveClassRefactoringsMetrics(m.name, m.couplingOld, m.couplingNew, m.cohesionOld, m.cohesionNew) FROM GeaProjects m WHERE m.name = :projectID")
	moveClassRefactoringsMetrics getMoveClassRefactoringsMetrics(String projectID);

}
