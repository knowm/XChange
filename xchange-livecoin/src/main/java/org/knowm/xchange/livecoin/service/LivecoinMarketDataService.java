package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.livecoin.Livecoin;
import org.knowm.xchange.livecoin.LivecoinAdapters;
import org.knowm.xchange.livecoin.LivecoinErrorAdapter;
import org.knowm.xchange.livecoin.LivecoinExchange;
import org.knowm.xchange.livecoin.dto.LivecoinException;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinOrderBook;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTicker;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinTrade;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public class LivecoinMarketDataService extends LivecoinMarketDataServiceRaw
    implements MarketDataService {

  public LivecoinMarketDataService(
      LivecoinExchange exchange, Livecoin livecoin, ResilienceRegistries resilienceRegistries) {
    super(exchange, livecoin, resilienceRegistries);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      LivecoinTicker ticker = getTickerRaw(currencyPair);
      return LivecoinAdapters.adaptTicker(ticker, currencyPair);
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    try {
      final List<CurrencyPair> currencyPairs = new ArrayList<>();
      if (params instanceof CurrencyPairsParam) {
        currencyPairs.addAll(((CurrencyPairsParam) params).getCurrencyPairs());
      }
      return getTickersRaw().stream()
          .map(
              livecoinTicker ->
                  LivecoinAdapters.adaptTicker(
                      livecoinTicker,
                      CurrencyPairDeserializer.getCurrencyPairFromString(
                          livecoinTicker.getSymbol())))
          .filter(
              ticker ->
                  currencyPairs.size() == 0 || currencyPairs.contains(ticker.getCurrencyPair()))
          .collect(Collectors.toList());
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      int depth = 50;
      if (args != null && args.length > 0) {
        if (args[0] instanceof Number) {
          Number arg = (Number) args[0];
          depth = arg.intValue();
        }
      }
      LivecoinOrderBook orderBook = getOrderBookRaw(currencyPair, depth, Boolean.TRUE);
      return LivecoinAdapters.adaptOrderBook(orderBook, currencyPair);
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    try {
      List<LivecoinTrade> rawTrades = getTradesRaw(currencyPair);
      return LivecoinAdapters.adaptTrades(rawTrades, currencyPair);
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }
}
