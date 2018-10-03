package org.knowm.xchange.coinbase.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

/** @author jamespedwards42 */
public class CoinbaseHistoricalSpotPrice implements Comparable<CoinbaseHistoricalSpotPrice> {

  private final Date timestamp;
  private final BigDecimal spotRate;

  CoinbaseHistoricalSpotPrice(Date timestamp, final BigDecimal spotRate) {

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

  @Override
  public int compareTo(CoinbaseHistoricalSpotPrice o) {

    return this.timestamp.compareTo(o.timestamp);
  }
}
