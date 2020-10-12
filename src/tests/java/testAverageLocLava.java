package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import eu.sdk4ed.uom.td.analysis.artifact.ClassMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.FileMetricsC;
import eu.sdk4ed.uom.td.analysis.artifact.PackageMetrics;
import eu.sdk4ed.uom.td.analysis.artifact.PackageMetricsC;
import eu.sdk4ed.uom.td.analysis.artifact.ProjectArtifact;
import eu.sdk4ed.uom.td.analysis.calculations.AverageLocCalculation;
import eu.sdk4ed.uom.td.analysis.calculations.AverageLocCalculationC;
import eu.sdk4ed.uom.td.analysis.calculations.FindSimilarArtifacts;
import eu.sdk4ed.uom.td.analysis.calculations.FindSimilarArtifactsC;
import eu.sdk4ed.uom.td.analysis.calculations.OptimalArtifact;
import eu.sdk4ed.uom.td.analysis.calculations.OptimalArtifactC;
import eu.sdk4ed.uom.td.analysis.database.DatabaseSaveData;
import eu.sdk4ed.uom.td.analysis.database.DatabaseSaveDataC;
import eu.sdk4ed.uom.td.analysis.preperation.ConfigurationFile;
import eu.sdk4ed.uom.td.analysis.versions.Versions;

class testAverageLocLava 
{
	AverageLocCalculation  ave = new AverageLocCalculation ();
	AverageLocCalculationC  aveC = new AverageLocCalculationC ();
	ConfigurationFile  f = new ConfigurationFile ();
	DatabaseSaveData saveDB = new DatabaseSaveData();
	DatabaseSaveDataC saveC = new DatabaseSaveDataC();
	
	@Test
	void testChangeProneness() throws NumberFormatException, IOException, SQLException 
	{
		try {
		      FileWriter myWriter = new FileWriter("rem_and_cpm_metrics_classLevel.csv");
		      myWriter.write("Class1.class");
		      myWriter.write(",");
		      myWriter.write("3");
		      myWriter.write(",");
		      myWriter.write("8");
		      myWriter.write(",");
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }

		assertEquals(1, ave.ChangeProneness("rem_and_cpm_metrics_classLevel.csv").size());
		
		File myObj = new File("rem_and_cpm_metrics_classLevel.csv"); 
		
	    if (myObj.delete()) 
	    { 
	      System.out.println("Deleted the file: " + myObj.getName());
	    } else 
	    {
	      System.out.println("Failed to delete the file.");
	    } 
		
	}
	
	@Test
	void testSetMetricsToClass() throws NumberFormatException, IOException, SQLException
	{
		f.readConfigurationFile();
		
		try {
		      FileWriter myWriter = new FileWriter("output0.csv");
		      myWriter.write("Class1.class");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.write("3");
		      myWriter.write(";");
		      myWriter.write("8");
		      myWriter.write(";");
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }

		 ave.setMetricsToClassLevel("ProjectForJava", 0);
		 
		 assertEquals(1, ave.getObjectsClassMetrics().size());
			
			File myObj = new File("output0.csv"); 
			
		    if (myObj.delete()) 
		    { 
		      System.out.println("Deleted the file: " + myObj.getName());
		    } else 
		    {
		      System.out.println("Failed to delete the file.");
		    } 
		    
		    saveDB.deleteInstancesPrincipal("ProjectForJava", "class1.class");
			
			saveDB.deleteInstancesInterest("ProjectForJava", "class1.class");	
	}
	
