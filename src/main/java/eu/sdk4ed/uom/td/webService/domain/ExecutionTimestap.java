package eu.sdk4ed.uom.td.webService.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Entity(name = "ExecutionTimestap")
@Table(name = "executionTimestap")
@XmlRootElement
public class ExecutionTimestap implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@Column(name = "project_name")
	private String projectName;
	@Basic(optional = false)
	@Column(name = "version")
	private int version;

	public ExecutionTimestap() {
	}

	public ExecutionTimestap(Integer id) {
		this.id = id;
	}

	public ExecutionTimestap(Integer id, String projectName, int version) {
		this.id = id;
		this.projectName = projectName;
		this.version = version;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
		if (!(object instanceof ExecutionTimestap)) {
			return false;
		}
		ExecutionTimestap other = (ExecutionTimestap) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "eu.sdk4ed.uom.td.domain.ExecutionTimestap[ id=" + id + " ]";
	}

}
