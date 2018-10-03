package org.knowm.xchange.upbit.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author interwater */
public class UpbitOrderBookData {

  private final BigDecimal askPrice;
  private final BigDecimal bidPrice;
  private final BigDecimal askSize;
  private final BigDecimal bidSize;

  /**
   * @param askPrice
   * @param bidPrice
   * @param askSize
   * @param bidSize
   */
  public UpbitOrderBookData(
      @JsonProperty("ask_price") BigDecimal askPrice,
      @JsonProperty("bid_price") BigDecimal bidPrice,
      @JsonProperty("ask_size") BigDecimal askSize,
      @JsonProperty("bid_size") BigDecimal bidSize) {
    this.askPrice = askPrice;
    this.bidPrice = bidPrice;
    this.askSize = askSize;
    this.bidSize = bidSize;
  }

  public BigDecimal getAskPrice() {
    return askPrice;
  }

  public BigDecimal getBidPrice() {
    return bidPrice;
  }

  public BigDecimal getAskSize() {
    return askSize;
  }

  public BigDecimal getBidSize() {
    return bidSize;
  }

  @Override
  public String toString() {

    return "UpbitOrderBook{"
        + "ask_price="
        + askPrice
        + ", ask_size="
        + askSize
        + ", bid_price="
        + bidPrice
        + ", bid_size="
        + bidSize
        + "}";
  }
}
