package org.knowm.xchange.binance.dto.marketdata;

import java.math.BigDecimal;

/**
 * Created by cyril on 10-Oct-17.
 */
public class BinanceOrderBookEntry {
  private BigDecimal price;
  private BigDecimal quantity;

  public BinanceOrderBookEntry(BigDecimal price, BigDecimal quantity) {
    this.price = price;
    this.quantity = quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public void setQuantity(BigDecimal quantity) {
    this.quantity = quantity;
  }

  @Override
  public String toString() {
    return "BinanceOrderBookEntry{" +
        "price=" + price +
        ", quantity=" + quantity +
        '}';
  }
}
