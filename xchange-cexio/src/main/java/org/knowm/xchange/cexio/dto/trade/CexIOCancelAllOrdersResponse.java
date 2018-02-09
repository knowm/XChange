package org.knowm.xchange.cexio.dto.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CexIOCancelAllOrdersResponse {

  @JsonProperty("e")
  private String eventName;

  @JsonProperty("ok")
  private String status;

  @JsonProperty("data")
  private List<String> canceledOrderIds;

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<String> getCanceledOrderIds() {
    return canceledOrderIds;
  }

  public void setCanceledOrderIds(List<String> canceledOrderIds) {
    this.canceledOrderIds = canceledOrderIds;
  }

  @Override
  public String toString() {
    return "CexIOCancelAllOrdersResponse{" +
        "eventName='" + eventName + '\'' +
        ", status='" + status + '\'' +
        ", canceledOrderIds=" + canceledOrderIds +
        '}';
  }
}
