package org.knowm.xchange.bitmex.dto.trade;

@SuppressWarnings("unused")
public enum BitmexOrderType {
  LIMIT("Limit"),
  STOP("Stop"),
  MARKET("Market"),
  STOP_LIMIT("StopLimit"),
  PEGGED("Pegged"),
  MARKET_IF_TOUCHED("MarketIfTouched"),
  LIMIT_IF_TOUCHED("LimitIfTouched"),
  MARKET_WITH_LEFT_OVER_AS_LIMIT("MarketWithLeftOverAsLimit");

  private String apiParameter;

  BitmexOrderType(String apiParameter) {
    this.apiParameter = apiParameter;
  }

  public String toApiParameter() {
    return apiParameter;
  }
}
