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

import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestRankResponseEnity;
import eu.sdk4ed.uom.td.webService.service.CMetricsService;
import eu.sdk4ed.uom.td.webService.service.JavaMetricsService;

@RestController
@RequestMapping(value = "/interestRanking")
public class InterestRankingController extends BaseController {

	@Autowired
	private CMetricsService cMetricsService;

	@Autowired
	private JavaMetricsService javaMetricsService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InterestRankResponseEnity> search(@RequestParam(value = "projectID", required = true) String projectID, @RequestParam(value = "language", required = true) String language) {
		logger.info("> search projectID: {}, language: {}", projectID, language);

		InterestRankResponseEnity interestRankResponseEnity = null;

		switch (language) {
		case "c":
			interestRankResponseEnity = new InterestRankResponseEnity(cMetricsService.getInterestRankingByProjectName(projectID));

			break;
		case "java":
			interestRankResponseEnity = new InterestRankResponseEnity(javaMetricsService.getInterestRankingByProjectName(projectID));

			break;
		default:
			break;
		}

		logger.info("< search projectID: {}, language: {}", projectID, language);
		return new ResponseEntity<InterestRankResponseEnity>(interestRankResponseEnity, HttpStatus.OK);
	}

}
