/*
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
package com.xeiam.xchange.bitfloor.service.marketdata.polling;

import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitfloor.Bitfloor;
import com.xeiam.xchange.bitfloor.BitfloorAdapters;
import com.xeiam.xchange.bitfloor.BitfloorUtils;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorDayInfo;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorOrderBook;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorTicker;
import com.xeiam.xchange.bitfloor.dto.marketdata.BitfloorTransaction;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;
import com.xeiam.xchange.service.streaming.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author Matija Mazi
 */
public class BitfloorPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  private final Bitfloor bitfloor;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BitfloorPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitfloor = RestProxyFactory.createProxy(Bitfloor.class, exchangeSpecification.getUri());
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);
    BitfloorTicker bitfloorTicker = bitfloor.getTicker();
    BitfloorDayInfo dayInfo = bitfloor.getDayInfo();

    return BitfloorAdapters.adaptTicker(bitfloorTicker, dayInfo, tradableIdentifier, currency);
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    BitfloorTransaction[] transactions = bitfloor.getTransactions();

    return BitfloorAdapters.adaptTrades(transactions, tradableIdentifier, currency);

  }

  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

    verify(tradableIdentifier, currency);

    BitfloorOrderBook bitfloorOrderBook = bitfloor.getOrderBook();

    return BitfloorAdapters.adaptOrders(bitfloorOrderBook, tradableIdentifier, currency);

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
    Assert.isTrue(BitfloorUtils.isValidCurrencyPair(new CurrencyPair(tradableIdentifier, currency)), "currencyPair is not valid:" + tradableIdentifier + " " + currency);
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    return BitfloorUtils.CURRENCY_PAIRS;
  }

}
