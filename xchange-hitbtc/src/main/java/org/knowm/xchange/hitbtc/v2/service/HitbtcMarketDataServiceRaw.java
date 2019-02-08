package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.hitbtc.v2.HitbtcAdapters;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcCandle;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcCurrency;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSort;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTrade;

public class HitbtcMarketDataServiceRaw extends HitbtcBaseService {

  public HitbtcMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<HitbtcSymbol> getHitbtcSymbols() throws IOException {

    return hitbtc.getSymbols();
  }

  public List<HitbtcCurrency> getHitbtcCurrencies() throws IOException {

    return hitbtc.getCurrencies();
  }

  public HitbtcCurrency getHitbtcCurrency(String currency) throws IOException {

    return hitbtc.getCurrency(currency);
  }

  public Map<String, HitbtcTicker> getHitbtcTickers() throws IOException {

    return hitbtc.getHitbtcTickers().stream()
        .collect(
            Collectors.toMap(
                hitbtcTicker -> hitbtcTicker.getSymbol(), hitbtcTicker -> hitbtcTicker));
  }

  public HitbtcTicker getHitbtcTicker(CurrencyPair currencyPair) throws IOException {

    return hitbtc.getTicker(HitbtcAdapters.adaptCurrencyPair(currencyPair));
  }

  public HitbtcOrderBook getHitbtcOrderBook(CurrencyPair currencyPair) throws IOException {

    return hitbtc.getOrderBook(HitbtcAdapters.adaptCurrencyPair(currencyPair), null);
  }

  public HitbtcOrderBook getHitbtcOrderBook(CurrencyPair currencyPair, Integer limit)
      throws IOException {

    return hitbtc.getOrderBook(HitbtcAdapters.adaptCurrencyPair(currencyPair), limit);
  }

  public List<HitbtcTrade> getHitbtcTrades(CurrencyPair currencyPair) throws IOException {

    return getHitbtcTrades(
        currencyPair,
        100,
        HitbtcTrade.HitbtcTradesSortField.SORT_BY_TRADE_ID,
        HitbtcSort.SORT_ASCENDING,
        0,
        100,
        0);
  }

  // TODO add extra params in API
  public List<HitbtcTrade> getHitbtcTrades(
      CurrencyPair currencyPair,
      long from,
      HitbtcTrade.HitbtcTradesSortField sortBy,
      HitbtcSort sortDirection,
      long startIndex,
      long maxResults,
      long offset)
      throws IOException {

    return hitbtc.getTrades(HitbtcAdapters.adaptCurrencyPair(currencyPair), maxResults, offset);
  }

  public List<HitbtcTrade> getHitbtcTrades(
      CurrencyPair currencyPair,
      long from,
      HitbtcTrade.HitbtcTradesSortField sortBy,
      HitbtcSort sortDirection,
      long maxResults)
      throws IOException {

    return hitbtc.getTrades(
        HitbtcAdapters.adaptCurrencyPair(currencyPair),
        sortDirection.toString(),
        sortBy.toString(),
        String.valueOf(from),
        maxResults);
  }

  public List<HitbtcCandle> getHitbtcCandles(CurrencyPair currencyPair, int limit, String period)
      throws IOException {

    return hitbtc.getHitbtcOHLC(HitbtcAdapters.adaptCurrencyPair(currencyPair), limit, period);
  }

  public List<HitbtcCandle> getHitbtcCandles(
      CurrencyPair currencyPair, int limit, String period, String sort) throws IOException {

    return hitbtc.getHitbtcOHLC(
        HitbtcAdapters.adaptCurrencyPair(currencyPair), limit, period, sort);
  }

  public List<HitbtcCandle> getHitbtcCandles(
      CurrencyPair currencyPair, int limit, String period, Date from, Date till, String sort)
      throws IOException {

    return hitbtc.getHitbtcOHLC(
        HitbtcAdapters.adaptCurrencyPair(currencyPair), limit, period, from, till, sort);
  }

  public List<HitbtcCandle> getHitbtcCandles(
      CurrencyPair currencyPair, int limit, String period, int offset, String sort)
      throws IOException {

    return hitbtc.getHitbtcOHLC(
        HitbtcAdapters.adaptCurrencyPair(currencyPair), limit, period, offset, sort);
  }
}
