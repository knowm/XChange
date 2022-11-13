package org.knowm.xchange.binance.dto.meta.exchangeinfo;

import lombok.Getter;

@Getter
public class FutureFilter {
  private String filterType;
  private String minPrice;
  private String maxPrice;
  private String tickSize;
  private String minQty;
  private String maxQty;
  private String stepSize;
  private String limit;
  private String multiplierUp;
  private String multiplierDown;
  private String multiplierDecimal;
  private String notional;

  @Override
  public String toString() {
    return "Filter{"
        + "maxPrice='"
        + maxPrice
        + '\''
        + ", filterType='"
        + filterType
        + '\''
        + ", tickSize='"
        + tickSize
        + '\''
        + ", minPrice='"
        + minPrice
        + '\''
        + ", minQty='"
        + minQty
        + '\''
        + ", maxQty='"
        + maxQty
        + '\''
        + ", stepSize='"
        + stepSize
        + '\''
        + ", notional='"
        + notional
        + '\''
        + '}';
  }
}
