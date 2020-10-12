package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import eu.sdk4ed.uom.td.analysis.api.SonarQubeArtifacts;
import eu.sdk4ed.uom.td.analysis.api.SonarQubeMetrics;

class testSonarQube 
{
	SonarQubeArtifacts sonar = new SonarQubeArtifacts("https://next.sonarqube.com/sonarqube");
	
	SonarQubeMetrics metrics = new SonarQubeMetrics("https://next.sonarqube.com/sonarqube");
	
	ArrayList<String>  classes;
	ArrayList<String>  packages;
	
	@BeforeEach
	void testSonarQubeCalls() throws JSONException, SAXException, ParserConfigurationException 
	{
		sonar.getArtifactsName("org.sonarsource.java%3Ajava", "FIL");
		sonar.getArtifactsName("org.sonarsource.java%3Ajava", "DIR");
		
		classes = sonar.getClassesId();
		packages = sonar.getPackagesId();
		
		//metrics.getMetricCommentsDensityFromApi(classes);
		metrics.getMetricsFromApiSonarClassLevel(classes, "Java");
		metrics.getMetricsFromSonarPackageLevel(packages, "Java");
		metrics.getTDFromApiSonar(classes);
		
	}
	
    @Test
    void testExceptionSonarQubeArtifacts() 
    {
    	
    	SonarQubeArtifacts sonar = new SonarQubeArtifacts(" ");
    	
        Exception exception = assertThrows(
        		RuntimeException.class,
			() -> sonar.getArtifactsName("org.sonarsource.java%3Ajava", "FIL"));

        assertFalse(exception.getMessage().contains("Failed : HTTP error code :"));
    }
    
	/*
	 * @Test void testExceptionSgetMetricCommentsDensityFromApi() { SonarQubeMetrics
	 * metrics = new SonarQubeMetrics("123://next.sonarqube.com/sonarqube");
	 * 
	 * Exception exception = assertThrows( RuntimeException.class, () ->
	 * metrics.getMetricCommentsDensityFromApi(classes));
	 * 
	 * assertFalse(exception.getMessage().contains("Failed : HTTP error code :"));
	 * 
	 * }
	 */
    @Test
    void testExceptiongetMetricsFromApiSonarClassLevel() 
    {
    	SonarQubeMetrics metrics = new SonarQubeMetrics("123://next.sonarqube.com/sonarqube");
    	
    	Exception exception1 = assertThrows(
        		RuntimeException.class,
			() -> metrics.getMetricsFromApiSonarClassLevel(classes, "Java"));

        assertFalse(exception1.getMessage().contains("Failed : HTTP error code :"));
    }
    
    @Test
    void testExceptionMetricsFromSonarPackageLevel()
    {
   	
    	SonarQubeMetrics metrics = new SonarQubeMetrics("123://next.sonarqube.com/sonarqube");
    	
        Exception exception2 = assertThrows(
        		RuntimeException.class,
			() -> metrics.getMetricsFromSonarPackageLevel(packages, " "));

        assertFalse(exception2.getMessage().contains("Failed : HTTP error code :"));
    }
    
    @Test
    void testExceptiongetTDFromApiSonar()
    {
    	SonarQubeMetrics metrics = new SonarQubeMetrics("123://next.sonarqube.com/sonarqube");
    	
        Exception exception3 = assertThrows(
        		RuntimeException.class,
			() -> metrics.getTDFromApiSonar(classes));

        assertFalse(exception3.getMessage().contains("Failed : HTTP error code :"));
		
    }  
}
