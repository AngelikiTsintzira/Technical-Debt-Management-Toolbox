/**
 * 
 */
package eu.sdk4ed.uom.td.webService.service;

import java.util.List;

import eu.sdk4ed.uom.td.webService.controller.response.entity.ClassPackageProjectName;
import eu.sdk4ed.uom.td.webService.controller.response.entity.moveClassRefactoringsMetrics;

public interface GeaClassesService {

	List<ClassPackageProjectName> getMoveClassRefactoringsByProjectNameAndIsNew(String projectName, boolean isNew);

	moveClassRefactoringsMetrics getMoveClassRefactoringsMetrics(String projectName);

}
