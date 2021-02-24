/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import java.util.Collection;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class CumulativeInterestLineChartResponseEnity {

	private CumulativeInterestLineChart cumulativeInterestLineChart;

	public CumulativeInterestLineChartResponseEnity() {	}

	public CumulativeInterestLineChartResponseEnity(CumulativeInterestLineChart cumulativeInterestLineChart) {
		this.cumulativeInterestLineChart = cumulativeInterestLineChart;
	}

	public CumulativeInterestLineChartResponseEnity(Collection<Double> values) {
		this.cumulativeInterestLineChart = new CumulativeInterestLineChart(values);
	}

	public CumulativeInterestLineChart getCumulativeInterestLineChart() {
		return cumulativeInterestLineChart;
	}

	public void setCumulativeInterestLineChart(CumulativeInterestLineChart cumulativeInterestLineChart) {
		this.cumulativeInterestLineChart = cumulativeInterestLineChart;
	}

}
