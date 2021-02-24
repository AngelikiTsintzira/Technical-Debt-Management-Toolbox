package eu.sdk4ed.uom.td.analysis.oopMaintainabilityMetrics.containers;

import eu.sdk4ed.uom.td.analysis.oopMaintainabilityMetrics.metrics.ClassMetrics;

import java.util.HashMap;

public class ClassMetricsContainer {
	private final HashMap<String, ClassMetrics> classToMetrics = new HashMap<>();

	public ClassMetrics getMetrics(String name) {
		ClassMetrics cm = this.classToMetrics.get(name);
		if (cm == null) {
			cm = new ClassMetrics();
			this.classToMetrics.put(name, cm);
		}
		return cm;
	}
}
