/**
 * The MIT License
 * Copyright (c) 2012 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
