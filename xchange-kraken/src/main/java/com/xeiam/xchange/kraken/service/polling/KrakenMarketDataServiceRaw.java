package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.kraken.Kraken;
import com.xeiam.xchange.kraken.KrakenUtils;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssetPairsResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepth;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepthResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTicker;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTickerResult;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTrades;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenTradesResult;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

public class KrakenMarketDataServiceRaw extends BasePollingExchangeService {

  private final Kraken kraken;

  public KrakenMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    kraken = RestProxyFactory.createProxy(Kraken.class, exchangeSpecification.getSslUri());
  }

  public Set<String> getKrakenAssetPairs() throws IOException {

    KrakenAssetPairsResult krakenAssetPairs = kraken.getAssetPairs();
    if (krakenAssetPairs.getError().length > 0) {
      throw new ExchangeException(krakenAssetPairs.getError().toString());
    }
    return krakenAssetPairs.getResult().keySet();
  }

  public KrakenTicker getKrakenTicker(String tradableIdentifier, String currency) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenTickerResult krakenTickerResult = kraken.getTicker(krakenCurrencyPair);
    if (krakenTickerResult.getError().length > 0) {
      throw new ExchangeException(Arrays.toString(krakenTickerResult.getError()));
    }
    KrakenTicker krakenTicker = krakenTickerResult.getResult().get(krakenCurrencyPair);

    return krakenTicker;
  }

  public KrakenDepth getKrakenDepth(String tradableIdentifier, String currency, Object... args) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenDepthResult krakenDepthReturn = null;
    if (args.length > 0) {
      Object arg = args[0];
      if (!(arg instanceof Long) || (Long) arg < 1) {
        throw new ExchangeException("Orderbook size argument must be an Long with a value greater than 0!");
      }
      else {
        krakenDepthReturn = kraken.getDepth(krakenCurrencyPair, (Long) arg);
      }
    }
    else { // default to full orderbook
      krakenDepthReturn = kraken.getDepth(krakenCurrencyPair, null);
    }

    if (krakenDepthReturn.getError().length > 0) {
      throw new ExchangeException(Arrays.toString(krakenDepthReturn.getError()));
    }
    return krakenDepthReturn.getResult().get(krakenCurrencyPair);
  }

  public KrakenTrades getKrakenTrades(String tradableIdentifier, String currency, Object... args) throws IOException {

    String krakenCurrencyPair = KrakenUtils.createKrakenCurrencyPair(tradableIdentifier, currency);
    KrakenTradesResult krakenTrades = null;
    if (args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof Long) {
        Long since = (Long) arg0;
        krakenTrades = kraken.getTrades(krakenCurrencyPair, since);
      }
      else {
        throw new ExchangeException("args[0] must be of type Long!");
      }
    }
    else {
      krakenTrades = kraken.getTrades(krakenCurrencyPair);
    }

    if (krakenTrades.getError().length > 0) {
      throw new ExchangeException(Arrays.toString(krakenTrades.getError()));
    }
    return krakenTrades.getResult();
  }
}
