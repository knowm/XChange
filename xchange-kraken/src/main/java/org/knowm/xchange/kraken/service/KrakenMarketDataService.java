package org.knowm.xchange.kraken.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Candle;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.dto.marketdata.KrakenDepth;
import org.knowm.xchange.kraken.dto.marketdata.KrakenOHLC;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicTrades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class KrakenMarketDataService extends KrakenMarketDataServiceRaw
    implements MarketDataService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public KrakenMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return KrakenAdapters.adaptTicker(getKrakenTicker(currencyPair), currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    if (!(params instanceof CurrencyPairsParam)) {
      throw new IllegalArgumentException("Params must be instance of CurrencyPairsParam");
    }
    Collection<CurrencyPair> pairs = ((CurrencyPairsParam) params).getCurrencyPairs();
    CurrencyPair[] pair = pairs.toArray(new CurrencyPair[pairs.size()]);
    return KrakenAdapters.adaptTickers(getKrakenTickers(pair));
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {

    long count = Long.MAX_VALUE;

    if (args != null && args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof Long) {
        count = (Long) arg0;
      } else if (arg0 instanceof Integer) {
        count = (Integer) arg0;
      } else {
        throw new ExchangeException("args[0] must be of type Long or Integer");
      }
    }

    KrakenDepth krakenDepth = getKrakenDepth(currencyPair, count);

    return KrakenAdapters.adaptOrderBook(krakenDepth, currencyPair);
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Long since = null;

    if (args != null && args.length > 0) {
      Object arg0 = args[0];
      if (arg0 instanceof Long) {
        since = (Long) arg0;
      } else {
        throw new ExchangeException("args[0] must be of type Long!");
      }
    }

    KrakenPublicTrades krakenTrades = getKrakenTrades(currencyPair, since);
    return KrakenAdapters.adaptTrades(
        krakenTrades.getTrades(), currencyPair, krakenTrades.getLast());
  }

  @Override
  public List<Candle> getPriceHistory(
      CurrencyPair currencyPair, int minutes, int maxHistory, Object... args) throws IOException {
    int secs = minutes * 60; // 1 (default), 5, 15, 30, 60, 240, 1440, 10080, 21600
    Date timestamp = new Date();

    List<KrakenOHLC> ohlCs =
        getKrakenOHLC(
                currencyPair,
                minutes,
                null) // (System.currentTimeMillis() / 1_000L) - (secs * maxHistory))
            .getOHLCs();

    int skip = ohlCs.size() > maxHistory ? (ohlCs.size() - maxHistory) : 0;

    List<Candle> collect =
        ohlCs.stream()
            .skip(skip) // Kraken returns data in ascending order
            .map(
                ohlc ->
                    new Candle.Builder()
                        .openTime(new Date(ohlc.getTime() * 1_000L))
                        .granularity(secs)
                        .open(ohlc.getOpen())
                        .close(ohlc.getClose())
                        .high(ohlc.getHigh())
                        .low(ohlc.getLow())
                        .volume(ohlc.getVolume())
                        .vwap(ohlc.getVwap())
                        .count(ohlc.getCount())
                        .timestamp(timestamp)
                        .build())
            .collect(Collectors.toList());
    return collect;
  }
}
