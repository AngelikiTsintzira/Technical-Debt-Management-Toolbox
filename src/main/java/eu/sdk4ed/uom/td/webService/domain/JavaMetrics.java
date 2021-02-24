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

@Entity(name = "JavaMetrics")
@Table(name = "javaMetrics")
@XmlRootElement
public class JavaMetrics implements Serializable {

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
	@Column(name = "scope")
	private String scope;
	@Basic(optional = false)
	@Column(name = "wmc")
	private double wmc;
	@Basic(optional = false)
	@Column(name = "dit")
	private double dit;
	@Basic(optional = false)
	@Column(name = "cbo")
	private double cbo;
	@Basic(optional = false)
	@Column(name = "rfc")
	private double rfc;
	@Basic(optional = false)
	@Column(name = "lcom")
	private double lcom;
	@Basic(optional = false)
	@Column(name = "wmc_dec")
	private double wmcDec;
	@Basic(optional = false)
	@Column(name = "nocc")
	private double nocc;
	@Basic(optional = false)
	@Column(name = "mpc")
	private double mpc;
	@Basic(optional = false)
	@Column(name = "dac")
	private double dac;
	@Basic(optional = false)
	@Column(name = "loc")
	private double loc;
	@Basic(optional = false)
	@Column(name = "number_of_properties")
	private double numberOfProperties;
	@Basic(optional = false)
	@Column(name = "dsc")
	private double dsc;
	@Basic(optional = false)
	@Column(name = "noh")
	private double noh;
	@Basic(optional = false)
	@Column(name = "ana")
	private double ana;
	@Basic(optional = false)
	@Column(name = "dam")
	private double dam;
	@Basic(optional = false)
	@Column(name = "dcc")
	private double dcc;
	@Basic(optional = false)
	@Column(name = "camc")
	private double camc;
	@Basic(optional = false)
	@Column(name = "moa")
	private double moa;
	@Basic(optional = false)
	@Column(name = "mfa")
	private double mfa;
	@Basic(optional = false)
	@Column(name = "nop")
	private double nop;
	@Basic(optional = false)
	@Column(name = "cis")
	private double cis;
	@Basic(optional = false)
	@Column(name = "nom")
	private double nom;
	@Basic(optional = false)
	@Column(name = "reusability")
	private double reusability;
	@Basic(optional = false)
	@Column(name = "flexibility")
	private double flexibility;
	@Basic(optional = false)
	@Column(name = "understandability")
	private double understandability;
	@Basic(optional = false)
	@Column(name = "functionality")
	private double functionality;
	@Basic(optional = false)
	@Column(name = "extendibility")
	private double extendibility;
	@Basic(optional = false)
	@Column(name = "effectiveness")
	private double effectiveness;
	@Basic(optional = false)
	@Column(name = "fanIn")
	private double fanIn;
	@Basic(optional = false)
	@Column(name = "commit_hash")
	private String commitHash;
	@Basic(optional = false)
	@Column(name = "version")
	private int version;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "principal")
	private Double principal;
	@Column(name = "interest")
	private Double interest;
	@Column(name = "breakingpoint")
	private Double breakingpoint;
	@Column(name = "frequency_of_change")
	private Double frequencyOfChange;
	@Column(name = "interest_probability")
	private Double interestProbability;
	@Column(name = "rem")
	private Double rem;
	@Column(name = "cpm")
	private Double cpm;

	public JavaMetrics() {
	}

	public JavaMetrics(Integer id) {
		this.id = id;
	}

	public JavaMetrics(Integer id, String className, String projectName, double wmc, double dit, double cbo, double rfc,
			double lcom, double wmcDec, double nocc, double mpc, double dac, double loc, double numberOfProperties,
			double dsc, double noh, double ana, double dam, double dcc, double camc, double moa, double mfa, double nop,
			double cis, double nom, double reusability, double flexibility, double understandability,
			double functionality, double extendibility, double effectiveness, double fanIn, String commitHash,
			int version) {
		this.id = id;
		this.className = className;
		this.projectName = projectName;
		this.wmc = wmc;
		this.dit = dit;
		this.cbo = cbo;
		this.rfc = rfc;
		this.lcom = lcom;
		this.wmcDec = wmcDec;
		this.nocc = nocc;
		this.mpc = mpc;
		this.dac = dac;
		this.loc = loc;
		this.numberOfProperties = numberOfProperties;
		this.dsc = dsc;
		this.noh = noh;
		this.ana = ana;
		this.dam = dam;
		this.dcc = dcc;
		this.camc = camc;
		this.moa = moa;
		this.mfa = mfa;
		this.nop = nop;
		this.cis = cis;
		this.nom = nom;
		this.reusability = reusability;
		this.flexibility = flexibility;
		this.understandability = understandability;
		this.functionality = functionality;
		this.extendibility = extendibility;
		this.effectiveness = effectiveness;
		this.fanIn = fanIn;
		this.commitHash = commitHash;
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

	public double getWmc() {
		return wmc;
	}

	public void setWmc(double wmc) {
		this.wmc = wmc;
	}

	public double getDit() {
		return dit;
	}

	public void setDit(double dit) {
		this.dit = dit;
	}

	public double getCbo() {
		return cbo;
	}

	public void setCbo(double cbo) {
		this.cbo = cbo;
	}

	public double getRfc() {
		return rfc;
	}

	public void setRfc(double rfc) {
		this.rfc = rfc;
	}

	public double getLcom() {
		return lcom;
	}

	public void setLcom(double lcom) {
		this.lcom = lcom;
	}

	public double getWmcDec() {
		return wmcDec;
	}

	public void setWmcDec(double wmcDec) {
		this.wmcDec = wmcDec;
	}

	public double getNocc() {
		return nocc;
	}

	public void setNocc(double nocc) {
		this.nocc = nocc;
	}

	public double getMpc() {
		return mpc;
	}

	public void setMpc(double mpc) {
		this.mpc = mpc;
	}

	public double getDac() {
		return dac;
	}

	public void setDac(double dac) {
		this.dac = dac;
	}

	public double getLoc() {
		return loc;
	}

	public void setLoc(double loc) {
		this.loc = loc;
	}

	public double getNumberOfProperties() {
		return numberOfProperties;
	}

	public void setNumberOfProperties(double numberOfProperties) {
		this.numberOfProperties = numberOfProperties;
	}

	public double getDsc() {
		return dsc;
	}

	public void setDsc(double dsc) {
		this.dsc = dsc;
	}

	public double getNoh() {
		return noh;
	}

	public void setNoh(double noh) {
		this.noh = noh;
	}

	public double getAna() {
		return ana;
	}

	public void setAna(double ana) {
		this.ana = ana;
	}

	public double getDam() {
		return dam;
	}

	public void setDam(double dam) {
		this.dam = dam;
	}

	public double getDcc() {
		return dcc;
	}

	public void setDcc(double dcc) {
		this.dcc = dcc;
	}

	public double getCamc() {
		return camc;
	}

	public void setCamc(double camc) {
		this.camc = camc;
	}

	public double getMoa() {
		return moa;
	}

	public void setMoa(double moa) {
		this.moa = moa;
	}

	public double getMfa() {
		return mfa;
	}

	public void setMfa(double mfa) {
		this.mfa = mfa;
	}

	public double getNop() {
		return nop;
	}

	public void setNop(double nop) {
		this.nop = nop;
	}

	public double getCis() {
		return cis;
	}

	public void setCis(double cis) {
		this.cis = cis;
	}

	public double getNom() {
		return nom;
	}

	public void setNom(double nom) {
		this.nom = nom;
	}

	public double getReusability() {
		return reusability;
	}

	public void setReusability(double reusability) {
		this.reusability = reusability;
	}

	public double getFlexibility() {
		return flexibility;
	}

	public void setFlexibility(double flexibility) {
		this.flexibility = flexibility;
	}

	public double getUnderstandability() {
		return understandability;
	}

	public void setUnderstandability(double understandability) {
		this.understandability = understandability;
	}

	public double getFunctionality() {
		return functionality;
	}

	public void setFunctionality(double functionality) {
		this.functionality = functionality;
	}

	public double getExtendibility() {
		return extendibility;
	}

	public void setExtendibility(double extendibility) {
		this.extendibility = extendibility;
	}

	public double getEffectiveness() {
		return effectiveness;
	}

	public void setEffectiveness(double effectiveness) {
		this.effectiveness = effectiveness;
	}

	public double getFanIn() {
		return fanIn;
	}

	public void setFanIn(double fanIn) {
		this.fanIn = fanIn;
	}

	public String getCommitHash() {
		return commitHash;
	}

	public void setCommitHash(String commitHash) {
		this.commitHash = commitHash;
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

	public Double getBreakingpoint() {
		return breakingpoint;
	}

	public void setBreakingpoint(Double breakingpoint) {
		this.breakingpoint = breakingpoint;
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

	public Double getRem() {
		return rem;
	}

	public void setRem(Double rem) {
		this.rem = rem;
	}

	public Double getCpm() {
		return cpm;
	}

	public void setCpm(Double cpm) {
		this.cpm = cpm;
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
		if (!(object instanceof JavaMetrics)) {
			return false;
		}
		JavaMetrics other = (JavaMetrics) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "eu.sdk4ed.uom.td.domain.JavaMetrics[ id=" + id + " ]";
	}

}
