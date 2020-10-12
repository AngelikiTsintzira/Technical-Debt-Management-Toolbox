package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.junit.jupiter.api.Test;

import eu.sdk4ed.uom.td.analysis.GitClone.GitCloneProject;

class testGitClone 
{

	@Test
	void testGitClonePublic() throws RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException, IOException 
	{
		GitCloneProject git = new GitCloneProject();
		String gitUrl = "https://github.com/AngelikiTsintzira/Technical-Debt-Management-Toolbox";
		
		ArrayList<String> sha = new ArrayList<String>();
		sha.add("");
		sha.add("");
		
		
		assertEquals(true, git.cloneCommits(System.getProperty("user.dir"), "", "", sha, gitUrl , "Project", 2));
	}
	
	@Test
	void testGitClonePrivate() throws RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException, IOException 
	{
		GitCloneProject git = new GitCloneProject();
		String gitUrl = "https://github.com/AngelikiTsintzira/Technical-Debt-Management-Toolbox";
		
		ArrayList<String> sha = new ArrayList<String>();
		sha.add("");
		sha.add("");
		
		Exception exception = assertThrows(
        		InvalidRefNameException.class,
			() -> git.cloneCommits(System.getProperty("user.dir"), "", "test", sha, gitUrl , "Project", 2));

        assertFalse(exception.getMessage().contains("Branch name is not allowed:"));
    
	}

}
