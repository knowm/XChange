package org.knowm.xchange.binance.dto.meta.exchangeinfo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Filter {

  private String maxPrice;

  private String filterType;

  private String tickSize;

  private String minPrice;

  private String minQty;

  private String maxQty;

  private String stepSize;

  private String minNotional;

  private String maxNotional;

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
        + ", minNotional='"
        + minNotional
        + '\''
        + ", maxNotional='"
        + maxNotional
        + '\''
        + '}';
  }
}
