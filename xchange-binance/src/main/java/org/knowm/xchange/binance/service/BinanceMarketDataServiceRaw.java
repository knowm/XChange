package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.marketdata.BinanceAggTrades;
import org.knowm.xchange.binance.dto.marketdata.BinanceKline;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.marketdata.BinancePrice;
import org.knowm.xchange.binance.dto.marketdata.BinancePriceQuantity;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.binance.dto.marketdata.KlineInterval;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.StreamUtils;

public class BinanceMarketDataServiceRaw extends BinanceBaseService {

  protected BinanceMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public void ping() throws IOException {
    binance.ping();
  }

  public BinanceOrderbook getBinanceOrderbook(CurrencyPair pair, Integer limit) throws IOException {
    return binance.depth(BinanceAdapters.toSymbol(pair), limit);
  }

  public List<BinanceAggTrades> aggTrades(
      CurrencyPair pair, Long fromId, Long startTime, Long endTime, Integer limit)
      throws IOException {
    return binance.aggTrades(BinanceAdapters.toSymbol(pair), fromId, startTime, endTime, limit);
  }

  public BinanceKline lastKline(CurrencyPair pair, KlineInterval interval) throws IOException {
    return klines(pair, interval, 1, null, null).stream().collect(StreamUtils.singletonCollector());
  }

  public List<BinanceKline> klines(CurrencyPair pair, KlineInterval interval) throws IOException {
    return klines(pair, interval, null, null, null);
  }

  public List<BinanceKline> klines(
      CurrencyPair pair, KlineInterval interval, Integer limit, Long startTime, Long endTime)
      throws IOException {
    List<Object[]> raw =
        binance.klines(BinanceAdapters.toSymbol(pair), interval.code(), limit, startTime, endTime);
    return raw.stream()
        .map(obj -> new BinanceKline(pair, interval, obj))
        .collect(Collectors.toList());
  }

  public List<BinanceTicker24h> ticker24h() throws IOException {
    List<BinanceTicker24h> binanceTicker24hList = binance.ticker24h();
    return binanceTicker24hList;
  }

  public BinanceTicker24h ticker24h(CurrencyPair pair) throws IOException {
    BinanceTicker24h ticker24h = binance.ticker24h(BinanceAdapters.toSymbol(pair));
    ticker24h.setCurrencyPair(pair);
    return ticker24h;
  }

  public BinancePrice tickerPrice(CurrencyPair pair) throws IOException {
    return tickerAllPrices()
        .stream()
        .filter(p -> p.getCurrencyPair().equals(pair))
        .collect(StreamUtils.singletonCollector());
  }

  public List<BinancePrice> tickerAllPrices() throws IOException {
    return binance.tickerAllPrices();
  }

  public List<BinancePriceQuantity> tickerAllBookTickers() throws IOException {
    return binance.tickerAllBookTickers();
  }
}
