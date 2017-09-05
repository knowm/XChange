package org.knowm.xchange.kraken.service;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kraken.KrakenUtils;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssetPairs;
import org.knowm.xchange.kraken.dto.marketdata.KrakenDepth;
import org.knowm.xchange.kraken.dto.marketdata.KrakenOHLCs;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicTrades;
import org.knowm.xchange.kraken.dto.marketdata.KrakenSpreads;
import org.knowm.xchange.kraken.dto.marketdata.KrakenTicker;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenAssetPairsResult;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenDepthResult;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenOHLCResult;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenPublicTradesResult;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenSpreadsResult;
import org.knowm.xchange.kraken.dto.marketdata.results.KrakenTickerResult;

public class KrakenMarketDataServiceRaw extends KrakenBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public KrakenOHLCs getKrakenOHLC(CurrencyPair currencyPair) throws IOException {
    return getKrakenOHLC(currencyPair, null, null);
  }

  public KrakenOHLCs getKrakenOHLC(CurrencyPair currencyPair, Integer interval, Long since) throws IOException {
    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(currencyPair);
    KrakenOHLCResult OHLCResult = kraken.getOHLC(krakenCurrencyPair, interval, since);
    return checkResult(OHLCResult);
  }

  public KrakenTicker getKrakenTicker(CurrencyPair currencyPair) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(currencyPair);
    KrakenTickerResult tickerResult = kraken.getTicker(krakenCurrencyPair);

    return checkResult(tickerResult).get(krakenCurrencyPair);
  }

  public Map<String, KrakenTicker> getKrakenTicker(CurrencyPair... currencyPairs) throws IOException {

    KrakenTickerResult tickerResult = kraken.getTicker(delimitAssetPairs(currencyPairs));

    return checkResult(tickerResult);
  }

  public KrakenDepth getKrakenDepth(CurrencyPair currencyPair, long count) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(currencyPair);
    KrakenDepthResult result = kraken.getDepth(krakenCurrencyPair, count);

    return checkResult(result).get(krakenCurrencyPair);
  }

  public KrakenPublicTrades getKrakenTrades(CurrencyPair currencyPair) throws IOException {

    return getKrakenTrades(currencyPair, null);
  }

  public KrakenPublicTrades getKrakenTrades(CurrencyPair currencyPair, Long since) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(currencyPair);
    KrakenPublicTradesResult result = kraken.getTrades(krakenCurrencyPair, since);

    return checkResult(result);
  }

  public KrakenSpreads getKrakenSpreads(Currency tradableIdentifier, Currency currency) throws IOException {

    return getKrakenSpreads(tradableIdentifier, currency, null);
  }

  private KrakenSpreads getKrakenSpreads(Currency tradableIdentifier, Currency currency, Long since) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenSpreadsResult spreadsResult = kraken.getSpread(krakenCurrencyPair, since);

    return checkResult(spreadsResult);
  }

  public KrakenAssetPairs getKrakenAssetPairs(CurrencyPair... currencyPairs) throws IOException {

    KrakenAssetPairsResult assetPairsResult = kraken.getAssetPairs(delimitAssetPairs(currencyPairs));

    return new KrakenAssetPairs(checkResult(assetPairsResult));
  }

}
