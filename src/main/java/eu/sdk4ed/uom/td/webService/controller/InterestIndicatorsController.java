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

import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorAbstract;
import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorsColumnLabelField;
import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorsColumnsRows;
import eu.sdk4ed.uom.td.webService.controller.response.entity.InterestIndicatorsResponseEnity;
import eu.sdk4ed.uom.td.webService.service.CMetricsService;
import eu.sdk4ed.uom.td.webService.service.JavaMetricsService;

@RestController
@RequestMapping(value = "/interestIndicators")
public class InterestIndicatorsController extends BaseController {

	@Autowired
	private CMetricsService cMetricsService;

	@Autowired
	private JavaMetricsService javaMetricsService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<InterestIndicatorsResponseEnity> search(@RequestParam(value = "projectID", required = true) String projectID, @RequestParam(value = "language", required = true) String language) {
		logger.info("> search projectID: {}, language: {}", projectID, language);

		InterestIndicatorsResponseEnity interestIndicatorsResponseEnity = null;
		List<InterestIndicatorAbstract> rows;

		switch (language) {
		case "c":
			List<InterestIndicatorsColumnLabelField> columnsC = new ArrayList<InterestIndicatorsColumnLabelField>() {{
				add(new InterestIndicatorsColumnLabelField("Artifact", "name"));
				add(new InterestIndicatorsColumnLabelField("Interest", "Interest"));
				add(new InterestIndicatorsColumnLabelField("Interest Probability", "IP"));
				add(new InterestIndicatorsColumnLabelField("Lines of Code", "LOC"));
				add(new InterestIndicatorsColumnLabelField("Cyclomatic Complexity", "CC"));
				add(new InterestIndicatorsColumnLabelField("Number of Functions", "NoF"));
				add(new InterestIndicatorsColumnLabelField("Comments Density", "CD"));
				add(new InterestIndicatorsColumnLabelField("Fan-Out", "Coupling"));
				add(new InterestIndicatorsColumnLabelField("Lack of Cohesion between Lines", "Cohesion"));
			}};

			rows = new ArrayList<>();
			rows.addAll(cMetricsService.getInterestIndicatorByProjectName(projectID));

			interestIndicatorsResponseEnity = new InterestIndicatorsResponseEnity(new InterestIndicatorsColumnsRows(columnsC, rows));
			break;
		case "java":
			List<InterestIndicatorsColumnLabelField> columnsJava = new ArrayList<InterestIndicatorsColumnLabelField>() {{
				add(new InterestIndicatorsColumnLabelField("Artifact", "name"));
				add(new InterestIndicatorsColumnLabelField("Interest", "Interest"));
				add(new InterestIndicatorsColumnLabelField("Interest Probability", "IP"));
				add(new InterestIndicatorsColumnLabelField("MPC", "MPC"));
				add(new InterestIndicatorsColumnLabelField("DIT", "DIT"));
				add(new InterestIndicatorsColumnLabelField("NOCC", "NOCC"));
				add(new InterestIndicatorsColumnLabelField("RFC", "RFC"));
				add(new InterestIndicatorsColumnLabelField("LCOM", "LCOM"));
				add(new InterestIndicatorsColumnLabelField("WMC", "WMC"));
				add(new InterestIndicatorsColumnLabelField("DAC", "DAC"));
				add(new InterestIndicatorsColumnLabelField("NOM", "NOM"));
				add(new InterestIndicatorsColumnLabelField("LOC", "LOC"));
				add(new InterestIndicatorsColumnLabelField("NOP", "NOP"));
				//add(new InterestIndicatorsColumnLabelField("REM", "REM"));
				//add(new InterestIndicatorsColumnLabelField("CPM", "CPM"));
			}};

			rows = new ArrayList<>();
			rows.addAll(javaMetricsService.getInterestIndicatorByProjectName(projectID));

			interestIndicatorsResponseEnity = new InterestIndicatorsResponseEnity(new InterestIndicatorsColumnsRows(columnsJava, rows));
			break;
		default:
			break;
		}

		logger.info("< search projectID: {}, language: {}", projectID, language);
		return new ResponseEntity<InterestIndicatorsResponseEnity>(interestIndicatorsResponseEnity, HttpStatus.OK);
	}

}
