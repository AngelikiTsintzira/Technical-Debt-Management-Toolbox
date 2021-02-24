/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import java.util.Collection;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class CumulativeInterestLineChart {

	private Collection<Double> values;

	public CumulativeInterestLineChart() {	}

	public CumulativeInterestLineChart(Collection<Double> values) {
		this.values = values;
	}

	public Collection<Double> getValues() {
		return values;
	}

	public void setValues(Collection<Double> values) {
		this.values = values;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		CumulativeInterestLineChart other = (CumulativeInterestLineChart) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

}
