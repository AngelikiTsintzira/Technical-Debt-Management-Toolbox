package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.jupiter.api.Test;

import eu.sdk4ed.uom.td.analysis.artifact.ArtifactMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.ProjectArtifact;

class testArtifacts 
{
	ProjectArtifact pr = new  ProjectArtifact ();
	
	ConcurrentHashMap<String, ArtifactMetrics> artifacts = new ConcurrentHashMap<String, ArtifactMetrics>();

	@Test
	void testClassArtifacts() throws NumberFormatException, SQLException, IOException 
	{
		ArtifactMetrics artifactObject = new ArtifactMetrics("ProjectName", "artifactID", "Java", "CommitHash", 0, "FIL");

		artifactObject.setMetricsfromSonar(5.0, 100, 5, 200, 20, 127, 45, 0, 2, 
				100, 20);
		
		artifactObject.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
	
		assertEquals(5.0, artifactObject.getNumOfClasses());
		assertEquals(100, artifactObject.getComplexity());
		assertEquals(5, artifactObject.getFunctions());
		assertEquals(200, artifactObject.getNcloc());
		assertEquals(20, artifactObject.getStatements());
		assertEquals(127, artifactObject.getTechnicalDebt());
		assertEquals(0, artifactObject.getBugs());
		assertEquals(45, artifactObject.getCodeSmells());
		assertEquals(2, artifactObject.getVulnerabilities());
		assertEquals(100, artifactObject.getDuplications());
		assertEquals(20, artifactObject.getCommentsDensity());
		
		assertEquals(1.2, artifactObject.getMpc());
		assertEquals(4.3, artifactObject.getNom());
		assertEquals(1, artifactObject.getDit());
		assertEquals(0, artifactObject.getNocc());
		assertEquals(5.3, artifactObject.getRfc());
		assertEquals(400, artifactObject.getLcom());
		assertEquals(34, artifactObject.getWmpc());
		assertEquals(4.4, artifactObject.getDac());
		assertEquals(100, artifactObject.getLoc());
		assertEquals(12, artifactObject.getNop());

		/*
		assertEquals(2, artifactObject.getNumOfClasses());
		assertEquals(20, artifactObject.getComplexity());
		assertEquals(3, artifactObject.getNumOfFunctions());
		assertEquals(156, artifactObject.getNcloc());
		assertEquals(4, artifactObject.getStatements());
		assertEquals(200, artifactObject.getTD());
		assertEquals(3.4, artifactObject.getCommentsDensity());
		assertEquals(30, artifactObject.getCodeSmells());
		assertEquals(0, artifactObject.getBugs());
		assertEquals(0, artifactObject.getVulnerabilities());
		assertEquals(2.3, artifactObject.getDuplications());
		*/
	}

}
