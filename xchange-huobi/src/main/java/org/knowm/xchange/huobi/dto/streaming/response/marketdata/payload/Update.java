package org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload;

import java.math.BigDecimal;

/**
 * Update of {@link MarketDepthDiffPayload} and {@link MarketDepthTopDiffPayload}.
 */
public class Update {

  private final BigDecimal[] price;
  private final BigDecimal[] amount;
  private final int[] row;

  public Update(BigDecimal[] price, BigDecimal[] amount, int[] row) {
    this.price = price;
    this.amount = amount;
    this.row = row;
  }

  public BigDecimal[] getPrice() {
    return price;
  }

  public BigDecimal[] getAmount() {
    return amount;
  }

  public int[] getRow() {
    return row;
  }

}
