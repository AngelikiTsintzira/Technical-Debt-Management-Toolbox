package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import eu.sdk4ed.uom.td.analysis.artifact.ClassMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.FileMetricsC;
import eu.sdk4ed.uom.td.analysis.artifact.PackageMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.PackageMetricsC;
import eu.sdk4ed.uom.td.analysis.artifact.ProjectArtifact;

class testArtifacts {

	ClassMetrics cl = new ClassMetrics("ProjectName", "ClassName");
	ClassMetrics cl2 = new ClassMetrics("ProjectName2", "ClassName2");
	FileMetricsC  fl = new FileMetricsC ("ProjectName1", "FileName");
	PackageMetrics pa = new PackageMetrics("ProjectName", "Package1");
	PackageMetricsC paC = new PackageMetricsC("ProjectNameC", "Package1C");
	ProjectArtifact pr = new  ProjectArtifact ();


	@Test
	void testClassArtifacts() throws NumberFormatException, SQLException, IOException 
	{
		// Class Metrics
		cl.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		cl.print();
		
		cl2.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl2.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);


		// File Metrics
		fl.metricsfromSonar(2, 20, 3, 156, 4, 200, 3.4, 30, 0, 0, 2.3);
		fl.setCohesion(34);
		fl.setCoupling(32);

		assertEquals(5.0, cl.getNumOfClasses());
		assertEquals(4.0, cl.getComplexity());
		assertEquals(5, cl.getFunctions());
		assertEquals(200, cl.getNcloc());
		assertEquals(20, cl.getStatements());
		assertEquals(127, cl.getTD());
		assertEquals(0, cl.getBugs());
		assertEquals(45, cl.getCodeSmells());
		assertEquals(2, cl.getVulnerabilities());
		assertEquals(1.8, cl.getDuplications());	
		
		assertEquals(1.2, cl.getMpc());
		assertEquals(4.3, cl.getNom());
		assertEquals(1, cl.getDit());
		assertEquals(0, cl.getNocc());
		assertEquals(5.3, cl.getRfc());
		assertEquals(400, cl.getLcom());
		assertEquals(34, cl.getWmpc());
		assertEquals(4.4, cl.getDac());
		assertEquals(100, cl.getSize1());
		assertEquals(12, cl.getSize2());

		assertEquals(2, fl.getNumOfClasses());
		assertEquals(20, fl.getComplexity());
		assertEquals(3, fl.getNumOfFunctions());
		assertEquals(156, fl.getNcloc());
		assertEquals(4, fl.getStatements());
		assertEquals(200, fl.getTD());
		assertEquals(3.4, fl.getCommentsDensity());
		assertEquals(30, fl.getCodeSmells());
		assertEquals(0, fl.getBugs());
		assertEquals(0, fl.getVulnerabilities());
		assertEquals(2.3, fl.getDuplications());
	}

	@Test
	void testPackageArtifacts()
	{
		//Package Metrics
		pa.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		pa.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		assertEquals(5, pa.getNumOfClasses());
		assertEquals(4, pa.getComplexity());
		assertEquals(5, pa.getFunctions());
		assertEquals(200, pa.getNcloc());
		assertEquals(20, pa.getStatements());
		assertEquals(127, pa.getTD());
		assertEquals(0, pa.getBugs());
		assertEquals(45, pa.getCodeSmells());
		assertEquals(2, pa.getVulnerabilities());
		assertEquals(1.8, pa.getDuplications());
		
		assertEquals(1.2, pa.getMpc());
		assertEquals(4.3, pa.getNom());
		assertEquals(1, pa.getDit());
		assertEquals(0, pa.getNocc());
		assertEquals(5.3, pa.getRfc());
		assertEquals(400, pa.getLcom());
		assertEquals(34, pa.getWmpc());
		assertEquals(4.4, pa.getDac());
		assertEquals(100, pa.getSize1());
		assertEquals(12, pa.getSize2());
		
		pa.setClassInPackage(cl);
		pa.setClassInPackage(cl2);
		
		assertEquals(2, pa.getClassInProject().size());
		
		//C Package Metrics
		paC.metricsfromSonar(5, 4.5, 5, 200, 20, 127, 45, 0, 2, 1.8, 0) ;
		
		assertEquals(5, paC.getNumOfClasses());
		assertEquals(4.5, paC.getComplexity());
		assertEquals(5, paC.getNumOfFunctions());
		assertEquals(200, paC.getNcloc());
		assertEquals(20, paC.getStatements());
		assertEquals(127, paC.getTD());
		assertEquals(45, paC.getCommentsDensity());
		assertEquals(0, paC.getCodeSmells());
		assertEquals(2, paC.getBugs());
		assertEquals(1.8, paC.getVulnerabilities());
		assertEquals(0, paC.getDuplications());	
		
		pr.setprojectName("Test1");
		pr.setNumOfVersions(10);
		
		assertEquals("Test1", pr.getProjectName());
		assertEquals(10, pr.getNumOfVersions());

	}


}
