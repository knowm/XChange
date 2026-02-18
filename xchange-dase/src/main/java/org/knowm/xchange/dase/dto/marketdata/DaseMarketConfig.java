package org.knowm.xchange.dase.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DaseMarketConfig {
  @JsonProperty("market")
  public String market;

  @JsonProperty("description")
  public String description;

  @JsonProperty("base")
  public String base;

  @JsonProperty("size_precision")
  public Integer sizePrecision;

  @JsonProperty("quote")
  public String quote;

  @JsonProperty("price_precision")
  public Integer pricePrecision;

  @JsonProperty("market_order_price_slippage")
  public String marketOrderPriceSlippage;

  @JsonProperty("min_order_size")
  public String minOrderSize;

  @JsonProperty("min_funds")
  public String minFunds;
}
