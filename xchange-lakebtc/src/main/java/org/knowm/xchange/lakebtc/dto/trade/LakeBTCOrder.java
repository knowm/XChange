package org.knowm.xchange.lakebtc.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** Created by cristian.lucaci on 12/19/2014. */
public class LakeBTCOrder {

  private final BigDecimal trades;

  private final BigDecimal totalTradedBtc;

  private final BigDecimal totalTradedCurrency;

  private final String currency;

  private final BigDecimal ppc;

  public LakeBTCOrder(
      @JsonProperty("trades") BigDecimal trades,
      @JsonProperty("total_traded_btc") BigDecimal totalTradedBtc,
      @JsonProperty("total_traded_currency") BigDecimal totalTradedCurrency,
      @JsonProperty("currency") String currency,
      @JsonProperty("ppc") BigDecimal ppc) {
    this.trades = trades;
    this.totalTradedBtc = totalTradedBtc;
    this.totalTradedCurrency = totalTradedCurrency;
    this.currency = currency;
    this.ppc = ppc;
  }

  public BigDecimal getTrades() {
    return trades;
  }

  public BigDecimal getTotalTradedBtc() {
    return totalTradedBtc;
  }

  public BigDecimal getTotalTradedCurrency() {
    return totalTradedCurrency;
  }

  public String getCurrency() {
    return currency;
  }

  public BigDecimal getPpc() {
    return ppc;
  }
}
