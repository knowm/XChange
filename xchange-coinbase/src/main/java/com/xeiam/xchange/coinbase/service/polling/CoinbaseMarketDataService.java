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
package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.ExchangeInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Ticker.TickerBuilder;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author jamespedwards42
 */
public class CoinbaseMarketDataService extends CoinbaseMarketDataServiceRaw implements PollingMarketDataService {

  public CoinbaseMarketDataService(final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  @Override
  public Ticker getTicker(final String tradableIdentifier, final String currency, final Object... args) throws IOException {

    final CoinbasePrice buyPrice = super.getCoinbaseBuyPrice(BigDecimal.ONE, currency);
    final CoinbasePrice sellPrice = super.getCoinbaseSellPrice(BigDecimal.ONE, currency);

    final Ticker ticker = TickerBuilder.newInstance().withTradableIdentifier(tradableIdentifier).withAsk(buyPrice.getSubTotal()).withBid(sellPrice.getSubTotal()).build();
    return ticker;
  }

  @Override
  public OrderBook getOrderBook(final String tradableIdentifier, final String currency, final Object... args) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public ExchangeInfo getExchangeInfo() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(final String tradableIdentifier, final String currency, final Object... args) {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * Use {@link #getCoinbaseCurrencies()} instead.  It will provide
   * a list of all currencies that are currently supported by 
   * Coinbase.  
   */
  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    throw new NotAvailableFromExchangeException();
  }
}
