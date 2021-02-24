package eu.sdk4ed.uom.td.webService.controller.response.entity;

public class moveClassRefactoringsMetricsResponseEntity {
	
	private moveClassRefactoringsMetrics metrics;
	
	public moveClassRefactoringsMetricsResponseEntity() { }

	public moveClassRefactoringsMetricsResponseEntity(moveClassRefactoringsMetrics metrics) {
		this.metrics = metrics;
	}

	public moveClassRefactoringsMetrics getMoveClassProjectMetrics() {
		return metrics;
	}

	public void setMoveClassRefactoringsMetrics(moveClassRefactoringsMetrics metrics) {
		this.metrics = metrics;
	}

}
