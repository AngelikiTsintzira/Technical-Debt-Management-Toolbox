package eu.sdk4ed.uom.td.webService.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "GeaProjects")
@Table(name = "gea_projects")
@XmlRootElement
public class GeaProjects implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
	@Basic(optional = false)
	@Column(name = "coupling_old")
	private double couplingOld;
	@Basic(optional = false)
	@Column(name = "cohesion_old")
	private double cohesionOld;
	@Basic(optional = false)
	@Column(name = "coupling_new")
	private double couplingNew;
	@Basic(optional = false)
	@Column(name = "cohesion_new")
	private double cohesionNew;

	public GeaProjects() {
	}

	public GeaProjects(String name) {
		this.name = name;
	}

	public GeaProjects(String name, double couplingOld, double cohesionOld, double couplingNew, double cohesionNew) {
		
		//this.name = name;
		//this.couplingOld = couplingOld;
		//this.cohesionOld = cohesionOld;
		//this.couplingNew = couplingNew;
		//this.cohesionNew = cohesionNew;

		if (Double.valueOf(couplingOld)  == null)
			this.couplingOld = 0.0;
		else
			this.couplingOld = couplingOld;

		if (Double.valueOf(couplingNew)  == null)
			this.couplingNew = 0.0;
		else
			this.couplingNew = couplingNew;

		if (Double.valueOf(cohesionOld)  == null)
			this.cohesionOld = 0.0;
		else
			this.cohesionOld = cohesionOld;

		if (Double.valueOf(cohesionNew)  == null)
			this.cohesionNew = 0.0;
		else
			this.cohesionNew = cohesionNew;

		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCouplingOld() {
		return couplingOld;
	}

	public void setCouplingOld(double couplingOld) {
		this.couplingOld = couplingOld;
	}

	public double getCohesionOld() {
		return cohesionOld;
	}

	public void setCohesionOld(double cohesionOld) {
		this.cohesionOld = cohesionOld;
	}

	public double getCouplingNew() {
		return couplingNew;
	}

	public void setCouplingNew(double couplingNew) {
		this.couplingNew = couplingNew;
	}

	public double getCohesionNew() {
		return cohesionNew;
	}

	public void setCohesionNew(double cohesionNew) {
		this.cohesionNew = cohesionNew;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (name != null ? name.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof GeaProjects)) {
			return false;
		}
		GeaProjects other = (GeaProjects) object;
		if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "eu.sdk4ed.uom.td.domain.GeaProjects[ name=" + name + " ]";
	}

}
