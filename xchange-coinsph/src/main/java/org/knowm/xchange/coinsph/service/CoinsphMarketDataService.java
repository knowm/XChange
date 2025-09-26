package org.knowm.xchange.coinsph.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.coinsph.CoinsphAdapters;
import org.knowm.xchange.coinsph.CoinsphExchange;
import org.knowm.xchange.coinsph.dto.CoinsphException;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

// For getTickers

// import org.knowm.xchange.coinsph.dto.marketdata.CoinsphTicker; // May not be needed if
// CoinsphAdapters handles all

public class CoinsphMarketDataService extends CoinsphMarketDataServiceRaw
    implements MarketDataService {

  public CoinsphMarketDataService(
      CoinsphExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  @Override
  public Ticker getTicker(Instrument instrument, Object... args) throws IOException {
    return getTicker((CurrencyPair) instrument, args);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args)
      throws IOException, CoinsphException {
    org.knowm.xchange.coinsph.dto.marketdata.CoinsphTicker coinsphTicker =
        getCoinsphTicker(currencyPair);
    return CoinsphAdapters.adaptTicker(coinsphTicker);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException, CoinsphException {
    // Coins.ph API for all tickers doesn't take params, it returns all.
    List<org.knowm.xchange.coinsph.dto.marketdata.CoinsphTicker> coinsphTickers =
        getCoinsphTickers();
    return CoinsphAdapters.adaptTickers(coinsphTickers);
  }

  @Override
  public OrderBook getOrderBook(Instrument instrument, Object... args) throws IOException {
    return getOrderBook((CurrencyPair) instrument, args);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args)
      throws IOException, CoinsphException {
    Integer limit = null;
    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer) {
        limit = (Integer) args[0];
      } else if (args[0] != null) {
        throw new IllegalArgumentException(
            "Argument 0 for getOrderBook must be an Integer (limit) or null");
      }
    }
    org.knowm.xchange.coinsph.dto.marketdata.CoinsphOrderBook coinsphOrderBook =
        getCoinsphOrderBook(currencyPair, limit);
    return CoinsphAdapters.adaptOrderBook(coinsphOrderBook, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args)
      throws IOException, CoinsphException {
    Integer limit = null;
    if (args != null && args.length > 0) {
      if (args[0] instanceof Integer) {
        limit = (Integer) args[0];
      } else if (args[0] != null) {
        throw new IllegalArgumentException(
            "Argument 0 for getTrades must be an Integer (limit) or null for Coins.ph public trades");
      }
    }
    List<org.knowm.xchange.coinsph.dto.marketdata.CoinsphPublicTrade> coinsphTrades =
        getCoinsphTrades(currencyPair, limit);
    return CoinsphAdapters.adaptTrades(coinsphTrades, currencyPair);
  }
}
