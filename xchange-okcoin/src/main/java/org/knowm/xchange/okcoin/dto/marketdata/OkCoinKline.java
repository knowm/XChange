package org.knowm.xchange.okcoin.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

public class OkCoinKline {
  protected Date date;
  protected BigDecimal open;
  protected BigDecimal height;
  protected BigDecimal low;
  protected BigDecimal close;
  protected BigDecimal amount;

  public OkCoinKline(Object[] kline) {
    this.date = new Date((long) kline[0]);
    this.open = new BigDecimal(String.valueOf(kline[1]));
    this.height = new BigDecimal(String.valueOf(kline[2]));
    this.low = new BigDecimal(String.valueOf(kline[3]));
    this.close = new BigDecimal(String.valueOf(kline[4]));
    this.amount = new BigDecimal(String.valueOf(kline[5]));
  }

  public OkCoinKline() {}

  public Date getDate() {
    return date;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getHeight() {
    return height;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  @Override
  public String toString() {
    return "OkCoinKline{"
        + "date="
        + date
        + ", open="
        + open
        + ", height="
        + height
        + ", low="
        + low
        + ", close="
        + close
        + ", amount="
        + amount
        + '}';
  }
}
