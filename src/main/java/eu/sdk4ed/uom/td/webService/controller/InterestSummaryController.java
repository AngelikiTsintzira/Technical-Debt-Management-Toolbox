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

import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestSummaryResponseEnity;
import eu.sdk4ed.uom.td.webService.service.CMetricsService;
import eu.sdk4ed.uom.td.webService.service.JavaMetricsService;

@RestController
@RequestMapping(value = "/interestSummary")
public class InterestSummaryController extends BaseController {

	@Autowired
	private CMetricsService cMetricsService;
	
	@Autowired
	private JavaMetricsService javaMetricsService;
	
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InterestSummaryResponseEnity> search(@RequestParam(value = "projectID", required = true) String projectID, @RequestParam(value = "language", required = true) String language) {
		logger.info("> search projectID: {}, language: {}", projectID, language);
		
		InterestSummaryResponseEnity interestSummaryResponseEnity = null;
		
		switch (language) {
		case "c":
			interestSummaryResponseEnity = new InterestSummaryResponseEnity(cMetricsService.interestSummary(projectID));
			break;
		case "java":
			interestSummaryResponseEnity = new InterestSummaryResponseEnity(javaMetricsService.interestSummary(projectID));
			break;
		default:
			break;
		}
		
		logger.info("< search projectID: {}, language: {}", projectID, language);
		return new ResponseEntity<InterestSummaryResponseEnity>(interestSummaryResponseEnity, HttpStatus.OK);
	}

}
