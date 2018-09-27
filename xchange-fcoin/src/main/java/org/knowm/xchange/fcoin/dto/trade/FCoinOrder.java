package org.knowm.xchange.fcoin.dto.trade;

import java.math.BigDecimal;

public class FCoinOrder {

  private String symbol;
  private FCoinSide side;
  private FCoinType type;
  private BigDecimal price;
  private BigDecimal amount;

  public FCoinOrder(
      String symbol, FCoinSide side, FCoinType type, BigDecimal price, BigDecimal amount) {
    this.symbol = symbol;
    this.side = side;
    this.type = type;
    this.price = price;
    this.amount = amount;
  }

  public String getSymbol() {
    return symbol;
  }

  public FCoinSide getSide() {
    return side;
  }

  public FCoinType getType() {
    return type;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getAmount() {
    return amount;
  }
}
