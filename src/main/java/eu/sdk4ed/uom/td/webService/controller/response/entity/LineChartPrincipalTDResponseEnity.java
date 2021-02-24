/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import java.util.Collection;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class LineChartPrincipalTDResponseEnity {

	private LineChartPrincipalTD lineChartPrincipalTD;

	public LineChartPrincipalTDResponseEnity() { }

	public LineChartPrincipalTDResponseEnity(LineChartPrincipalTD lineChartPrincipalTD) {
		this.lineChartPrincipalTD = lineChartPrincipalTD;
	}

	public LineChartPrincipalTDResponseEnity(Collection<Double> values) {
		this.lineChartPrincipalTD = new LineChartPrincipalTD(values);
	}

	public LineChartPrincipalTD getLineChartPrincipalTD() {
		return lineChartPrincipalTD;
	}

	public void setLineChartPrincipalTD(LineChartPrincipalTD lineChartPrincipalTD) {
		this.lineChartPrincipalTD = lineChartPrincipalTD;
	}
	
	

}
