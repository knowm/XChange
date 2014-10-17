package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Map;

import si.mazi.rescu.SynchronizedValueFactory;

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
  public KrakenMarketDataServiceRaw(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(Kraken.class, exchangeSpecification, nonceFactory);
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
