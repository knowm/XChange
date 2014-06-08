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
package com.xeiam.xchange.vaultofsatoshi.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.service.polling.PollingMarketDataService;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiAdapters;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VaultOfSatoshiDepth;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VaultOfSatoshiTrade;

/**
 * <p>
 * Implementation of the market data service for VaultOfSatoshi
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VaultOfSatoshiMarketDataService extends VaultOfSatoshiMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public VaultOfSatoshiMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    // Adapt to XChange DTOs
    return VaultOfSatoshiAdapters.adaptTicker(getVosTicker(currencyPair), currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    VaultOfSatoshiDepth vaultOfSatoshiDepth = getVosOrderBook(currencyPair);

    // Adapt to XChange DTOs
    List<LimitOrder> asks = VaultOfSatoshiAdapters.adaptOrders(vaultOfSatoshiDepth.getAsks(), currencyPair, "ask", "");
    List<LimitOrder> bids = VaultOfSatoshiAdapters.adaptOrders(vaultOfSatoshiDepth.getBids(), currencyPair, "bid", "");

    return new OrderBook(null, asks, bids);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    // Request data
    List<VaultOfSatoshiTrade> virtExTrades = getVosTrades(currencyPair);

    // Adapt to XChange DTOs
    return VaultOfSatoshiAdapters.adaptTrades(virtExTrades, currencyPair);
  }

}
