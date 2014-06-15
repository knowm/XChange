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

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.coinbase.CoinbaseAdapters;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseMoney;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author jamespedwards42
 */
public class CoinbaseMarketDataService extends CoinbaseMarketDataServiceRaw implements PollingMarketDataService {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CoinbaseMarketDataService(final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  /**
   * @param args Optional Boolean. If true an additional call to retrieve the spot price history will be made
   *          and used to populate the 24 hour high and low values for the Ticker.
   * @return A Ticker with Coinbase's current buy price as the best ask, sell price as the best bid,
   *         spot price as the last value, and can optionally use the spot price history to find the 24 hour
   *         high and low.
   */
  @Override
  public Ticker getTicker(final CurrencyPair currencyPair, final Object... args) throws IOException {

    final String currency = currencyPair.counterSymbol;
    final CoinbasePrice buyPrice = super.getCoinbaseBuyPrice(BigDecimal.ONE, currency);
    final CoinbasePrice sellPrice = super.getCoinbaseSellPrice(BigDecimal.ONE, currency);
    final CoinbaseMoney spotRate = super.getCoinbaseSpotRate(currency);

    final CoinbaseSpotPriceHistory coinbaseSpotPriceHistory =
        (args != null && args.length > 0 && args[0] != null && args[0] instanceof Boolean && (Boolean) args[0]) ? super.getCoinbaseHistoricalSpotRates() : null;

    return CoinbaseAdapters.adaptTicker(currencyPair, buyPrice, sellPrice, spotRate, coinbaseSpotPriceHistory);
  }

  @Override
  public OrderBook getOrderBook(final CurrencyPair currencyPair, final Object... args) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public Trades getTrades(final CurrencyPair currencyPair, final Object... args) {

    throw new NotAvailableFromExchangeException();
  }

}
