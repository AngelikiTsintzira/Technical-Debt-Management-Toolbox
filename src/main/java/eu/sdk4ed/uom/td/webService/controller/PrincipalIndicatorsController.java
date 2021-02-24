/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalIndicatorsColumnLabelField;
import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalIndicatorsColumnsRows;
import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalIndicatorsResponseEnity;
import eu.sdk4ed.uom.td.webService.controller.response.entity.PrincipalMetricsIndicators;
import eu.sdk4ed.uom.td.webService.service.PrincipalMetricsService;

@RestController
@RequestMapping(value = "/principalIndicators")
public class PrincipalIndicatorsController extends BaseController {

	@Autowired
	private PrincipalMetricsService principalMetricsService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PrincipalIndicatorsResponseEnity> search(@RequestParam(value = "projectID", required = true) String projectID) {
		logger.info("> search projectID: {}", projectID);

		List<PrincipalIndicatorsColumnLabelField> columns = new ArrayList<PrincipalIndicatorsColumnLabelField>() {{
			add(new PrincipalIndicatorsColumnLabelField("Artifact", "name"));
			add(new PrincipalIndicatorsColumnLabelField("TD-minutes", "tdInMinutes"));
			add(new PrincipalIndicatorsColumnLabelField("TD-currency", "tdInCurrency"));
			add(new PrincipalIndicatorsColumnLabelField("Bugs", "bugs"));
			add(new PrincipalIndicatorsColumnLabelField("Vulnerabilities", "vulnerabilities"));
			add(new PrincipalIndicatorsColumnLabelField("Duplications", "duplCode"));
			add(new PrincipalIndicatorsColumnLabelField("Code Smells", "codeSmells"));
		}};

		List<PrincipalMetricsIndicators> rows = principalMetricsService.getPrincipalIndicatorsByProjectName(projectID);

		PrincipalIndicatorsResponseEnity principalIndicatorsResponseEnity = new PrincipalIndicatorsResponseEnity(new PrincipalIndicatorsColumnsRows(columns, rows));

		logger.info("< search projectID: {}", projectID);
		return new ResponseEntity<PrincipalIndicatorsResponseEnity>(principalIndicatorsResponseEnity, HttpStatus.OK);
	}

}
