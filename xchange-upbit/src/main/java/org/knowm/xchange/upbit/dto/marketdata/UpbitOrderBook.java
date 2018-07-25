package org.knowm.xchange.upbit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author interwater */
public class UpbitOrderBook {

  private final String market;
  private final BigDecimal timestamp;
  private final BigDecimal totalAskSize;
  private final BigDecimal totalBidSize;
  private final UpbitOrderBookData[] orderbookUnits;

  /**
   * @param market
   * @param timestamp
   * @param totalAskSize
   * @param totalBidSize
   * @param orderbookUnits
   */
  public UpbitOrderBook(
      @JsonProperty("market") String market,
      @JsonProperty("timestamp") BigDecimal timestamp,
      @JsonProperty("total_ask_size") BigDecimal totalAskSize,
      @JsonProperty("total_bid_size") BigDecimal totalBidSize,
      @JsonProperty("orderbook_units") UpbitOrderBookData[] orderbookUnits) {
    this.market = market;
    this.timestamp = timestamp;
    this.totalAskSize = totalAskSize;
    this.totalBidSize = totalBidSize;
    this.orderbookUnits = orderbookUnits;
  }

  public String getMarket() {
    return market;
  }

  public BigDecimal getTimestamp() {
    return timestamp;
  }

  public BigDecimal getTotalAskSize() {
    return totalAskSize;
  }

  public BigDecimal getTotalBidSize() {
    return totalBidSize;
  }

  public UpbitOrderBookData[] getOrderbookUnits() {
    return orderbookUnits;
  }
}
