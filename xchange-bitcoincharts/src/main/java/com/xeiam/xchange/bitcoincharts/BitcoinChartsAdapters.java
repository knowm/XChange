package com.xeiam.xchange.bitcoincharts;

import java.math.BigDecimal;
import java.util.Date;

import com.xeiam.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;

/**
 * Various adapters for converting from BitcoinCharts DTOs to XChange DTOs
 */
public final class BitcoinChartsAdapters {

  /**
   * private Constructor
   */
  private BitcoinChartsAdapters() {

  }

  /**
   * Adapts a BitcoinChartsTicker[] to a Ticker Object
   * 
   * @param bitcoinChartsTickers
   * @param tradableIdentifier
   * @return
   */
  public static Ticker adaptTicker(BitcoinChartsTicker[] bitcoinChartsTickers, CurrencyPair currencyPair) {

    for (int i = 0; i < bitcoinChartsTickers.length; i++) {
      if (bitcoinChartsTickers[i].getSymbol().equals(currencyPair.counterSymbol)) {

        BigDecimal last = bitcoinChartsTickers[i].getClose() != null ? bitcoinChartsTickers[i].getClose() : null;
        BigDecimal bid = bitcoinChartsTickers[i].getBid() != null ? bitcoinChartsTickers[i].getBid() : null;
        BigDecimal ask = bitcoinChartsTickers[i].getAsk() != null ? bitcoinChartsTickers[i].getAsk() : null;
        BigDecimal high = bitcoinChartsTickers[i].getHigh() != null ? bitcoinChartsTickers[i].getHigh() : null;
        BigDecimal low = bitcoinChartsTickers[i].getLow() != null ? bitcoinChartsTickers[i].getLow() : null;
        BigDecimal volume = bitcoinChartsTickers[i].getVolume();
        Date timeStamp = new Date(bitcoinChartsTickers[i].getLatestTrade() * 1000L);

        return TickerBuilder.newInstance().withCurrencyPair(currencyPair).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timeStamp).build();

      }
    }
    return null;
  }

}
