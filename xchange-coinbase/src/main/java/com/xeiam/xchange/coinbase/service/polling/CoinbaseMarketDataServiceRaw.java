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
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.Coinbase;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbasePrice;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseSpotPriceHistory;

/**
 * @author jamespedwards42
 */
class CoinbaseMarketDataServiceRaw extends CoinbaseBaseService<Coinbase> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public CoinbaseMarketDataServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(Coinbase.class, exchangeSpecification);
  }
  
  public Map<String, BigDecimal> getCoinbaseCurrencyExchangeRates() throws IOException {

    return coinbase.getCurrencyExchangeRates();
  }

  public CoinbasePrice getCoinbaseBuyPrice() throws IOException {

    return getCoinbaseBuyPrice(null, null);
  }

  public CoinbasePrice getCoinbaseBuyPrice(BigDecimal quantity) throws IOException {

    return getCoinbaseBuyPrice(quantity, null);
  }

  public CoinbasePrice getCoinbaseBuyPrice(BigDecimal quantity, String currency) throws IOException {

    return coinbase.getBuyPrice(quantity, currency);
  }

  public CoinbasePrice getCoinbaseSellPrice() throws IOException {

    return getCoinbaseSellPrice(null, null);
  }

  public CoinbasePrice getCoinbaseSellPrice(BigDecimal quantity) throws IOException {

    return getCoinbaseSellPrice(quantity, null);
  }

  public CoinbasePrice getCoinbaseSellPrice(BigDecimal quantity, String currency) throws IOException {

    return coinbase.getSellPrice(quantity, currency);
  }

  public CoinbaseAmount getCoinbaseSpotRate(String currency) throws IOException {

    return coinbase.getSpotRate(currency);
  }

  public CoinbaseSpotPriceHistory getCoinbaseHistoricalSpotRates() throws IOException {

    return getCoinbaseHistoricalSpotRates(null);
  }

  public CoinbaseSpotPriceHistory getCoinbaseHistoricalSpotRates(Integer page) throws IOException {

    return coinbase.getHistoricalSpotRates(page);
  }
}
