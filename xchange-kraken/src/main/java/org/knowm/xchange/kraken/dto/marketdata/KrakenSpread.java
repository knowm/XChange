package org.knowm.xchange.kraken.dto.marketdata;

import java.math.BigDecimal;

public class KrakenSpread {

  private final long time;
  private final BigDecimal bid;
  private final BigDecimal ask;

  public KrakenSpread(long time, BigDecimal bid, BigDecimal ask) {

    this.time = time;
    this.bid = bid;
    this.ask = ask;
  }

  public long getTime() {

    return time;
  }

  public BigDecimal getBid() {

    return bid;
  }

  public BigDecimal getAsk() {

    return ask;
  }

  @Override
  public String toString() {

    return "KrakenSpread [time=" + time + ", bid=" + bid + ", ask=" + ask + "]";
  }
}
