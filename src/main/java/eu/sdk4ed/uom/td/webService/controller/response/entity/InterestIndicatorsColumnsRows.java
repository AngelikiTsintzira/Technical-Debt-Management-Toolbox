/**
 * 
 */
package eu.sdk4ed.uom.td.webService.controller.response.entity;

import java.util.List;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class InterestIndicatorsColumnsRows {

	private List<InterestIndicatorsColumnLabelField> columns;

	private List<InterestIndicatorAbstract> rows;

	public InterestIndicatorsColumnsRows() { }

	public InterestIndicatorsColumnsRows(List<InterestIndicatorsColumnLabelField> columns, List<InterestIndicatorAbstract> rows) {
		this.columns = columns;
		this.rows = rows;
	}

	public List<InterestIndicatorsColumnLabelField> getColumns() {
		return columns;
	}

	public void setColumns(List<InterestIndicatorsColumnLabelField> columns) {
		this.columns = columns;
	}

	public List<InterestIndicatorAbstract> getRows() {
		return rows;
	}

	public void setRows(List<InterestIndicatorAbstract> rows) {
		this.rows = rows;
	}

}
