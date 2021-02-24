/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class Project {

	private String project_name;
	private String language;
	private int version;

	public Project() {}

	public Project(String project_name, String language) {
		this.project_name = project_name;
		this.language = language;
	}

	public String getproject_name() {
		return project_name;
	}

	public void setproject_name(String project_name) {
		this.project_name = project_name;
	}

	public String getLanguage() {
		return language;
	}
	
	public int getVersion()
	{
		return version;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public void setVersion(int version)
	{
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((project_name == null) ? 0 : project_name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (project_name == null) {
			if (other.project_name != null)
				return false;
		} else if (!project_name.equals(other.project_name))
			return false;
		return true;
	}

}
