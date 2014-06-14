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
package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.kraken.Kraken;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepth;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicTrades;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenSpreads;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTicker;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenDepthResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenPublicTradesResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenSpreadsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenTickerResult;

public class KrakenMarketDataServiceRaw extends KrakenBasePollingService<Kraken> {

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public KrakenMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(Kraken.class, exchangeSpecification);
  }

  public KrakenTicker getKrakenTicker(CurrencyPair currencyPair) throws IOException {

    String krakenCurrencyPair = createKrakenCurrencyPair(currencyPair);
    KrakenTickerResult tickerResult = kraken.getTicker(krakenCurrencyPair);

    return checkResult(tickerResult).get(krakenCurrencyPair);
  }

  public Map<String, KrakenTicker> getKrakenTicker(CurrencyPair... currencyPairs) throws IOException {

    KrakenTickerResult tickerResult = kraken.getTicker(delimitAssetPairs(currencyPairs));

    return checkResult(tickerResult);
  }

  public KrakenDepth getKrakenDepth(CurrencyPair currencyPair, long count) throws IOException {

    String krakenCurrencyPair = createKrakenCurrencyPair(currencyPair);
    KrakenDepthResult result = kraken.getDepth(krakenCurrencyPair, count);

    return checkResult(result).get(krakenCurrencyPair);
  }

  public KrakenPublicTrades getKrakenTrades(CurrencyPair currencyPair) throws IOException {

    return getKrakenTrades(currencyPair, null);
  }

  public KrakenPublicTrades getKrakenTrades(CurrencyPair currencyPair, Long since) throws IOException {

    String krakenCurrencyPair = createKrakenCurrencyPair(currencyPair);
    KrakenPublicTradesResult result = kraken.getTrades(krakenCurrencyPair, since);

    return checkResult(result);
  }

  public KrakenSpreads getKrakenSpreads(String tradableIdentifier, String currency) throws IOException {

    return getKrakenSpreads(tradableIdentifier, currency, null);
  }

  private KrakenSpreads getKrakenSpreads(String tradableIdentifier, String currency, Long since) throws IOException {

    String krakenCurrencyPair = createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenSpreadsResult spreadsResult = kraken.getSpread(krakenCurrencyPair, since);

    return checkResult(spreadsResult);
  }
}
