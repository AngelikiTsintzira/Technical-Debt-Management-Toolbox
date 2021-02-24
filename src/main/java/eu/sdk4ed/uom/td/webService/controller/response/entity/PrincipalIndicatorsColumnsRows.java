/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import java.util.List;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class PrincipalIndicatorsColumnsRows {

	private List<PrincipalIndicatorsColumnLabelField> columns;

	private List<PrincipalMetricsIndicators> rows;

	public PrincipalIndicatorsColumnsRows() { }

	public PrincipalIndicatorsColumnsRows(List<PrincipalIndicatorsColumnLabelField> columns, List<PrincipalMetricsIndicators> rows) {
		this.columns = columns;
		this.rows = rows;
	}

	public List<PrincipalIndicatorsColumnLabelField> getColumns() {
		return columns;
	}

	public void setColumns(List<PrincipalIndicatorsColumnLabelField> columns) {
		this.columns = columns;
	}

	public List<PrincipalMetricsIndicators> getRows() {
		return rows;
	}

	public void setRows(List<PrincipalMetricsIndicators> rows) {
		this.rows = rows;
	}

}
