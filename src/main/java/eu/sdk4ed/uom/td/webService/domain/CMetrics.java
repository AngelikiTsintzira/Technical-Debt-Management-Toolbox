package eu.sdk4ed.uom.td.webService.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Entity(name = "CMetrics")
@Table(name = "cMetrics")
public class CMetrics implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@Column(name = "class_name")
	private String className;
	@Basic(optional = false)
	@Column(name = "project_name")
	private String projectName;
	@Basic(optional = false)
	@Column(name = "scope")
	private String scope;
	@Basic(optional = false)
	@Column(name = "loc")
	private double loc;
	@Basic(optional = false)
	@Column(name = "cyclomatic_complexity")
	private double cyclomaticComplexity;
	@Basic(optional = false)
	@Column(name = "number_of_functions")
	private double numberOfFunctions;
	@Basic(optional = false)
	@Column(name = "comments_density")
	private double commentsDensity;
	@Basic(optional = false)
	@Column(name = "version")
	private int version;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "principal")
	private Double principal;
	@Column(name = "interest")
	private Double interest;
	@Column(name = "breaking_point")
	private Double breakingPoint;
	@Column(name = "frequency_of_change")
	private Double frequencyOfChange;
	@Column(name = "interest_probability")
	private Double interestProbability;
	@Column(name = "coupling")
	private Double coupling;
	@Column(name = "cohesion")
	private Double cohesion;

	public CMetrics() {
	}

	public CMetrics(Integer id) {
		this.id = id;
	}

	public CMetrics(Integer id, String className, String projectName, String scope, double loc, double cyclomaticComplexity, double numberOfFunctions, double commentsDensity, int version) {
		this.id = id;
		this.className = className;
		this.projectName = projectName;
		this.scope = scope;
		this.loc = loc;
		this.cyclomaticComplexity = cyclomaticComplexity;
		this.numberOfFunctions = numberOfFunctions;
		this.commentsDensity = commentsDensity;
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public double getLoc() {
		return loc;
	}

	public void setLoc(double loc) {
		this.loc = loc;
	}

	public double getCyclomaticComplexity() {
		return cyclomaticComplexity;
	}

	public void setCyclomaticComplexity(double cyclomaticComplexity) {
		this.cyclomaticComplexity = cyclomaticComplexity;
	}

	public double getNumberOfFunctions() {
		return numberOfFunctions;
	}

	public void setNumberOfFunctions(double numberOfFunctions) {
		this.numberOfFunctions = numberOfFunctions;
	}

	public double getCommentsDensity() {
		return commentsDensity;
	}

	public void setCommentsDensity(double commentsDensity) {
		this.commentsDensity = commentsDensity;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getBreakingPoint() {
		return breakingPoint;
	}

	public void setBreakingPoint(Double breakingPoint) {
		this.breakingPoint = breakingPoint;
	}

	public Double getFrequencyOfChange() {
		return frequencyOfChange;
	}

	public void setFrequencyOfChange(Double frequencyOfChange) {
		this.frequencyOfChange = frequencyOfChange;
	}

	public Double getInterestProbability() {
		return interestProbability;
	}

	public void setInterestProbability(Double interestProbability) {
		this.interestProbability = interestProbability;
	}

	public Double getCoupling() {
		return coupling;
	}

	public void setCoupling(Double coupling) {
		this.coupling = coupling;
	}

	public Double getCohesion() {
		return cohesion;
	}

	public void setCohesion(Double cohesion) {
		this.cohesion = cohesion;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof CMetrics)) {
			return false;
		}
		CMetrics other = (CMetrics) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "eu.sdk4ed.uom.td.domain.CMetrics[ id=" + id + " ]";
	}

}
