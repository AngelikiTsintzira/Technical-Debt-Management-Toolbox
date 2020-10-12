package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import eu.sdk4ed.uom.td.analysis.database.DatabaseGetData;
import eu.sdk4ed.uom.td.analysis.database.DatabaseSaveData;
import eu.sdk4ed.uom.td.analysis.database.DatabaseSaveDataC;
import eu.sdk4ed.uom.td.analysis.database.GetAnalysisDataJava;
import eu.sdk4ed.uom.td.analysis.database.TablesCreation;
import eu.sdk4ed.uom.td.analysis.preperation.ConfigurationFile;

class testDatabaseActions {
	
	ConfigurationFile  f = new ConfigurationFile ();
	DatabaseSaveDataC dataC = new DatabaseSaveDataC();
	DatabaseGetData db = new DatabaseGetData();
	DatabaseSaveData saveDB = new DatabaseSaveData();
	GetAnalysisDataJava preData = new GetAnalysisDataJava();

	@Test
	void testTablesCreation() throws SQLException, IOException 
	{
		TablesCreation table = new TablesCreation();
		
		f.readConfigurationFile();

		assertEquals(true, table.createDatabaseTables());

	}
	
	@Test
	void testSaveDataC() throws IOException, SQLException
	{
		// Delete old instances
		dataC.deleteInstancesPrincipal("Project", "class1");
		
		dataC.deleteInstancesInterest("Project", "class1");
		
		// Test database
		
		dataC.saveMetricsInDatabase("Project", 0, "class1", "FIL", 244, 3.4, 432, 333, 32.2, 32);
		
		dataC.savePrincipalMetrics("class1", "Project", 0, 23, 42,
				34, 0, 0, 3.4, "FIL", 32, 
				432, 432, 32, 3, 2, "C");
		
		dataC.updatePrincipal("class1", 0, 42, "Project");
		
		dataC.saveBreakingPointInDatabase("class1", 0, 3.2, 4, 0.4, 2.3, 2.3);
				
		//preData.getAnalysisDataBPTC("Project", "FIL", 0); 
		preData.getAnalysisDataC("Project", "FIL", 0); 
		
	}
	
	@Test
	void testSaveDataJava() throws IOException, SQLException
	{
		// Delete old instances
		saveDB.deleteInstancesPrincipal("ProjectJava", "class1");
		
		saveDB.deleteInstancesInterest("ProjectJava", "class1");
		
		// Test database

		saveDB.saveMetricsInDatabase("ProjectJava", 0, "class1", "FIL", 32, 34, 3.2, 4.4, 100, 
				20, 3.2, 4.3, 4.3, 42.1, 432, 4, 0, 0, 0, 0, 0, 
				0, 0, 0, 0, 0, 0, 0, 0, 0, 
				0, 0, 0, 0, 0);

		
		saveDB.savePrincipalMetrics("class1", "ProjectJava", 0, 23, 42,
				34, 0, 0, 3.4, "FIL", 32, 
				432, 432, 32, 3, 2,"Java");
		
		
		saveDB.updatePrincipal("class1", 0, 42, "ProjectJava");
		
		saveDB.saveBreakingPointInDatabase("class1", 0, 3.3, 32, 43, 3, 44.4, "ProjectJava");
		
		preData.getAnalysisDataBPTJava("ProjectJava", "FIL", "0") ;
		preData.getAnalysisDataBPTJava("ProjectJava", "DIR", "0") ;
		preData.getAnalysisDataPrincipalSonar("ProjectJava", "FIL", 0); 
		preData.getAnalysisDataPrincipalSonar("ProjectJava", "DIR", 0); 
	
		
	}
	
	@Test
	void testGetPreAnalysedData()
	{
		
		
		
	}
	
	@Test
	void testGetData() throws SQLException
	{
		
		assertEquals(2, db.getCouplingCohesion("class1", 0).size());
		
		assertEquals(1, db.getKForArtifactC("class1").size());
		
		assertEquals(3, db.getKForArtifact("class1").size());
		
		assertEquals(1, db.getLoCForArtifactC("class1", 0).size());	
		
		assertEquals(2, db.getLoCForArtifact("class1", 0).size());	
				
		assertEquals(43.0, db.getInterestForArtifactJava("ProjectJava", 0).get(0));
		
		assertEquals(0.4, db.getInterestForArtifactC("Project", 0).get(0));
		
		dataC.deleteInstancesPrincipal("Project", "class1");
		
		dataC.deleteInstancesInterest("Project", "class1");
		
		saveDB.deleteInstancesPrincipal("ProjectJava", "class1");
		
		saveDB.deleteInstancesInterest("ProjectJava", "class1");	
		
		db.clearData();

	}

}
