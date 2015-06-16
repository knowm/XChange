/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.huobi.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.huobi.Huobi;
import com.xeiam.xchange.huobi.HuobiMarketTrade;
import com.xeiam.xchange.huobi.dto.marketdata.*;
import com.xeiam.xchange.huobi.service.HuobiBaseService;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.Assert;

public class HuobiMarketDataServiceRaw extends HuobiBaseService {

  private final Huobi huobi;
  private final HuobiMarketTrade huobiMarketTrade;

  protected HuobiMarketDataServiceRaw(Exchange exchange) {

    super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");
    this.huobi = RestProxyFactory.createProxy(Huobi.class, exchange.getExchangeSpecification().getSslUri());
    this.huobiMarketTrade = RestProxyFactory.createProxy(HuobiMarketTrade.class, exchange.getExchangeSpecification().getHost());
  }

  public HuobiTickerWrapper getHuobiTicker(CurrencyPair currencyPair) throws IOException {

    HuobiTickerWrapper huobiTicker = huobi.getTicker(currencyPair.baseSymbol.toLowerCase());
    return huobiTicker;
  }

  public HuobiDepthWrapper getHuobiOrderBook(CurrencyPair currencyPair) throws IOException {

    HuobiDepthWrapper HuobiDepthWrapper = huobi.getDepth(currencyPair.baseSymbol.toLowerCase());
    return HuobiDepthWrapper;
  }

  public HuobiFullTrade[] getHuobiTrades(CurrencyPair currencyPair, Long sincetid) throws IOException {

      HuobiFullTrade[] trades = null;

      if (sincetid == null) {
          trades = huobiMarketTrade.getLastTrades("trades");
      } else {
          trades = huobiMarketTrade.getTradesSince("trades", sincetid);
      }

      return trades;
  }
}
