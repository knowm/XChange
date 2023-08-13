package org.knowm.xchange.binance.dto.marketdata;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;

import lombok.Getter;
import org.knowm.xchange.instrument.Instrument;

@Getter
public final class BinanceKline {

  private final Instrument instrument;
  private final KlineInterval interval;
  private final long openTime;
  private final BigDecimal open;
  private final BigDecimal high;
  private final BigDecimal low;
  private final BigDecimal close;
  private final BigDecimal volume;
  private final long closeTime;
  private final BigDecimal quoteAssetVolume;
  private final long numberOfTrades;
  private final BigDecimal takerBuyBaseAssetVolume;
  private final BigDecimal takerBuyQuoteAssetVolume;
  private final boolean closed;

  public BinanceKline(Instrument instrument, KlineInterval interval, Object[] obj) {
    this.instrument = instrument;
    this.interval = interval;
    this.openTime = Long.parseLong(obj[0].toString());
    this.open = new BigDecimal(obj[1].toString());
    this.high = new BigDecimal(obj[2].toString());
    this.low = new BigDecimal(obj[3].toString());
    this.close = new BigDecimal(obj[4].toString());
    this.volume = new BigDecimal(obj[5].toString());
    this.closeTime = Long.parseLong(obj[6].toString());
    this.quoteAssetVolume = new BigDecimal(obj[7].toString());
    this.numberOfTrades = Long.parseLong(obj[8].toString());
    this.takerBuyBaseAssetVolume = new BigDecimal(obj[9].toString());
    this.takerBuyQuoteAssetVolume = new BigDecimal(obj[10].toString());
    this.closed = (Boolean)obj[11];
  }

  public BigDecimal getAveragePrice() {
    return low.add(high).divide(new BigDecimal("2"), MathContext.DECIMAL32);
  }

  public String toString() {
    String tstamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(openTime);
    return String.format(
        "[%s] %s %s O:%.6f A:%.6f C:%.6f", instrument, tstamp, interval, open, getAveragePrice(), close);
  }
}
