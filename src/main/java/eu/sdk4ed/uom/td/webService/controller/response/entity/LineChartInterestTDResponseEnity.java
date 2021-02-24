/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import java.util.Collection;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class LineChartInterestTDResponseEnity {

	private LineChartInterestTD lineChartInterestTD;

	public LineChartInterestTDResponseEnity() {}

	public LineChartInterestTDResponseEnity(LineChartInterestTD lineChartInterestTD) {
		this.lineChartInterestTD = lineChartInterestTD;
	}

	public LineChartInterestTDResponseEnity(Collection<Double> values) {
		this.lineChartInterestTD = new LineChartInterestTD(values);
	}

	public LineChartInterestTD getLineChartInterestTD() {
		return lineChartInterestTD;
	}

	public void setLineChartInterestTD(LineChartInterestTD lineChartInterestTD) {
		this.lineChartInterestTD = lineChartInterestTD;
	}

}
