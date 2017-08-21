package org.knowm.xchange.hitbtc.dto.trade;

import java.util.List;

import org.knowm.xchange.hitbtc.dto.HitbtcBaseResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HitbtcMultiExecutionReportResponse extends HitbtcBaseResponse {

  List<HitbtcExecutionReport> executionReport;
  HitbtcCancelReject cancelReject;

  public HitbtcMultiExecutionReportResponse(@JsonProperty(value = "ExecutionReport", required = false) List<HitbtcExecutionReport> executionReport,
      @JsonProperty(value = "CancelReject", required = false) HitbtcCancelReject cancelReject) {

    super();
    this.executionReport = executionReport;
    this.cancelReject = cancelReject;
  }

  public List<HitbtcExecutionReport> getExecutionReport() {

    return executionReport;
  }

  public HitbtcCancelReject getCancelReject() {

    return cancelReject;
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();
    builder.append("HitbtcMultiExecutionReportResponse [executionReport=");
    builder.append(executionReport);
    builder.append(", cancelReject=");
    builder.append(cancelReject);
    builder.append("]");
    return builder.toString();
  }
}
