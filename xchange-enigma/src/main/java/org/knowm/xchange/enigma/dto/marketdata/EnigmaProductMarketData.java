package org.knowm.xchange.enigma.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class EnigmaProductMarketData {
  private String productName;
  private BigDecimal bid;
  private BigDecimal ask;

  public EnigmaProductMarketData(
      @JsonProperty("product_name") String productName,
      @JsonProperty("bid") BigDecimal bid,
      @JsonProperty("ask") BigDecimal ask) {
    this.productName = productName;
    this.ask = ask;
    this.bid = bid;
  }

  public String getProductName() {
    return this.productName;
  }

  public BigDecimal getBid() {
    return this.bid;
  }

  public BigDecimal getAsk() {
    return this.ask;
  }
}
