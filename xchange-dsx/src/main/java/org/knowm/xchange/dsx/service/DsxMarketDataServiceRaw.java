package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DsxAdapters;
import org.knowm.xchange.dsx.dto.*;

public class DsxMarketDataServiceRaw extends DsxBaseService {

  public DsxMarketDataServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<DsxSymbol> getDsxSymbols() throws IOException {

    return dsx.getSymbols();
  }

  public List<DsxCurrency> getDsxCurrencies() throws IOException {

    return dsx.getCurrencies();
  }

  public DsxCurrency getDsxCurrency(String currency) throws IOException {

    return dsx.getCurrency(currency);
  }

  public Map<String, DsxTicker> getDsxTickers() throws IOException {

    return dsx.getDsxTickers().stream()
        .collect(Collectors.toMap(DsxTicker::getSymbol, dsxTicker -> dsxTicker));
  }

  public DsxTicker getDsxTicker(CurrencyPair currencyPair) throws IOException {

    return dsx.getTicker(DsxAdapters.adaptCurrencyPair(currencyPair));
  }

  public DsxOrderBook getDsxOrderBook(CurrencyPair currencyPair) throws IOException {

    return dsx.getOrderBook(DsxAdapters.adaptCurrencyPair(currencyPair), null);
  }

  public DsxOrderBook getDsxOrderBook(CurrencyPair currencyPair, Integer limit) throws IOException {

    return dsx.getOrderBook(DsxAdapters.adaptCurrencyPair(currencyPair), limit);
  }

  public Map<String, List<DsxTrade>> getDsxTrades(
      DsxSort sortDirection,
      DsxTradesSortBy sortBy,
      Long from,
      Long till,
      Integer maxResults,
      Integer offset)
      throws IOException {

    return dsx.getTrades(sortDirection, sortBy, from, till, maxResults, offset);
  }

  public List<DsxTrade> getDsxTrades(
      CurrencyPair currencyPair,
      DsxSort sortDirection,
      DsxTradesSortBy sortBy,
      Long from,
      Long till,
      Integer maxResults,
      Integer offset)
      throws IOException {

    return dsx.getTrades(
        DsxAdapters.adaptCurrencyPair(currencyPair),
        sortDirection,
        sortBy,
        from,
        till,
        maxResults,
        offset);
  }

  public List<DsxCandle> getDsxCandles(CurrencyPair currencyPair, int limit, String period)
      throws IOException {

    return dsx.getDsxOHLC(DsxAdapters.adaptCurrencyPair(currencyPair), limit, period);
  }

  public List<DsxCandle> getDsxCandles(
      CurrencyPair currencyPair, int limit, String period, String sort) throws IOException {

    return dsx.getDsxOHLC(DsxAdapters.adaptCurrencyPair(currencyPair), limit, period, sort);
  }

  public List<DsxCandle> getDsxCandles(
      CurrencyPair currencyPair, int limit, String period, Date from, Date till, String sort)
      throws IOException {

    return dsx.getDsxOHLC(
        DsxAdapters.adaptCurrencyPair(currencyPair), limit, period, from, till, sort);
  }

  public List<DsxCandle> getDsxCandles(
      CurrencyPair currencyPair, int limit, String period, int offset, String sort)
      throws IOException {

    return dsx.getDsxOHLC(DsxAdapters.adaptCurrencyPair(currencyPair), limit, period, offset, sort);
  }
}
