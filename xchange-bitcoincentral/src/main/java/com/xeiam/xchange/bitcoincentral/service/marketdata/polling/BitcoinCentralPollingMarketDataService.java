/**
 * Copyright (C) 2013 Matija Mazi
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
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
package com.xeiam.xchange.bitcoincentral.service.marketdata.polling;

import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitcoincentral.BitcoinCentral;
import com.xeiam.xchange.bitcoincentral.BitcoinCentralAdapters;
import com.xeiam.xchange.bitcoincentral.BitcoinCentralUtils;
import com.xeiam.xchange.bitcoincentral.dto.marketdata.BitcoinCentralDepth;
import com.xeiam.xchange.bitcoincentral.dto.marketdata.BitcoinCentralTicker;
import com.xeiam.xchange.bitcoincentral.dto.marketdata.BitcoinCentralTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author Matija Mazi
 */
public class BitcoinCentralPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  private BitcoinCentral bitcoincentral;

  public BitcoinCentralPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitcoincentral = RestProxyFactory.createProxy(BitcoinCentral.class, exchangeSpecification.getUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return BitcoinCentralUtils.CURRENCY_PAIRS;
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    BitcoinCentralTicker bitcoinCentralTicker = bitcoincentral.getTicker(currency);
    return BitcoinCentralAdapters.adaptTicker(bitcoinCentralTicker, tradableIdentifier);
  }

  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    BitcoinCentralDepth bitcoinCentralDepth = bitcoincentral.getOrderBook(currency);

    return BitcoinCentralAdapters.adaptOrders(bitcoinCentralDepth, currency, tradableIdentifier);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);
    BitcoinCentralTrade[] bitcoinCentralTrades = bitcoincentral.getTrades(currency, 5);
    return BitcoinCentralAdapters.adaptTrades(bitcoinCentralTrades, currency, tradableIdentifier);
  }

  /**
   * Verify
   * 
   * @param tradableIdentifier
   * @param currency
   */
  private void verify(String tradableIdentifier, String currency) {

    Assert.notNull(tradableIdentifier, "tradableIdentifier cannot be null");
    Assert.notNull(currency, "currency cannot be null");
    Assert.isTrue(BitcoinCentralUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);
  }
}
