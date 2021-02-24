/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import java.util.Objects;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class LineChartBreakingPointTDDivide {

	private Double dividedValue;

	public LineChartBreakingPointTDDivide() { }

	public LineChartBreakingPointTDDivide(Double principal, Double interest) {
		if (Objects.isNull(principal) || Objects.isNull(interest) || interest == 0)
			this.dividedValue = null;
		else
			this.dividedValue = principal / interest;
	}

	public Double getDividedValue() {
		return dividedValue;
	}

	public void setDividedValue(Double dividedValue) {
		this.dividedValue = dividedValue;
	}

	@Override
	public String toString() {
		return "LineChartBreakingPointTDDivide [dividedValue=" + dividedValue + "]";
	}

}
