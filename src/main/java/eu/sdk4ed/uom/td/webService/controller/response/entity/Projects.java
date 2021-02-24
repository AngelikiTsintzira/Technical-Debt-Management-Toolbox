package eu.sdk4ed.uom.td.webService.controller.response.entity;

import java.util.Set;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class Projects {

	private Set<Project> projects;

	public Projects() { }

	public Projects(Set<Project> projects) {
		this.projects = projects;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

}
