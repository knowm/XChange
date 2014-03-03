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
package com.xeiam.xchange.bitcoinium.service.polling;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.bitcoinium.BitcoiniumAdapters;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import com.xeiam.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * <p>
 * Implementation of the generic market data service for Bitcoinium
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class BitcoiniumMarketDataService extends BitcoiniumMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public BitcoiniumMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    String exchange = "";

    if (args.length == 1) {
      Object arg = args[0];
      if (!(arg instanceof String)) {
        throw new ExchangeException("Exchange argument must be a String!");
      }
      else {
        exchange = (String) arg;
      }
    }
    else {
      throw new ExchangeException("One and only one String argument is allowed!");
    }

    // Request data
    BitcoiniumTicker bitcoiniumTicker = getBitcoiniumTicker(currencyPair.baseCurrency, currencyPair.counterCurrency, exchange);

    // Adapt to XChange DTOs
    return BitcoiniumAdapters.adaptTicker(bitcoiniumTicker, currencyPair);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    String exchange = "";
    String priceWindow = "";

    if (args.length == 2) {
      Object arg0 = args[0];
      if (!(arg0 instanceof String)) {
        throw new ExchangeException("Exchange argument must be a String!");
      }
      else {
        exchange = (String) arg0;
      }
      Object arg1 = args[1];
      if (!(arg1 instanceof String)) {
        throw new ExchangeException("priceWindow argument must be a String!");
      }
      else {
        priceWindow = (String) arg1;
      }
    }
    else {
      throw new ExchangeException("Exactly 2 String arguments are necessary: exchange, and priceWindow!");
    }

    // Request data
    BitcoiniumOrderbook bitcoiniumOrderbook = getBitcoiniumOrderbook(currencyPair.baseCurrency, currencyPair.counterCurrency, exchange, priceWindow);

    // Adapt to XChange DTOs
    return BitcoiniumAdapters.adaptOrderbook(bitcoiniumOrderbook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public ExchangeInfo getExchangeInfo() throws IOException {

    throw new NotAvailableFromExchangeException();
  }

}
