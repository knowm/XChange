package org.knowm.xchange.globitex.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class GlobitexExecutionReport implements Serializable {

  @JsonProperty("ExecutionReport")
  private final ExecutionReportObject object;

  public GlobitexExecutionReport(@JsonProperty("ExecutionReport") ExecutionReportObject object) {
    this.object = object;
  }

  public ExecutionReportObject getObject() {
    return object;
  }

  @Override
  public String toString() {
    return "GlobitexExecutionReport{" + "object=" + object + '}';
  }
}
