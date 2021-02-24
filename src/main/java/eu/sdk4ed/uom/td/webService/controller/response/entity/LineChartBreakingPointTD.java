/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import java.util.Collection;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class LineChartBreakingPointTD {

	private Collection<Double> values;

	public LineChartBreakingPointTD() { }

	public LineChartBreakingPointTD(Collection<Double> values) {
		this.values = values;
	}

	public Collection<Double> getValues() {
		return values;
	}

	public void setValues(Collection<Double> values) {
		this.values = values;
	}

}
