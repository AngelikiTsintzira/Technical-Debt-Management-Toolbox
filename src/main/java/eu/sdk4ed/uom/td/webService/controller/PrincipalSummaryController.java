package eu.sdk4ed.uom.td.webService.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalSummary;
import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalSummaryResponseEnity;
import eu.sdk4ed.uom.td.webService.service.PrincipalMetricsService;

@RestController
@RequestMapping(value = "/principalSummary")
public class PrincipalSummaryController extends BaseController {

	@Autowired
	private PrincipalMetricsService principalMetricsService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PrincipalSummaryResponseEnity> search(@RequestParam(value = "projectID", required = true) String projectID) {
		logger.info("> search projectID: {}", projectID);

		PrincipalSummary principalMetrics = principalMetricsService.getPrincipalSummaryByProjectName(projectID);
		PrincipalSummaryResponseEnity principalSummaryResponseEnity = new PrincipalSummaryResponseEnity(Objects.nonNull(principalMetrics) ? principalMetrics : new PrincipalSummary());

		logger.info("< search projectID: {}", projectID);
		return new ResponseEntity<PrincipalSummaryResponseEnity>(principalSummaryResponseEnity, HttpStatus.OK);
	}

}