	@Test
	void testLocJava() 
	{
		ProjectArtifact p = new ProjectArtifact();
		
		Versions v = new Versions();
		v.setProjectName("Testing");
		v.setVersionId(0);	

		ClassMetrics cl1 = new ClassMetrics("Testing", "Package1/ClassName1");
		ClassMetrics cl2 = new ClassMetrics("Testing", "Package1/ClassName2");
		
		ClassMetrics cl3 = new ClassMetrics("Testing", "Package2/ClassName3");
		ClassMetrics cl4 = new ClassMetrics("Testing", "Package2/ClassName4");
		
		PackageMetrics pa1 = new PackageMetrics("Testing", "Package1");
		PackageMetrics pa2 = new PackageMetrics("Testing", "Package2");
		
		cl1.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl1.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		cl2.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl2.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		cl3.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl3.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		cl4.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl4.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		pa1.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		pa1.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		pa2.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		pa2.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		pa1.setClassInPackage(cl1);
		pa1.setClassInPackage(cl2);
		
		pa2.setClassInPackage(cl3);
		pa2.setClassInPackage(cl4);
		
		ArrayList<PackageMetrics> pacK = new ArrayList<PackageMetrics>();
		pacK.add(pa1);
		pacK.add(pa2);
		
		v.setPackageInProject(pacK);
		p.setVersion(v);
		
		assertEquals(4, ave.findAllClasses(p).size());
		
		assertEquals(2, ave.findAllPackages(p).size());
		
		ave.calculateLocClassLevel(p);
		ave.calculateLocPackageLevel(p);	
	}
	
	@Test
	void testLocC() 
	{
		ProjectArtifact p = new ProjectArtifact();
		
		Versions v = new Versions();
		v.setProjectName("Testing");
		v.setVersionId(0);	

		FileMetricsC cl1 = new FileMetricsC("Testing", "Package1/ClassName1");
		FileMetricsC cl2 = new FileMetricsC("Testing", "Package1/ClassName2");
		
		FileMetricsC cl3 = new FileMetricsC("Testing", "Package2/ClassName3");
		FileMetricsC cl4 = new FileMetricsC("Testing", "Package2/ClassName4");
		
		PackageMetricsC pa1 = new PackageMetricsC("Testing", "Package1");
		PackageMetricsC pa2 = new PackageMetricsC("Testing", "Package2");
		
		cl1.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;
		cl2.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;		
		cl3.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;
		cl4.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;

		pa1.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;		
		pa2.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;

		pa1.setClassInPackage(cl1);
		pa1.setClassInPackage(cl2);
		
		pa2.setClassInPackage(cl3);
		pa2.setClassInPackage(cl4);
		
		ArrayList<PackageMetricsC> pacK = new ArrayList<PackageMetricsC>();
		pacK.add(pa1);
		pacK.add(pa2);
		
		v.setPackageInProjectC(pacK);
		p.setVersion(v);
		
		assertEquals(4, aveC.findAllClasses(p).size());
		
		assertEquals(2, aveC.findAllPackages(p).size());
		
		aveC.calculateLocClassLevel(p);
		aveC.calculateLocPackageLevel(p);
			
	}
	
	@Test
	void testSimilarArtifactsJava() throws SQLException
	{
		ProjectArtifact p = new ProjectArtifact();
		
		Versions v = new Versions();
		v.setProjectName("Testing");
		v.setVersionId(0);	

		ClassMetrics cl1 = new ClassMetrics("Testing", "Package1/ClassName1");
		ClassMetrics cl2 = new ClassMetrics("Testing", "Package1/ClassName2");
		
		ClassMetrics cl3 = new ClassMetrics("Testing", "Package2/ClassName3");
		ClassMetrics cl4 = new ClassMetrics("Testing", "Package2/ClassName4");
		
		ClassMetrics cl5 = new ClassMetrics("Testing", "Package2/ClassName5");
		ClassMetrics cl6 = new ClassMetrics("Testing", "Package2/ClassName6");
		
		PackageMetrics pa1 = new PackageMetrics("Testing", "Package1");
		PackageMetrics pa2 = new PackageMetrics("Testing", "Package2");
		
		OptimalArtifact op = new OptimalArtifact ();
		
		cl1.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl1.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		cl2.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl2.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		cl3.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl3.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		cl4.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl4.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		cl5.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl5.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		cl6.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		cl6.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		pa1.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		pa1.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		pa2.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8) ;
		pa2.metricsfromMetricsCalculator(1.2, 4.3, 1, 0, 5.3, 400, 34, 4.4, 100, 12);
		
		pa1.setClassInPackage(cl1);
		pa1.setClassInPackage(cl2);
		
		pa1.setClassInPackage(cl5);
		pa1.setClassInPackage(cl6);
		
