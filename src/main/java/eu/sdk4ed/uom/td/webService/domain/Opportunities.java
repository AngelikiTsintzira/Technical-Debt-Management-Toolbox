package eu.sdk4ed.uom.td.webService.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Entity(name = "Opportunities")
@Table(name = "OPPORTUNITIES")
@XmlRootElement
public class Opportunities implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@Lob
	@Column(name = "PROJECT_NAME", columnDefinition = "TEXT")
	private String projectName;
	@Basic(optional = false)
	@Column(name = "PROJECT_VERSION")
	private int projectVersion;
	@Basic(optional = false)
	@Lob
	@Column(name = "CLASS_PATH", columnDefinition = "TEXT")
	private String classPath;
	@Basic(optional = false)
	@Lob
	@Column(name = "CLASS_NAME", columnDefinition = "TEXT")
	private String className;
	@Basic(optional = false)
	@Lob
	@Column(name = "METHOD_NAME", columnDefinition = "TEXT")
	private String methodName;
	@Basic(optional = false)
	@Column(name = "LINE_START")
	private int lineStart;
	@Basic(optional = false)
	@Column(name = "LINE_END")
	private int lineEnd;
	@Basic(optional = false)
	@Column(name = "COHESION_BENEFIIT")
	private double cohesionBenefiit;
	@Basic(optional = false)
	@Column(name = "METHOD_ORIGINAL_COHESION")
	private double methodOriginalCohesion;
	@Basic(optional = false)
	@Column(name = "LINES_OF_CODE")
	private double linesOfCode;

	public Opportunities() {
	}

	public Opportunities(Integer id) {
		this.id = id;
	}

	public Opportunities(Integer id, String projectName, int projectVersion, String classPath, String className,
			String methodName, int lineStart, int lineEnd, double cohesionBenefiit, double methodOriginalCohesion,
			double linesOfCode) {
		this.id = id;
		this.projectName = projectName;
		this.projectVersion = projectVersion;
		this.classPath = classPath;
		this.className = className;
		this.methodName = methodName;
		this.lineStart = lineStart;
		this.lineEnd = lineEnd;
		this.cohesionBenefiit = cohesionBenefiit;
		this.methodOriginalCohesion = methodOriginalCohesion;
		this.linesOfCode = linesOfCode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(int projectVersion) {
		this.projectVersion = projectVersion;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getLineStart() {
		return lineStart;
	}

	public void setLineStart(int lineStart) {
		this.lineStart = lineStart;
	}

	public int getLineEnd() {
		return lineEnd;
	}

	public void setLineEnd(int lineEnd) {
		this.lineEnd = lineEnd;
	}

	public double getCohesionBenefiit() {
		return cohesionBenefiit;
	}

	public void setCohesionBenefiit(double cohesionBenefiit) {
		this.cohesionBenefiit = cohesionBenefiit;
	}

	public double getMethodOriginalCohesion() {
		return methodOriginalCohesion;
	}

	public void setMethodOriginalCohesion(double methodOriginalCohesion) {
		this.methodOriginalCohesion = methodOriginalCohesion;
	}

	public double getLinesOfCode() {
		return linesOfCode;
	}

	public void setLinesOfCode(double linesOfCode) {
		this.linesOfCode = linesOfCode;
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
		if (!(object instanceof Opportunities)) {
			return false;
		}
		Opportunities other = (Opportunities) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "eu.sdk4ed.uom.td.domain.Opportunities[ id=" + id + " ]";
	}

}
