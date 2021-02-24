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

import eu.sdk4ed.uom.td.webService.controller.response.entity.moveClassRefactoringsMetrics;
import eu.sdk4ed.uom.td.webService.controller.response.entity.moveClassRefactoringsMetricsResponseEntity;
import eu.sdk4ed.uom.td.webService.service.GeaClassesService;

@RestController
@RequestMapping(value = "/moveClassRefactoringMetrics")
public class moveClassRefactoringsMetricsController extends BaseController {

	@Autowired
	private GeaClassesService metricsService;

	@CrossOrigin(origins = "*")
	@GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<moveClassRefactoringsMetricsResponseEntity> search(@RequestParam(value = "projectID", required = true) String projectID) {
		logger.info("> search projectID: {} ", projectID);

		moveClassRefactoringsMetrics mcrmProject = metricsService.getMoveClassRefactoringsMetrics(projectID);
		moveClassRefactoringsMetricsResponseEntity metrics = new moveClassRefactoringsMetricsResponseEntity(Objects.isNull(mcrmProject) ? new moveClassRefactoringsMetrics() : mcrmProject);

		logger.info("< search projectID: {} ", projectID);
		return new ResponseEntity<moveClassRefactoringsMetricsResponseEntity>(metrics, HttpStatus.OK);	}

}
