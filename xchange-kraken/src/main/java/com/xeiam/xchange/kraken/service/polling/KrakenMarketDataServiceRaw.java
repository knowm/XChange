package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.kraken.Kraken;
import com.xeiam.xchange.kraken.KrakenUtils;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssetPairs;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssets;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepth;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenServerTime;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenSpreads;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTicker;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicTrades;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenAssetPairsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenAssetsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenDepthResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenPublicTradesResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenServerTimeResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenSpreadsResult;
import com.xeiam.xchange.kraken.dto.marketdata.results.KrakenTickerResult;

public class KrakenMarketDataServiceRaw extends BaseKrakenService {

  private final Kraken kraken;

  public KrakenMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    kraken = RestProxyFactory.createProxy(Kraken.class, exchangeSpecification.getSslUri());
  }

  public KrakenServerTime getServerTime() throws IOException {

    KrakenServerTimeResult timeResult = kraken.getServerTime();

    return checkResult(timeResult);
  }

  public KrakenAssets getKrakenAssets() throws IOException {

    KrakenAssetsResult assetPairsResult = kraken.getAssets();

    return new KrakenAssets(checkResult(assetPairsResult));
  }
  
  public KrakenAssetPairs getKrakenAssetPairs() throws IOException {

    KrakenAssetPairsResult assetPairsResult = kraken.getAssetPairs();

    return new KrakenAssetPairs(checkResult(assetPairsResult));
  }

  public KrakenTicker getKrakenTicker(String tradableIdentifier, String currency) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenTickerResult tickerResult = kraken.getTicker(krakenCurrencyPair);

    return checkResult(tickerResult).get(krakenCurrencyPair);
  }

  public KrakenDepth getKrakenDepth(String tradableIdentifier, String currency, Object... args) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenDepthResult depthResult = null;
    if (args.length > 0) {
      Object arg = args[0];
      if (!(arg instanceof Long) || (Long) arg < 1) {
        throw new ExchangeException("Orderbook size argument must be an Long with a value greater than 0!");
      }
      else {
        depthResult = kraken.getDepth(krakenCurrencyPair, (Long) arg);
      }
    }
    else { // default to full orderbook
      depthResult = kraken.getDepth(krakenCurrencyPair, null);
    }

    return checkResult(depthResult).get(krakenCurrencyPair);
  }

  public KrakenPublicTrades getKrakenTrades(String tradableIdentifier, String currency, Object... args) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenPublicTradesResult tradesResult = null;
    if (args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof Long) {
        Long since = (Long) arg0;
        tradesResult = kraken.getTrades(krakenCurrencyPair, since);
      }
      else {
        throw new ExchangeException("args[0] must be of type Long!");
      }
    }
    else {
      tradesResult = kraken.getTrades(krakenCurrencyPair);
    }

    return checkResult(tradesResult);
  }
  
  public KrakenSpreads getKrakenSpreads(String tradableIdentifier, String currency) throws IOException {
    return getKrakenSpreads(tradableIdentifier, currency, 0);
  }
  
  public KrakenSpreads getKrakenSpreads(String tradableIdentifier, String currency, long since) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenSpreadsResult spreadsResult = kraken.getSpread(krakenCurrencyPair, since);

    return checkResult(spreadsResult);
  }
}
