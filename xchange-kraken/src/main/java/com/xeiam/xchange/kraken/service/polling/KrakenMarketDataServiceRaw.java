package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Map;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepth;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicTrades;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenSpreads;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTicker;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenDepthResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenPublicTradesResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenSpreadsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenTickerResult;

public class KrakenMarketDataServiceRaw extends KrakenBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
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

  public KrakenSpreads getKrakenSpreads(Currency tradableIdentifier, Currency currency) throws IOException {

    return getKrakenSpreads(tradableIdentifier, currency, null);
  }

  private KrakenSpreads getKrakenSpreads(Currency tradableIdentifier, Currency currency, Long since) throws IOException {

    String krakenCurrencyPair = createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenSpreadsResult spreadsResult = kraken.getSpread(krakenCurrencyPair, since);

    return checkResult(spreadsResult);
  }
}
