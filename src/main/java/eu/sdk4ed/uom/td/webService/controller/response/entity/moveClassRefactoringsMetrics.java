package eu.sdk4ed.uom.td.webService.controller.response.entity;

public class moveClassRefactoringsMetrics 
{
	private String projectName;
	private Double couplingOld;
    private Double couplingNew; 
    private Double cohesionOld;
    private Double cohesionNew;
    
    public moveClassRefactoringsMetrics() { } 
    
    public moveClassRefactoringsMetrics(String project, Double couplingO, Double couplingN, Double cohesionO, Double cohesionN)
    {
        if (Double.valueOf(couplingO)  == null)
        	this.couplingOld = 0.0;
        else
        	this.couplingOld = couplingO;
    	
        if (Double.valueOf(couplingN)  == null)
        	this.couplingNew = 0.0;
        else
        	this.couplingNew = couplingN;
        
        if (Double.valueOf(cohesionO)  == null)
        	this.cohesionOld = 0.0;
        else
        	this.cohesionOld = cohesionO;
        
        if (Double.valueOf(cohesionN)  == null)
        	this.cohesionNew = 0.0;
        else
        	this.cohesionNew = cohesionN;
    	
    	this.projectName = project;
    }
    
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName)
	{
		this.projectName = projectName;
	}
    
	public Double getCouplingOld() {
		return couplingOld;
	}
	
	public void setCouplingOld(double couplingOld)
	{
		this.couplingOld = couplingOld;
	}
    
	public Double getCouplingNew() {
		return couplingNew;
	}
	
	public void setCouplingNew(double couplingNew)
	{
		this.couplingNew = couplingNew;
	}
    
	public Double getCohesionOld() {
		return cohesionOld;
	}
	
	public void setCohesionOld(double cohesionOld)
	{
		this.cohesionOld = cohesionOld;
	}
    
	public Double getCohesionNew() {
		return cohesionNew;
	}
	
	public void setCohesionNew(double cohesionNew)
	{
		this.cohesionNew = cohesionNew;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((couplingOld == null) ? 0 : couplingOld.hashCode());
		result = prime * result + ((couplingNew == null) ? 0 : couplingNew.hashCode());
		result = prime * result + ((cohesionOld == null) ? 0 : cohesionOld.hashCode());
		result = prime * result + ((cohesionNew == null) ? 0 : cohesionNew.hashCode());
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
		
		moveClassRefactoringsMetrics other = (moveClassRefactoringsMetrics) obj;
		
		if (projectName == null) {
			if (other.projectName != null)
				return false;
		} else if (!projectName.equals(other.projectName))
			return false;
		if (couplingOld == null) {
			if (other.couplingOld != null)
				return false;
		} else if (!couplingOld.equals(other.couplingOld))
			return false;
		if (couplingNew == null) {
			if (other.couplingNew != null)
				return false;
		} else if (!couplingNew.equals(other.couplingNew))
			return false;
		if (cohesionOld == null) {
			if (other.cohesionOld != null)
				return false;
		} else if (!cohesionOld.equals(other.cohesionOld))
			return false;
		if (cohesionNew == null) {
			if (other.cohesionNew != null)
				return false;
		} else if (!cohesionNew.equals(other.cohesionNew))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		
		return "moveClassRefactoringMetrics [projectName=" + projectName + ", couplingOld=" + couplingOld + ", couplingNew=" + couplingNew + ", cohesionOld=" + cohesionOld + ", cohesionNew=" + cohesionNew + "]";
	}

}
