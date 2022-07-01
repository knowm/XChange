package org.knowm.xchange.bitflyer.dto.trade.results;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitflyerParentOrderAcceptance {
  @JsonProperty("parent_order_acceptance_id")
  private String parentOrderAcceptanceId;

  public String getParentOrderAcceptanceId() {
    return parentOrderAcceptanceId;
  }

  public void setParentOrderAcceptanceId(String parentOrderAcceptanceId) {
    this.parentOrderAcceptanceId = parentOrderAcceptanceId;
  }

  @Override
  public String toString() {
    return "BitflyerParentOrderAcceptance{"
        + "parentOrderAcceptanceId='"
        + parentOrderAcceptanceId
        + '\''
        + '}';
  }
}
