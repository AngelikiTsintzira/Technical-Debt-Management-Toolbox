/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller;

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

import eu.sdk4ed.uom.td.webService.controller.response.entity.LineChartBreakingPointTDDivide;
import eu.sdk4ed.uom.td.webService.controller.response.entity.LineChartBreakingPointTDResponseEnity;
import eu.sdk4ed.uom.td.webService.service.CMetricsService;
import eu.sdk4ed.uom.td.webService.service.JavaMetricsService;

@RestController
@RequestMapping(value = "/lineChartBreakingPointTD")
public class LineChartBreakingPointTDController extends BaseController {

	@Autowired
	private CMetricsService cMetricsService;

	@Autowired
	private JavaMetricsService javaMetricsService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LineChartBreakingPointTDResponseEnity> search(@RequestParam(value = "projectID", required = true) String projectID, @RequestParam(value = "language", required = true) String language) {
		logger.info("> search projectID: {}, language: {}", projectID, language);

		LineChartBreakingPointTDResponseEnity lineChartBreakingPointTDResponseEnity = null;

		switch (language) {
		case "c":
			lineChartBreakingPointTDResponseEnity = new LineChartBreakingPointTDResponseEnity(cMetricsService.getLineChartBreakingPointTD(projectID).stream().map(LineChartBreakingPointTDDivide::getDividedValue).collect(Collectors.toList()));

			break;
		case "java":
			lineChartBreakingPointTDResponseEnity = new LineChartBreakingPointTDResponseEnity(javaMetricsService.getLineChartBreakingPointTD(projectID).stream().map(LineChartBreakingPointTDDivide::getDividedValue).collect(Collectors.toList()));

			break;
		default:
			break;
		}

		logger.info("< search projectID: {}, language: {}", projectID, language);
		return new ResponseEntity<LineChartBreakingPointTDResponseEnity>(lineChartBreakingPointTDResponseEnity, HttpStatus.OK);
	}

}
