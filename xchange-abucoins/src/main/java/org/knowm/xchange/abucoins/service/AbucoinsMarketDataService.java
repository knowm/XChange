package org.knowm.xchange.abucoins.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.AbucoinsAdapters;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsDepth;
import org.knowm.xchange.abucoins.dto.marketdata.AbucoinsTrade;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Author: bryant_harris
 */
public class AbucoinsMarketDataService extends AbucoinsMarketDataServiceRaw implements MarketDataService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      return AbucoinsAdapters.adaptTicker(getAbucoinsTicker(currencyPair), currencyPair);
    }
    catch (Exception e) {
      throw new IOException("Unable to get ticker for " + currencyPair, e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
/*
    AbucoinsDepth AbucoinsDepth = getAbucoinsOrderBook(currencyPair);

    if (AbucoinsDepth.getError() != null) {
      //eg: 'Rate limit exceeded'
      throw new ExchangeException("Abucoins getOrderBook request for " + currencyPair + " failed with: " + AbucoinsDepth.getError());
    }

    return AbucoinsAdapters.adaptOrderBook(AbucoinsDepth, currencyPair);
    */
          return null;
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
          return null;
          /*
    AbucoinsTrade[] trades;

    if (args != null && args.length > 0) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Number)) {
        throw new ExchangeException("arg[0] must be a Number used to represent since trade id.");
      } else {
        trades = getAbucoinsTrades(currencyPair, ((Number) arg0).longValue());
      }
    } else { // default to full available trade history
      trades = getAbucoinsTrades(currencyPair, null);
    }

    return AbucoinsAdapters.adaptTrades(trades, currencyPair);
    */
  }

}
