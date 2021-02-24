package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.URISyntaxException;
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
	
	private ArrayList<String> classesIDs = new ArrayList<String>();
	private ArrayList<String> packagesIDs = new ArrayList<String>();
	
	@BeforeEach
	void testSonarQubeArtifacts() throws JSONException, SAXException, ParserConfigurationException, URISyntaxException 
	{
		sonar.getArtifactsName("org.sonarsource.java%3Ajava", "FIL");
		sonar.getArtifactsName("org.sonarsource.java%3Ajava", "DIR");
		
		classesIDs = sonar.getClassesId();
		packagesIDs = sonar.getPackagesId();
		
		assertEquals(3, classesIDs.size());
		assertEquals(0, packagesIDs.size());
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

    @Test
    void testSonarQubeMetrics() throws JSONException, URISyntaxException 
    {
    	SonarQubeMetrics metrics = new SonarQubeMetrics("123://next.sonarqube.com/sonarqube");

    	metrics.getPrincipalMetricsFromSonarqube(classesIDs, "FIL", "org.sonarsource.java%3Ajava", "Java", "",  0);

    }

}
