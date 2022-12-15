package org.knowm.xchange.bitcoincharts;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

/** Various adapters for converting from BitcoinCharts DTOs to XChange DTOs */
public final class BitcoinChartsAdapters {

  /** private Constructor */
  private BitcoinChartsAdapters() {}

  /**
   * Adapts a BitcoinChartsTicker[] to a Ticker Object
   *
   * @param bitcoinChartsTickers
   * @return
   */
  public static Ticker adaptTicker(
      BitcoinChartsTicker[] bitcoinChartsTickers, CurrencyPair currencyPair) {

    for (BitcoinChartsTicker bitcoinChartsTicker : bitcoinChartsTickers) {
      if (bitcoinChartsTicker.getSymbol().equals(currencyPair.counter.getCurrencyCode())) {

        BigDecimal last =
                bitcoinChartsTicker.getClose() != null ? bitcoinChartsTicker.getClose() : null;
        BigDecimal bid =
                bitcoinChartsTicker.getBid() != null ? bitcoinChartsTicker.getBid() : null;
        BigDecimal ask =
                bitcoinChartsTicker.getAsk() != null ? bitcoinChartsTicker.getAsk() : null;
        BigDecimal high =
                bitcoinChartsTicker.getHigh() != null ? bitcoinChartsTicker.getHigh() : null;
        BigDecimal low =
                bitcoinChartsTicker.getLow() != null ? bitcoinChartsTicker.getLow() : null;
        BigDecimal volume = bitcoinChartsTicker.getVolume();
        Date timeStamp = new Date(bitcoinChartsTicker.getLatestTrade() * 1000L);

        return new Ticker.Builder()
                .currencyPair(currencyPair)
                .instrument(currencyPair)
                .last(last)
                .bid(bid)
                .ask(ask)
                .high(high)
                .low(low)
                .volume(volume)
                .timestamp(timeStamp)
                .build();
      }
    }
    return null;
  }

  public static ExchangeMetaData adaptMetaData(
      ExchangeMetaData exchangeMetaData, BitcoinChartsTicker[] tickers) {

    Map<Instrument, InstrumentMetaData> pairs = new HashMap<>();

    for (BitcoinChartsTicker ticker : tickers) {
      BigDecimal anyPrice =
          firstNonNull(
              ticker.getAsk(),
              ticker.getBid(),
              ticker.getClose(),
              ticker.getHigh(),
              ticker.getHigh());
      int scale = anyPrice != null ? anyPrice.scale() : 0;
      pairs.put(
          new CurrencyPair(Currency.BTC, Currency.getInstance(ticker.getSymbol())),
          new InstrumentMetaData.Builder().priceScale(scale).build());
    }

    return new ExchangeMetaData(
        pairs,
        exchangeMetaData.getCurrencies(),
        exchangeMetaData.getPublicRateLimits(),
        exchangeMetaData.getPrivateRateLimits(),
        exchangeMetaData.isShareRateLimits());
  }

  private static <T> T firstNonNull(T... objects) {

    for (T o : objects) {
      if (o != null) {
        return o;
      }
    }
    return null;
  }
}
