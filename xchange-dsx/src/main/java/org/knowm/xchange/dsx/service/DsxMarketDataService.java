package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DsxAdapters;
import org.knowm.xchange.dsx.dto.DsxSort;
import org.knowm.xchange.dsx.dto.DsxTrade;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class DsxMarketDataService extends DsxMarketDataServiceRaw implements MarketDataService {

  public DsxMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {

    return DsxAdapters.adaptTicker(getDsxTicker(currencyPair), currencyPair);
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return DsxAdapters.adaptTickers(getDsxTickers());
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    if (args == null || args.length == 0) {
      return DsxAdapters.adaptOrderBook(getDsxOrderBook(currencyPair), currencyPair);
    } else {
      Integer limit = (Integer) args[0];
      return DsxAdapters.adaptOrderBook(getDsxOrderBook(currencyPair, limit), currencyPair);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    if (args == null || args.length == 0) {
      return DsxAdapters.adaptTrades(getDsxTrades(currencyPair), currencyPair);
    }

    long from = (Long) args[0]; // <trade_id> or <timestamp>
    DsxTrade.DsxTradesSortField sortBy =
        (DsxTrade.DsxTradesSortField) args[1]; // "trade_id" or "timestamp"
    DsxSort sortDirection = (DsxSort) args[2]; // "asc" or "desc"
    long startIndex = (Long) args[3]; // 0
    long max_results = (Long) args[4]; // max is 1000
    long offset = (Long) args[5]; // max is 100000

    return DsxAdapters.adaptTrades(
        getDsxTrades(currencyPair, from, sortBy, sortDirection, startIndex, max_results, offset),
        currencyPair);
  }

  public Trades getTradesCustom(
      CurrencyPair currencyPair,
      long fromTradeId,
      DsxTrade.DsxTradesSortField sortBy,
      DsxSort sortDirection,
      long limit)
      throws IOException {

    return DsxAdapters.adaptTrades(
        getDsxTrades(currencyPair, fromTradeId, sortBy, sortDirection, limit), currencyPair);
  }
}
