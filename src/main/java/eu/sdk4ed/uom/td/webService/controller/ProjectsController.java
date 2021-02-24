/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.sdk4ed.uom.td.webService.controller.response.entity.Projects;
import eu.sdk4ed.uom.td.webService.service.PrincipalMetricsService;

@RestController
@RequestMapping(value = "/projectLanguage")
public class ProjectsController extends BaseController {
	
	@Autowired
	private PrincipalMetricsService principalMetricsService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Projects> search(@RequestParam(value = "projectID", required = true) String projectID) {
		logger.info("> find lanuage projectID: {}", projectID );
		
		Projects projects = new Projects(principalMetricsService.languageForProject(projectID));

		logger.info("< get All Projects");
		return new ResponseEntity<Projects>(projects, HttpStatus.OK);
	}

}

	
	
	