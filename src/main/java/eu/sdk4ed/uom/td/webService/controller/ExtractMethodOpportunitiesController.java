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

import eu.sdk4ed.uom.td.webService.controller.response.entity.ExtractMethodOpportunitiesResponseEntity;
import eu.sdk4ed.uom.td.webService.service.OpportunitiesService;

@RestController
@RequestMapping(value = "/extractMethodOpportunities")
public class ExtractMethodOpportunitiesController extends BaseController {

	@Autowired
	private OpportunitiesService opportunitiesService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ExtractMethodOpportunitiesResponseEntity> search(@RequestParam(value = "projectID", required = true) String projectID) {
		logger.info("> search projectID: {}, language: {}", projectID);

		ExtractMethodOpportunitiesResponseEntity extractMethodOpportunitiesResponseEntity = new ExtractMethodOpportunitiesResponseEntity(opportunitiesService.extractMethodOpportunitiesByProjectName(projectID));

		logger.info("< search projectID: {}, language: {}", projectID);
		return new ResponseEntity<ExtractMethodOpportunitiesResponseEntity>(extractMethodOpportunitiesResponseEntity, HttpStatus.OK);
	}

}
