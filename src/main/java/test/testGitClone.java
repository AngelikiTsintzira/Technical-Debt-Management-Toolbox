package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.junit.jupiter.api.Test;

import eu.sdk4ed.uom.td.analysis.GitClone.GitCloneProject;
import eu.sdk4ed.uom.td.analysis.api.CommitShas;

class testGitClone 
{
	@Test
	void testGitClonePublic() throws RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException, IOException, URISyntaxException 
	{
		GitCloneProject git = new GitCloneProject();
		String gitUrl = "https://github.com/AngelikiTsintzira/Technical-Debt-Management-Toolbox.git";
		
		ArrayList<String> sha = new ArrayList<String>();
		
		CommitShas commitShas = new CommitShas();
		sha = commitShas.getShas(gitUrl, "", "");
		
		assertEquals(true, git.cloneCommits(System.getProperty("user.dir"), "", "", sha, gitUrl , "Project", sha.size()));
	}
	
	@Test
	void testGitClonePrivate() throws RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException, IOException, URISyntaxException 
	{
		GitCloneProject git = new GitCloneProject();
		String gitUrl = "";
		
		ArrayList<String> sha = new ArrayList<String>();
		
		// Dont forget to set username and password
		CommitShas commitShas = new CommitShas();
		sha = commitShas.getShas(gitUrl, "", "");

		
		assertEquals(true, git.cloneCommits(System.getProperty("user.dir"), "", "", sha, gitUrl , "Project", sha.size()));
	}
}
