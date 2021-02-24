package eu.sdk4ed.uom.td.analysis.oopMaintainabilityMetrics.output;

import eu.sdk4ed.uom.td.analysis.oopMaintainabilityMetrics.metrics.ClassMetrics;
import eu.sdk4ed.uom.td.analysis.oopMaintainabilityMetrics.metrics.ProjectMetrics;

public abstract interface CkjmOutputHandler
{
  public abstract void handleProject(String paramString, ProjectMetrics paramProjectMetrics);

  public abstract void handleClass(String paramString, ClassMetrics paramClassMetrics);
}