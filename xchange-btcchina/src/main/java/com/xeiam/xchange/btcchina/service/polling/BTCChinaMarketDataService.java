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
package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaDepth;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author ObsessiveOrange
 *         <p>
 *         Implementation of the market data service for BTCChina
 *         </p>
 *         <ul>
 *         <li>Provides access to various market data values</li>
 *         </ul>
 */
public class BTCChinaMarketDataService extends BTCChinaMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BTCChinaMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    BTCChinaTicker btcChinaTicker = getBTCChinaTicker();

    // Adapt to XChange DTOs
    return BTCChinaAdapters.adaptTicker(btcChinaTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    BTCChinaDepth btcChinaDepth = getBTCChinaOrderBook();

    // Adapt to XChange DTOs
    List<LimitOrder> asks = BTCChinaAdapters.adaptOrders(btcChinaDepth.getAsks(), currencyPair, OrderType.ASK);
    List<LimitOrder> bids = BTCChinaAdapters.adaptOrders(btcChinaDepth.getBids(), currencyPair, OrderType.BID);

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    BTCChinaTrade[] btcChinaTrades = null;

    if (args.length == 0) {
      btcChinaTrades = getBTCChinaTrades();
    }
    else if (args.length == 1) {
      Object arg0 = args[0];

      if (arg0 instanceof Integer) {
        Integer sinceTransactionID = (Integer) args[0];
        btcChinaTrades = getBTCChinaTrades(sinceTransactionID);
      }
      else {
        throw new ExchangeException("args[0] must be of type Integer!");
      }
    }

    else {
      throw new ExchangeException("Invalid argument length. Must be 0, or 1");
    }

    // Adapt to XChange DTOs
    return BTCChinaAdapters.adaptTrades(btcChinaTrades, currencyPair);
  }

}
