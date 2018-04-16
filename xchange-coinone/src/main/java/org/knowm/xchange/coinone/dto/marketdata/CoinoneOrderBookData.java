package org.knowm.xchange.coinone.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/** @author interwater */
public class CoinoneOrderBookData {

  private final BigDecimal price;
  private final BigDecimal qty;

  /**
   * @param price
   * @param qty
   */
  public CoinoneOrderBookData(
      @JsonProperty("price") String price, @JsonProperty("qty") String qty) {
    this.price = new BigDecimal(price);
    this.qty = new BigDecimal(qty);
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getQty() {
    return qty;
  }

  @Override
  public String toString() {

    return "CoinoneOrderBook{" + "price=" + price + ", qty=" + qty + "}";
  }
}
