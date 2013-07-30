/**
 * Copyright (C) 2012 - 2013 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.bitcoincharts;

import com.xeiam.xchange.bitcoincharts.dto.charts.ChartData;
import java.math.BigDecimal;
import java.util.Date;

import org.joda.money.BigMoney;

import com.xeiam.xchange.bitcoincharts.dto.marketdata.BitcoinChartsTicker;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import java.util.ArrayList;

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
  public static Ticker adaptTicker(BitcoinChartsTicker[] bitcoinChartsTickers, String tradableIdentifier) {

    for (int i = 0; i < bitcoinChartsTickers.length; i++) {
      if (bitcoinChartsTickers[i].getSymbol().equals(tradableIdentifier)) {

        BigMoney last = bitcoinChartsTickers[i].getClose() != null ? MoneyUtils.parse(bitcoinChartsTickers[i].getCurrency() + " " + bitcoinChartsTickers[i].getClose()) : null;
        BigMoney bid = bitcoinChartsTickers[i].getBid() != null ? MoneyUtils.parse(bitcoinChartsTickers[i].getCurrency() + " " + bitcoinChartsTickers[i].getBid()) : null;
        BigMoney ask = bitcoinChartsTickers[i].getAsk() != null ? MoneyUtils.parse(bitcoinChartsTickers[i].getCurrency() + " " + bitcoinChartsTickers[i].getAsk()) : null;
        BigMoney high = bitcoinChartsTickers[i].getHigh() != null ? MoneyUtils.parse(bitcoinChartsTickers[i].getCurrency() + " " + bitcoinChartsTickers[i].getHigh()) : null;
        BigMoney low = bitcoinChartsTickers[i].getLow() != null ? MoneyUtils.parse(bitcoinChartsTickers[i].getCurrency() + " " + bitcoinChartsTickers[i].getLow()) : null;
        BigDecimal volume = bitcoinChartsTickers[i].getVolume();
        Date timeStamp = new Date(bitcoinChartsTickers[i].getLatestTrade() * 1000L);

        return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).withHigh(high).withLow(low).withVolume(volume).withTimestamp(timeStamp)
            .build();

      }
    }
    // TODO check on this logic returning null
    return null;

  }

  public static ChartData[] adaptChartData(ArrayList<ArrayList> pRawData) {
    ChartData[] ret = new ChartData[pRawData.size()];
    for (int i = 0; i < pRawData.size(); i++) {
      ArrayList cd = pRawData.get(i);
      ChartData chartData = new ChartData((ArrayList)cd);
      ret[i] = chartData;
    }
    return ret;
  }
}
