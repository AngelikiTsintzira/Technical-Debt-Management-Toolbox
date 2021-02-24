/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class InterestIndicatorsColumnLabelField {

	private String label;
	private String field;

	public InterestIndicatorsColumnLabelField(String label, String field) {
		this.label = label;
		this.field = field;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		InterestIndicatorsColumnLabelField other = (InterestIndicatorsColumnLabelField) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InterestIndicatorsColumnLabelField [label=" + label + ", field=" + field + "]";
	}

}
