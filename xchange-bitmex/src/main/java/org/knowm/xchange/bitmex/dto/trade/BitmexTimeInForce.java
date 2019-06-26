package org.knowm.xchange.bitmex.dto.trade;

@SuppressWarnings("unused")
public enum BitmexTimeInForce {
  DAY("Day"),
  GOOD_TILL_CANCEL("GoodTillCancel"),
  IMMEDIATE_OR_CANCEL("ImmediateOrCancel"),
  FILL_OR_KILL("FillOrKill");

  private String apiParameter;

  BitmexTimeInForce(String apiParameter) {
    this.apiParameter = apiParameter;
  }

  public String toApiParameter() {
    return apiParameter;
  }
}
