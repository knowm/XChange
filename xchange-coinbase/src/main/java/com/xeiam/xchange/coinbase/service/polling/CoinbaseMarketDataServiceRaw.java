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
import java.util.Map;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.Coinbase;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseCurrency;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseRate;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;

/**
 * @author jamespedwards42
 */
public class CoinbaseMarketDataServiceRaw extends BasePollingExchangeService {

  protected final Coinbase coinbase;

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CoinbaseMarketDataServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.coinbase = RestProxyFactory.createProxy(Coinbase.class, exchangeSpecification.getSslUri());
  }

  public List<CoinbaseCurrency> getCurrencies() throws IOException {

    return coinbase.getCurrencies();
  }

  public Map<String, BigDecimal> getCurrencyExchangeRates() throws IOException {

    return coinbase.getCurrencyExchangeRates();
  }

  public CoinbasePrice getBuyPrice() throws IOException {

    return getBuyPrice(null, null);
  }

  public CoinbasePrice getBuyPrice(BigDecimal quantity) throws IOException {

    return getBuyPrice(quantity, null);
  }

  public CoinbasePrice getBuyPrice(BigDecimal quantity, String currency) throws IOException {

    return coinbase.getBuyPrice(quantity, currency);
  }

  public CoinbasePrice getSellPrice() throws IOException {

    return getBuyPrice(null, null);
  }

  public CoinbasePrice getSellPrice(BigDecimal quantity) throws IOException {

    return getBuyPrice(quantity, null);
  }

  public CoinbasePrice getSellPrice(BigDecimal quantity, String currency) throws IOException {

    return coinbase.getBuyPrice(quantity, currency);
  }

  public CoinbaseRate getSpotRate(String currency) throws IOException {

    return coinbase.getSpotRate(currency);
  }

  public CoinbaseSpotPriceHistory getHistoricalSpotRates() throws IOException {

    return getHistoricalSpotRates(null);
  }

  public CoinbaseSpotPriceHistory getHistoricalSpotRates(Integer page) throws IOException {

    return coinbase.getHistoricalSpotRates(page);
  }
}
