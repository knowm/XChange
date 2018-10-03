package org.knowm.xchange.coinmarketcap.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

/** @author allenday */
public class CoinMarketCapHistoricalSpotPrice
    implements Comparable<CoinMarketCapHistoricalSpotPrice> {

  private final Date timestamp;
  private final BigDecimal spotRate;

  CoinMarketCapHistoricalSpotPrice(Date timestamp, final BigDecimal spotRate) {
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
  public int compareTo(CoinMarketCapHistoricalSpotPrice o) {

    return this.timestamp.compareTo(o.timestamp);
  }
}
