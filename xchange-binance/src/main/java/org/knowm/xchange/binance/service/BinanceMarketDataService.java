package org.knowm.xchange.binance.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.marketdata.BinanceAggTrades;
import org.knowm.xchange.binance.dto.marketdata.BinanceOrderbook;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

public class BinanceMarketDataService extends BinanceMarketDataServiceRaw
    implements MarketDataService {

  public BinanceMarketDataService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair pair, Object... args) throws IOException {

    int limitDepth = 100;

    if (args != null && args.length == 1) {
      Object arg0 = args[0];
      if (!(arg0 instanceof Integer)) {
        throw new ExchangeException("Argument 0 must be an Integer!");
      } else {
        limitDepth = (Integer) arg0;
      }
    }
    BinanceOrderbook binanceOrderbook = getBinanceOrderbook(pair, limitDepth);
    return convertOrderBook(binanceOrderbook, pair);
  }

  public static OrderBook convertOrderBook(BinanceOrderbook ob, CurrencyPair pair) {
    List<LimitOrder> bids =
        ob.bids
            .entrySet()
            .stream()
            .map(e -> new LimitOrder(OrderType.BID, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    List<LimitOrder> asks =
        ob.asks
            .entrySet()
            .stream()
            .map(e -> new LimitOrder(OrderType.ASK, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    return new OrderBook(null, asks, bids);
  }

  @Override
  public Ticker getTicker(CurrencyPair pair, Object... args) throws IOException {

    return ticker24h(pair).toTicker();
  }

  @Override
  public List<Ticker> getTickers(Params params) throws IOException {
    return ticker24h().stream().map(BinanceTicker24h::toTicker).collect(Collectors.toList());
  }

  /**
   * optional parameters provided in the args array:
   *
   * <ul>
   *   <li>0: Long fromId optional, ID to get aggregate trades from INCLUSIVE.
   *   <li>1: Long startTime optional, Timestamp in ms to get aggregate trades from INCLUSIVE.
   *   <li>2: Long endTime optional, Timestamp in ms to get aggregate trades until INCLUSIVE.
   *   <li>3: Integer limit optional, Default 500; max 500.
   * </ul>
   *
   * <p>
   */
  @Override
  public Trades getTrades(CurrencyPair pair, Object... args) throws IOException {

    Long fromId = null;
    Long startTime = null;
    Long endTime = null;
    Integer limit = null;

    try {
      if (args[0] != null) fromId = Long.valueOf(args[0].toString());
    } catch (Throwable ignored) {
    }
    try {
      if (args[1] != null) startTime = Long.valueOf(args[1].toString());
    } catch (Throwable ignored) {
    }
    try {
      if (args[2] != null) endTime = Long.valueOf(args[2].toString());
    } catch (Throwable ignored) {
    }
    try {
      if (args[3] != null) limit = Integer.valueOf(args[3].toString());
    } catch (Throwable ignored) {
    }

    List<BinanceAggTrades> aggTrades =
        binance.aggTrades(BinanceAdapters.toSymbol(pair), fromId, startTime, endTime, limit);
    List<Trade> trades =
        aggTrades
            .stream()
            .map(
                at ->
                    new Trade(
                        BinanceAdapters.convertType(at.buyerMaker),
                        at.quantity,
                        pair,
                        at.price,
                        at.getTimestamp(),
                        Long.toString(at.aggregateTradeId)))
            .collect(Collectors.toList());
    return new Trades(trades, TradeSortType.SortByTimestamp);
  }
}
