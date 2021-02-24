/**
 * 
 */
package eu.sdk4ed.uom.td.webService.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.sdk4ed.uom.td.webService.controller.response.entity.ClassPackageProjectName;
import eu.sdk4ed.uom.td.webService.controller.response.entity.moveClassRefactoringsMetrics;
import eu.sdk4ed.uom.td.webService.persistence.GeaClassesRepository;

@Service
public class GeaClassesServiceBean implements GeaClassesService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GeaClassesRepository geaClassesRepository;

	@Override
	public List<ClassPackageProjectName> getMoveClassRefactoringsByProjectNameAndIsNew(String projectName, boolean isNew) {
		logger.info("> getMoveClassRefactoringsByProjectNameAndIsNew: {}, {}", projectName, isNew);

		List<ClassPackageProjectName> classesPackagesNames = geaClassesRepository.getMoveClassRefactoringsByProjectNameAndIsNew(projectName, isNew);

		logger.info("< getMoveClassRefactoringsByProjectNameAndIsNew: {}, {}", projectName, isNew);
		return classesPackagesNames;
	}
	
	@Override
	public moveClassRefactoringsMetrics getMoveClassRefactoringsMetrics(String projectName) {
		logger.info("> getMoveClassRefactoringsMetrics: {}", projectName);

		moveClassRefactoringsMetrics metrics = geaClassesRepository.getMoveClassRefactoringsMetrics(projectName);

		logger.info("< getMoveClassRefactoringsMetrics: {}", projectName);
		return metrics;
	}

}
