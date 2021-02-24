/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.sdk4ed.uom.td.webService.controller.response.entity.ClassPackageProjectName;
import eu.sdk4ed.uom.td.webService.controller.response.entity.MoveClassRefactorings;
import eu.sdk4ed.uom.td.webService.controller.response.entity.MoveClassRefactoringsChild;
import eu.sdk4ed.uom.td.webService.controller.response.entity.MoveClassRefactoringsNameChildren;
import eu.sdk4ed.uom.td.webService.controller.response.entity.MoveClassRefactoringsResponseEntity;
import eu.sdk4ed.uom.td.webService.service.GeaClassesService;

@RestController
@RequestMapping(value = "/moveClassRefactorings")
public class MoveClassRefactoringsController extends BaseController {

	private static final String SPLIT_BY = "\\.";

	@Autowired
	private GeaClassesService geaClassesService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MoveClassRefactoringsResponseEntity> search(@RequestParam(value = "projectID", required = true) String projectID, @RequestParam(value = "isNew", required = true) int isNew) {
		logger.info("> search projectID: {}, isNew: {}", projectID, isNew);

		MoveClassRefactoringsResponseEntity moveClassRefactoringsResponseEntity;
		List<MoveClassRefactoringsNameChildren> children = new ArrayList<>();

		List<ClassPackageProjectName> res = geaClassesService.getMoveClassRefactoringsByProjectNameAndIsNew(projectID, (isNew == 1 ? true : false));

		// Check if there are refactorings in database
		if (res != null && !res.isEmpty())
		{
			String packageName = res.get(0).getPackageName();
			String rootPackage = packageName.split(SPLIT_BY)[0];

			Map<String, Set<String>> myMap = new HashMap<>();
			myMap.put(packageName, new LinkedHashSet<>(Arrays.asList(res.get(0).getClassName())));
			for (int i = 1; i < res.size(); i++) {
				if (Objects.equals(res.get(i).getPackageName(), packageName)) {
					if (!myMap.containsKey(packageName))
						myMap.put(packageName, new LinkedHashSet<>(Arrays.asList(res.get(i).getClassName())));
					else {
						Set<String> classNamesSet = myMap.get(packageName);
						classNamesSet.add(res.get(i).getClassName());
						myMap.put(packageName, classNamesSet);
					}

				}
				else {
					packageName = res.get(i).getPackageName(); 
					myMap.put(packageName, new LinkedHashSet<>(Arrays.asList(res.get(i).getClassName())));
				}
			}

			for (Map.Entry<String, Set<String>> entry : myMap.entrySet())
				children.add(new MoveClassRefactoringsNameChildren(entry.getKey().replaceAll(rootPackage + SPLIT_BY, ""), entry.getValue().stream().map(MoveClassRefactoringsChild::new).collect(Collectors.toList())));

			moveClassRefactoringsResponseEntity = new MoveClassRefactoringsResponseEntity(new MoveClassRefactorings(rootPackage, children));

			logger.info("< search projectID: {}, isNew: {}", projectID, isNew);
			return new ResponseEntity<MoveClassRefactoringsResponseEntity>(moveClassRefactoringsResponseEntity, HttpStatus.OK);
		}
		else
		{
			moveClassRefactoringsResponseEntity = new MoveClassRefactoringsResponseEntity(new MoveClassRefactorings());
			return new ResponseEntity<MoveClassRefactoringsResponseEntity>(moveClassRefactoringsResponseEntity, HttpStatus.OK);
		}
	}

}
