package org.knowm.xchange.coinone.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author interwater */
public class CoinoneTradeData {

  private final BigDecimal price;
  private final BigDecimal qty;
  private final String timestamp;

  /**
   * @param price
   * @param qty
   * @param timestamp
   */
  public CoinoneTradeData(
      @JsonProperty("price") String price,
      @JsonProperty("qty") String qty,
      @JsonProperty("timestamp") String timestamp) {
    this.price = new BigDecimal(price);
    this.qty = new BigDecimal(qty);
    this.timestamp = timestamp;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQty() {
    return qty;
  }

  public String getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {

    return "CoinoneOrderBook{" + "price=" + price + ", qty=" + qty + "}";
  }
}
