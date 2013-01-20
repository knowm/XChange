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
package com.xeiam.xchange.bitcoincentral.service.marketdata.polling;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.CurrencyPair;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoincentral.BitcoinCentral;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.rest.RestProxyFactory;
import com.xeiam.xchange.service.BasePollingExchangeService;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;

/**
 * @author Matija Mazi
 */
public class BitcoinCentralPollingMarketDataService extends BasePollingExchangeService implements PollingMarketDataService {

  private static final Logger log = LoggerFactory.getLogger(BitcoinCentralPollingMarketDataService.class);

  private BitcoinCentral bitcoincentral;

  public BitcoinCentralPollingMarketDataService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitcoincentral = RestProxyFactory.createProxy(BitcoinCentral.class, exchangeSpecification.getUri());
  }

  @Override
  public List<CurrencyPair> getExchangeSymbols() {

    // todo
    return null;
  }

  @Override
  public Ticker getTicker(String tradableIdentifier, String currency) {

    Object result = bitcoincentral.getTicker(currency);
    log.debug("result ticker = {}", result);
    // todo: adapt to XChange dto
    return null;
  }

  @Override
  public OrderBook getPartialOrderBook(String tradableIdentifier, String currency) {

    // todo
    return null;
  }

  @Override
  public OrderBook getFullOrderBook(String tradableIdentifier, String currency) {

    Object orderBook = bitcoincentral.getOrderBook();
    log.debug("orderBook = {}", orderBook);
    // todo: adapt to XChange dto
    return null;
  }

  @Override
  public Trades getTrades(String tradableIdentifier, String currency) {

    // todo
    return null;
  }
}
