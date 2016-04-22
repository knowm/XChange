package org.knowm.xchange.huobi.dto.streaming.response.payload;

import java.math.BigDecimal;

import org.knowm.xchange.huobi.dto.streaming.response.marketdata.payload.MarketDetailPayload;

/**
 * Trades for {@link MarketDetailPayload} and {@link ReqMarketDetailPayload}.
 */
public class Trades {
  private final BigDecimal[] price;
  private final long[] time;
  private final BigDecimal[] amount;
  private final int[] direction;

  public Trades(BigDecimal[] price, long[] time, BigDecimal[] amount, int[] direction) {
    super();
    this.price = price;
    this.time = time;
    this.amount = amount;
    this.direction = direction;
  }

  public BigDecimal[] getPrice() {
    return price;
  }

  public long[] getTime() {
    return time;
  }

  public BigDecimal[] getAmount() {
    return amount;
  }

  public int[] getDirection() {
    return direction;
  }

}
