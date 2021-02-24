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
@Entity(name = "PrincipalMetrics")
@Table(name = "principalMetrics")
public class PrincipalMetrics implements Serializable {

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
	@Column(name = "version")
	private int version;
	@Basic(optional = false)
	@Column(name = "td_minutes")
	private float tdMinutes;
	@Basic(optional = false)
	@Column(name = "principal")
	private double principal;
	@Basic(optional = false)
	@Column(name = "code_smells")
	private int codeSmells;
	@Basic(optional = false)
	@Column(name = "bugs")
	private int bugs;
	@Basic(optional = false)
	@Column(name = "vulnerabilities")
	private int vulnerabilities;
	@Basic(optional = false)
	@Column(name = "duplicated_lines_density")
	private float duplicatedLinesDensity;
	@Basic(optional = false)
	@Column(name = "scope")
	private String scope;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "classes")
	private Double classes;
	@Column(name = "complexity")
	private Double complexity;
	@Column(name = "functions")
	private Double functions;
	@Column(name = "nloc")
	private Double nloc;
	@Column(name = "statements")
	private Double statements;
	@Column(name = "comment_lines_density")
	private Double commentLinesDensity;
	@Basic(optional = false)
	@Column(name = "language")
	private String language;

	public PrincipalMetrics() {
	}

	public PrincipalMetrics(Integer id) {
		this.id = id;
	}

	public PrincipalMetrics(Integer id, String className, String projectName, int version, float tdMinutes,
			double principal, int codeSmells, int bugs, int vulnerabilities, float duplicatedLinesDensity, String scope,
			String language) {
		this.id = id;
		this.className = className;
		this.projectName = projectName;
		this.version = version;
		this.tdMinutes = tdMinutes;
		this.principal = principal;
		this.codeSmells = codeSmells;
		this.bugs = bugs;
		this.vulnerabilities = vulnerabilities;
		this.duplicatedLinesDensity = duplicatedLinesDensity;
		this.scope = scope;
		this.language = language;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public float getTdMinutes() {
		return tdMinutes;
	}

	public void setTdMinutes(float tdMinutes) {
		this.tdMinutes = tdMinutes;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public int getCodeSmells() {
		return codeSmells;
	}

	public void setCodeSmells(int codeSmells) {
		this.codeSmells = codeSmells;
	}

	public int getBugs() {
		return bugs;
	}

	public void setBugs(int bugs) {
		this.bugs = bugs;
	}

	public int getVulnerabilities() {
		return vulnerabilities;
	}

	public void setVulnerabilities(int vulnerabilities) {
		this.vulnerabilities = vulnerabilities;
	}

	public float getDuplicatedLinesDensity() {
		return duplicatedLinesDensity;
	}

	public void setDuplicatedLinesDensity(float duplicatedLinesDensity) {
		this.duplicatedLinesDensity = duplicatedLinesDensity;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Double getClasses() {
		return classes;
	}

	public void setClasses(Double classes) {
		this.classes = classes;
	}

	public Double getComplexity() {
		return complexity;
	}

	public void setComplexity(Double complexity) {
		this.complexity = complexity;
	}

	public Double getFunctions() {
		return functions;
	}

	public void setFunctions(Double functions) {
		this.functions = functions;
	}

	public Double getNloc() {
		return nloc;
	}

	public void setNloc(Double nloc) {
		this.nloc = nloc;
	}

	public Double getStatements() {
		return statements;
	}

	public void setStatements(Double statements) {
		this.statements = statements;
	}

	public Double getCommentLinesDensity() {
		return commentLinesDensity;
	}

	public void setCommentLinesDensity(Double commentLinesDensity) {
		this.commentLinesDensity = commentLinesDensity;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
		if (!(object instanceof PrincipalMetrics)) {
			return false;
		}
		PrincipalMetrics other = (PrincipalMetrics) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "eu.sdk4ed.uom.td.domain.PrincipalMetrics[ id=" + id + " ]";
	}

}
