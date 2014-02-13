package com.xeiam.xchange.coinbase.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

public class CoinbaseHistoricalSpotPrice {

  private final Date timestamp;
  private final BigDecimal spotRate;

  public CoinbaseHistoricalSpotPrice(final Date timestamp, final BigDecimal spotRate) {

    this.timestamp = timestamp;
    this.spotRate = spotRate;
  }

  public Date getTimestamp() {

    return timestamp;
  }

  public BigDecimal getSpotRate() {

    return spotRate;
  }

  @Override
  public String toString() {

    return "CoinbaseHistoricalPrice [timestamp=" + timestamp + ", spotRate=" + spotRate + "]";
  }

}
