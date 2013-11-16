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
package com.xeiam.xchange.bitcoinaverage;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.money.BigMoney;

import com.xeiam.xchange.bitcoinaverage.dto.marketdata.BitcoinAverageTicker;
import com.xeiam.xchange.currency.MoneyUtils;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;

/**
 * Various adapters for converting from BitcoinAverage DTOs to XChange DTOs
 */
public final class BitcoinAverageAdapters {

  /**
   * private Constructor
   */
  private BitcoinAverageAdapters() {

  }

  /**
   * Adapts a BitcoinAverageTicker to a Ticker Object
   * 
   * @param bitcoinAverageTicker
   * @param currency
   * @param tradableIdentifier
   * @return Ticker
   */
  public static Ticker adaptTicker(BitcoinAverageTicker bitcoinAverageTicker, String currency, String tradableIdentifier) {

    BigMoney last = MoneyUtils.parse(currency + " " + bitcoinAverageTicker.getLast());
    BigMoney bid = MoneyUtils.parse(currency + " " + bitcoinAverageTicker.getBid());
    BigMoney ask = MoneyUtils.parse(currency + " " + bitcoinAverageTicker.getAsk());
    Date timestamp = bitcoinAverageTicker.getTimestamp();
    BigDecimal volume = bitcoinAverageTicker.getVolume();

    return TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withLast(last).withBid(bid).withAsk(ask).withVolume(volume).withTimestamp(timestamp).build();
  }

}
