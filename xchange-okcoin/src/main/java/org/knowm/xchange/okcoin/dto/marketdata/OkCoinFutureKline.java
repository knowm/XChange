package org.knowm.xchange.okcoin.dto.marketdata;

import java.math.BigDecimal;
import java.util.Date;

public class OkCoinFutureKline {
  protected Date closeTime;
  protected BigDecimal close;
  protected BigDecimal high;
  protected BigDecimal low;
  protected BigDecimal open;
  protected BigDecimal volume;
  protected BigDecimal quoteAssetVolume;

  public OkCoinFutureKline() {}

  public OkCoinFutureKline(Object[] kline) {
    if (kline[0] instanceof Long) {
      this.closeTime = new Date((Long) kline[0]);
    } else {
      this.closeTime = new Date(Long.valueOf((String) kline[0]));
    }
    this.open = new BigDecimal(String.valueOf(kline[1]));
    this.high = new BigDecimal(String.valueOf(kline[2]));
    this.low = new BigDecimal(String.valueOf(kline[3]));
    this.close = new BigDecimal(String.valueOf(kline[4]));
    this.volume = new BigDecimal(String.valueOf(kline[5]));
    this.quoteAssetVolume = new BigDecimal(String.valueOf(kline[6]));
  }

  public Date getCloseTime() {
    return closeTime;
  }

  public BigDecimal getClose() {
    return close;
  }

  public BigDecimal getHigh() {
    return high;
  }

  public BigDecimal getLow() {
    return low;
  }

  public BigDecimal getOpen() {
    return open;
  }

  public BigDecimal getVolume() {
    return volume;
  }

  public BigDecimal getQuoteAssetVolume() {
    return quoteAssetVolume;
  }
}
