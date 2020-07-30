package org.knowm.xchange.dsx.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DsxAdapters;
import org.knowm.xchange.dsx.dto.DsxSort;
import org.knowm.xchange.dsx.dto.DsxTrade;
import org.knowm.xchange.dsx.dto.DsxTradesSortBy;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;
import org.knowm.xchange.utils.ArrayUtils;

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
      Integer limit = ArrayUtils.getElement(0, args, Integer.class);
      return DsxAdapters.adaptOrderBook(getDsxOrderBook(currencyPair, limit), currencyPair);
    }
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {

    Long from = ArrayUtils.getElement(0, args, Long.class); // <trade_id> or <timestamp>
    Long till = ArrayUtils.getElement(1, args, Long.class); // <trade_id> or <timestamp>
    DsxTradesSortBy sortBy =
        ArrayUtils.getElement(
            2, args, DsxTradesSortBy.class, DsxTradesSortBy.timestamp); // "id" or "timestamp"
    DsxSort sortDirection = ArrayUtils.getElement(3, args, DsxSort.class); // "ASC" or "DESC"
    Integer maxResults = ArrayUtils.getElement(4, args, Integer.class); // max is 1000
    Integer offset = ArrayUtils.getElement(5, args, Integer.class); // max is 100000

    return DsxAdapters.adaptTrades(
        getDsxTrades(currencyPair, sortDirection, sortBy, from, till, maxResults, offset),
        currencyPair,
        sortBy);
  }

  public Map<String, Trades> getAllTrades(
      DsxSort sortDirection,
      DsxTradesSortBy sortBy,
      Long from,
      Long till,
      Integer maxResults,
      Integer offset)
      throws IOException {

    Map<String, List<DsxTrade>> dsxTrades =
        getDsxTrades(sortDirection, sortBy, from, till, maxResults, offset);
    HashMap<String, Trades> tradeMap = new HashMap<>();

    dsxTrades.entrySet().stream()
        .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
        .forEach(
            entry -> {
              String symbol = entry.getKey();
              List<DsxTrade> trades = entry.getValue();
              tradeMap.put(symbol, DsxAdapters.adaptTrades(trades, null, sortBy));
            });

    return tradeMap;
  }
}
