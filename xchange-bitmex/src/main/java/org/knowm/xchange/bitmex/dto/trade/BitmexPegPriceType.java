package org.knowm.xchange.bitmex.dto.trade;

@SuppressWarnings("unused")
public enum BitmexPegPriceType {
  LAST_PEG("LastPeg"),
  MID_PRICE_PEG("MidPricePeg"),
  MARGET_PEG("MarketPeg"),
  PRIMARY_PEG("PrimaryPeg"),
  TRAILING_STOP_PEG("TrailingStopPeg");

  private String apiParameter;

  BitmexPegPriceType(String apiParameter) {
    this.apiParameter = apiParameter;
  }

  public String toApiParameter() {
    return apiParameter;
  }
}
