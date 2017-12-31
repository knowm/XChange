package org.knowm.xchange.binance.dto.marketdata;

import java.math.BigDecimal;

public final class BinanceKline {

  public final long openTime;
  public final BigDecimal open;
  public final BigDecimal high;
  public final BigDecimal low;
  public final BigDecimal close;
  public final BigDecimal volume;
  public final long closeTime;
  public final BigDecimal quoteAssetVolume;
  public final long numberOfTrades;
  public final BigDecimal takerBuyBaseAssetVolume;
  public final BigDecimal takerBuyQuoteAssetVolume;

  public BinanceKline(Object[] obj) {
    openTime = Long.valueOf(obj[0].toString());
    open = new BigDecimal(obj[1].toString());
    high = new BigDecimal(obj[2].toString());
    low = new BigDecimal(obj[3].toString());
    close = new BigDecimal(obj[4].toString());
    volume = new BigDecimal(obj[5].toString());
    closeTime = Long.valueOf(obj[6].toString());
    quoteAssetVolume = new BigDecimal(obj[7].toString());
    numberOfTrades = Long.valueOf(obj[8].toString());
    takerBuyBaseAssetVolume = new BigDecimal(obj[9].toString());
    takerBuyQuoteAssetVolume = new BigDecimal(obj[10].toString());
  }
}