		pa1.setClassInPackage(cl3);
		pa1.setClassInPackage(cl4);
		
		ArrayList<PackageMetrics> pacK = new ArrayList<PackageMetrics>();
		pacK.add(pa1);
		pacK.add(pa2);
		
		v.setPackageInProject(pacK);
		p.setVersion(v);
		
		ave.calculateLocClassLevel(p);
		ave.calculateLocPackageLevel(p);
		
		FindSimilarArtifacts sim = new FindSimilarArtifacts();
		
		ArrayList<ClassMetrics> c = sim.findAllClasses(p, 0, 1);
		
		ArrayList<PackageMetrics> Pa = sim.findAllPackages(p, 0, 1);
		
		assertEquals(6, c.size());
		
		assertEquals(2, Pa.size());
		
		ArrayList<FindSimilarArtifacts> simClasses = sim.calculateSimilarityForClasses(p, 0, 1);
		
		ArrayList<FindSimilarArtifacts> simPackages = sim.calculateSimilarityForPackages(p, 0, 1);
		
		assertEquals(6, simClasses.size());
		
		assertEquals(2, simPackages .size());
		
		op.calculateOptimalClass(simClasses,0);
		
		op.calculateOptimalPackage(simPackages,0);
		
	}
	
	@Test
	void testSimilarArtifactsC() throws SQLException
	{
		ProjectArtifact p = new ProjectArtifact();
		
		Versions v = new Versions();
		v.setProjectName("Testing");
		v.setVersionId(0);	

		FileMetricsC cl1 = new FileMetricsC("Testing", "Package1/ClassName1");
		FileMetricsC cl2 = new FileMetricsC("Testing", "Package1/ClassName2");
		
		FileMetricsC cl3 = new FileMetricsC("Testing", "Package2/ClassName3");
		FileMetricsC cl4 = new FileMetricsC("Testing", "Package2/ClassName4");
		
		FileMetricsC cl5 = new FileMetricsC("Testing", "Package2/ClassName5");
		FileMetricsC cl6 = new FileMetricsC("Testing", "Package2/ClassName6");
		
		PackageMetricsC pa1 = new PackageMetricsC("Testing", "Package1");
		PackageMetricsC pa2 = new PackageMetricsC("Testing", "Package2");
		
		OptimalArtifactC op = new OptimalArtifactC ();
		
		cl1.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;
		cl2.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;		
		cl3.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;
		cl4.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;
		cl5.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;
		cl6.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;

		pa1.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;		
		pa2.metricsfromSonar(5.0, 4.0, 5, 200, 20, 127, 45, 0, 2, 1.8, 3) ;
		
		
		pa1.setClassInPackage(cl1);
		pa1.setClassInPackage(cl2);
		
		pa1.setClassInPackage(cl5);
		pa1.setClassInPackage(cl6);
		
		pa1.setClassInPackage(cl3);
		pa1.setClassInPackage(cl4);
		
		ArrayList<PackageMetricsC> pacK = new ArrayList<PackageMetricsC>();
		pacK.add(pa1);
		pacK.add(pa2);
		
		v.setPackageInProjectC(pacK);
		p.setVersion(v);
		
		aveC.calculateLocClassLevel(p);
		aveC.calculateLocPackageLevel(p);
		
		FindSimilarArtifactsC sim = new FindSimilarArtifactsC();
		
		ArrayList<FileMetricsC> c = sim.findAllClasses(p, 0, 1);
		
		ArrayList<PackageMetricsC> Pa = sim.findAllPackages(p, 0, 1);
		
		assertEquals(6, c.size());
		
		assertEquals(2, Pa.size());
		
		ArrayList<FindSimilarArtifactsC> simClasses = sim.calculateSimilarityForClasses(p, 0, 1);
		
		ArrayList<FindSimilarArtifactsC> simPackages = sim.calculateSimilarityForPackages(p, 0, 1);
		
		assertEquals(6, simClasses.size());
		
		assertEquals(2, simPackages .size());
		
		op.calculateOptimalClass(simClasses,0);
		
		op.calculateOptimalPackage(simPackages,0);
		
	}



}
