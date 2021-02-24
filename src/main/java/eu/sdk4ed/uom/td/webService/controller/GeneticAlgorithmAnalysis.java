package eu.sdk4ed.uom.td.webService.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.json.JSONException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

@RestController
@RequestMapping(value = "/geneticAlgorithm")
public class GeneticAlgorithmAnalysis extends BaseController 
{
	@CrossOrigin(origins = "*")
	@GetMapping(value = "/execute")
	public void execute(@RequestParam(value = "projectName", required = true) String projectName,
			@RequestParam(value = "language", required = true) String language,
			@RequestParam(value = "versionsNum", required = true) int versionsNum,
			@RequestParam(value = "typeAnalysis", required = true) int typeAnalysis,
			@RequestParam(value = "gitUrl", required = true) String gitUrl,
			@RequestParam(value = "gitUsername", required = true) String gitUsername,
			@RequestParam(value = "gitPassword", required = true) String gitPassword,
			@RequestParam(value = "shas", required = true) String shas) throws SQLException, IOException, InterruptedException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException, JSONException, SAXException, ParserConfigurationException {
		logger.info("> execute projectName: {}, language: {}, versionsNum: {}, typeAnalysis: {}, gitUrl: {}, gitUsername: {}, gitPassword: {}, shas: {}",
				projectName, language, versionsNum, typeAnalysis, gitUrl, gitUsername, gitPassword, shas);

	}
}
