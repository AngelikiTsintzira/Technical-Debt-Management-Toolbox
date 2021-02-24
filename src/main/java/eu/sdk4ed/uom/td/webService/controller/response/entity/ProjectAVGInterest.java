/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class ProjectAVGInterest {

	private String projectName;
	private Double avgInterest;

	public ProjectAVGInterest() { }

	public ProjectAVGInterest(Double avgInterest, String projectName) {
		this.avgInterest = avgInterest;
		this.projectName = projectName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Double getAvgInterest() {
		return avgInterest;
	}

	public void setAvgInterest(Double avgInterest) {
		this.avgInterest = avgInterest;
	}

	@Override
	public String toString() {
		return "ProjectAVGInterest [projectName=" + projectName + ", avgInterest=" + avgInterest + "]";
	}

}
