package com.xeiam.xchange.kraken.service.polling;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.kraken.dto.marketdata.KrakenTicker;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenDepth;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenPublicTrades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class KrakenMarketDataService extends KrakenMarketDataServiceRaw implements PollingMarketDataService {

  public KrakenMarketDataService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return KrakenAdapters.adaptTicker(getKrakenTicker(currencyPair), currencyPair);
  }

  public List<Ticker> getTickers() throws IOException {

    List<Ticker> tickers = new ArrayList<Ticker>();

    Collection<CurrencyPair> exchangeSymbols = getExchangeSymbols();
    Map<String, KrakenTicker> krakenTickerMap = getKrakenTicker(exchangeSymbols.toArray(new CurrencyPair[exchangeSymbols.size()]));

    for (Map.Entry<String, KrakenTicker> tickerEntry : krakenTickerMap.entrySet()) {

      String krakenTradeCurrency = tickerEntry.getKey().substring(0, 4);
      String krakenPriceCurrency = tickerEntry.getKey().substring(4);

      String tradeCurrency = KrakenAdapters.adaptCurrency(krakenTradeCurrency);
      String priceCurrency = KrakenAdapters.adaptCurrency(krakenPriceCurrency);

      CurrencyPair currencyPair = new CurrencyPair(tradeCurrency, priceCurrency);
      tickers.add(KrakenAdapters.adaptTicker(tickerEntry.getValue(), currencyPair));
    }

    return tickers;
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    long count = Long.MAX_VALUE;

    if (args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof Long) {
        count = (Long) arg0;
      }
      else {
        throw new ExchangeException("args[0] must be of type Long!");
      }
    }

    KrakenDepth krakenDepth = getKrakenDepth(currencyPair, count);

    return KrakenAdapters.adaptOrderBook(krakenDepth, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Long since = null;

    if (args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof Long) {
        since = (Long) arg0;
      }
      else {
        throw new ExchangeException("args[0] must be of type Long!");
      }
    }

    KrakenPublicTrades krakenTrades = getKrakenTrades(currencyPair, since);
    Trades trades = KrakenAdapters.adaptTrades(krakenTrades.getTrades(), currencyPair, krakenTrades.getLast());
    return trades;
  }

}
