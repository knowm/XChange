package com.xeiam.xchange.hitbtc.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcExecutionReportResponse {

  HitbtcExecutionReport executionReport;
  HitbtcCancelReject cancelReject;

  public HitbtcExecutionReportResponse(@JsonProperty(value = "ExecutionReport", required = false) HitbtcExecutionReport executionReport,
      @JsonProperty(value = "CancelReject", required = false) HitbtcCancelReject cancelReject) {

    super();
    this.executionReport = executionReport;
    this.cancelReject = cancelReject;
  }

  public HitbtcExecutionReport getExecutionReport() {

    return executionReport;
  }

  public HitbtcCancelReject getCancelReject() {

    return cancelReject;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcExecutionReportResponse [executionReport=");
    builder.append(executionReport);
    builder.append(", cancelReject=");
    builder.append(cancelReject);
    builder.append("]");
    return builder.toString();
  }
}
