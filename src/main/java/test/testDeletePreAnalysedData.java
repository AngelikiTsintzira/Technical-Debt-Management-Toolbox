package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import eu.sdk4ed.uom.td.analysis.database.DatabaseSaveData;
import eu.sdk4ed.uom.td.analysis.preperation.ConfigurationFile;
import eu.sdk4ed.uom.td.analysis.preperation.deletePreAnalysedData;

class testDeletePreAnalysedData 
{

	deletePreAnalysedData del = new deletePreAnalysedData();
	ConfigurationFile  f = new ConfigurationFile ();
	
	@Test
	void testDeleteDBPreAnalysedData() throws IOException, NumberFormatException, SQLException 
	{
		f.readConfigurationFile();
		
		DatabaseSaveData saveDB = new DatabaseSaveData();
		/*
		saveDB.saveMetricsInDatabase("Project", 0, "class1", "FIL", 32, 34, 3.2, 4.4, 100, 
				20, 3.2, 4.3, 4.3, 42.1, 432, 4, 0, 0, 0, 0, 0, 
				0, 0, 0, 0, 0, 0, 0, 0, 0, 
				0, 0, 0, "bdhjgdg");
		
		saveDB.saveMetricsInDatabase("Project", 1, "class1", "FIL", 32, 34, 3.2, 4.4, 100, 
				20, 3.2, 4.3, 4.3, 42.1, 432, 4, 0, 0, 0, 0, 0, 
				0, 0, 0, 0, 0, 0, 0, 0, 0, 
				0, 0, 0, "hdjadja");
		
		saveDB.saveMetricsInDatabase("Project", 2, "class1", "FIL", 32, 34, 3.2, 4.4, 100, 
				20, 3.2, 4.3, 4.3, 42.1, 432, 4, 0, 0, 0, 0, 0, 
				0, 0, 0, 0, 0, 0, 0, 0, 0, 
				0, 0, 0, "dhahdjas");
				*/
		
		saveDB.savePrincipalMetrics("class1", "Project", 0, 23, 42,
				34, 0, 0, 3.4, "FIL", 32, 
				432, 432, 32, 3, 2,"Java");
		
		saveDB.savePrincipalMetrics("class1", "Project", 1, 23, 42,
				34, 0, 0, 3.4, "FIL", 32, 
				432, 432, 32, 3, 2,"Java");
		
		saveDB.savePrincipalMetrics("class1", "Project", 2, 23, 42,
				34, 0, 0, 3.4, "FIL", 32, 
				432, 432, 32, 3, 2,"Java");
		
		saveDB.updatePrincipal("class1", 0, 42, "Project");
		

		saveDB.saveBreakingPointInDatabase("class1", 0, 2.3, 34, 12, 2.2, 0.2, "Project");
		
		saveDB.saveBreakingPointInDatabase("class1", 1, 2.3, 34, 12, 2.2, 0.2, "Project");
		
		saveDB.saveBreakingPointInDatabase("class1", 2, 2.3, 34, 12, 2.2, 0.2, "Project");
		
		assertEquals(true, del.DeleteFromDBAlreadyAnalysedVersion("Project", "Java", 2));
		
		assertEquals(true, saveDB.saveTimestamp("Project", 0));
		
		assertEquals(true, saveDB.deleteTimestamp("Project", 0));
		
		assertEquals(true, f.clodeConnection());
		
		saveDB.deleteInstancesPrincipal("Project", "class1");
		
		saveDB.deleteInstancesInterest("Project", "class1");
		
	}
	
	@Test
	void testDeleteDBPreAnalysedDataAll() throws IOException
	{
		f.readConfigurationFile();
		
		assertEquals(true, del.DeleteFromDBAlreadyAnalysedProject("Project", "Java"));
		
		del.delete("test.txt");
		del.deleteFile(0);
		
	}
}
