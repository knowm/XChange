package org.knowm.xchange.bitflyer.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitflyerChildOrderAcceptance {
  @JsonProperty("child_order_acceptance_id")
  private String childOrderAcceptanceId;

  public String getChildOrderAcceptanceId() {
    return childOrderAcceptanceId;
  }

  public void setChildOrderAcceptanceId(String parentOrderAcceptanceId) {
    this.childOrderAcceptanceId = parentOrderAcceptanceId;
  }

  @Override
  public String toString() {
    return "BitflyerChildOrderAcceptance{"
        + "childOrderAcceptanceId='"
        + childOrderAcceptanceId
        + '\''
        + '}';
  }
}
