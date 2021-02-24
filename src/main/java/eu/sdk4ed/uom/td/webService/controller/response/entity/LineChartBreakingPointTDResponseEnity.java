/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import java.util.List;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class LineChartBreakingPointTDResponseEnity {

	private LineChartBreakingPointTD lineChartBreakingPointTD;

	public LineChartBreakingPointTDResponseEnity() { }

	public LineChartBreakingPointTDResponseEnity(LineChartBreakingPointTD lineChartBreakingPointTD) {
		this.lineChartBreakingPointTD = lineChartBreakingPointTD;
	}

	public LineChartBreakingPointTDResponseEnity(List<Double> values) {
		this.lineChartBreakingPointTD = new LineChartBreakingPointTD(values);
	}

	public LineChartBreakingPointTD getLineChartBreakingPointTD() {
		return lineChartBreakingPointTD;
	}

	public void setLineChartBreakingPointTD(LineChartBreakingPointTD lineChartBreakingPointTD) {
		this.lineChartBreakingPointTD = lineChartBreakingPointTD;
	}

}
