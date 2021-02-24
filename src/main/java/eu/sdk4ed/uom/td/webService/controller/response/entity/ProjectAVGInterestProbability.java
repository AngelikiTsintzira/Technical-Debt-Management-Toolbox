/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class ProjectAVGInterestProbability {

	private String projectName;
	private Double avgInterestProbability;

	public ProjectAVGInterestProbability() { }

	public ProjectAVGInterestProbability(Double avgInterestProbability, String projectName) {
		this.avgInterestProbability = avgInterestProbability;
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getAvgInterestProbability() {
		return avgInterestProbability;
	}

	public void setAvgInterestProbability(Double avgInterestProbability) {
		this.avgInterestProbability = avgInterestProbability;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((avgInterestProbability == null) ? 0 : avgInterestProbability.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
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
		ProjectAVGInterestProbability other = (ProjectAVGInterestProbability) obj;
		if (avgInterestProbability == null) {
			if (other.avgInterestProbability != null)
				return false;
		} else if (!avgInterestProbability.equals(other.avgInterestProbability))
			return false;
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProjectAVGInterestProbability [projectName=" + projectName + ", avgInterestProbability=" + avgInterestProbability + "]";
	}

}
