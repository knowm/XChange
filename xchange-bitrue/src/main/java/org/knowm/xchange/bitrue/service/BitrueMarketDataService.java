package org.knowm.xchange.bitrue.service;

import org.knowm.xchange.bitrue.BitrueAdapters;
import org.knowm.xchange.bitrue.BitrueAuthenticated;
import org.knowm.xchange.bitrue.BitrueErrorAdapter;
import org.knowm.xchange.bitrue.BitrueExchange;
import org.knowm.xchange.bitrue.dto.BitrueException;
import org.knowm.xchange.bitrue.dto.marketdata.BitrueAggTrades;
import org.knowm.xchange.bitrue.dto.marketdata.BitrueOrderbook;
import org.knowm.xchange.bitrue.dto.marketdata.BitrueTicker24h;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BitrueMarketDataService extends BitrueMarketDataServiceRaw
    implements MarketDataService {

  public BitrueMarketDataService(
      BitrueExchange exchange,
      BitrueAuthenticated bitrue,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, bitrue, resilienceRegistries);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair pair, Object... args) throws IOException {
    try {
      int limitDepth = 100;

      if (args != null && args.length == 1) {
        Object arg0 = args[0];
        if (!(arg0 instanceof Integer)) {
          throw new ExchangeException("Argument 0 must be an Integer!");
        } else {
          limitDepth = (Integer) arg0;
        }
      }
      BitrueOrderbook bitrueOrderBook = getBitrueOrderbook(pair, limitDepth);
      return convertOrderBook(bitrueOrderBook, pair);
    } catch (BitrueException e) {
      throw BitrueErrorAdapter.adapt(e);
    }
  }

  public static OrderBook convertOrderBook(BitrueOrderbook ob, CurrencyPair pair) {
    List<LimitOrder> bids =
        ob.bids.entrySet().stream()
            .map(e -> new LimitOrder(OrderType.BID, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    List<LimitOrder> asks =
        ob.asks.entrySet().stream()
            .map(e -> new LimitOrder(OrderType.ASK, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    return new OrderBook(null, asks, bids);
  }

  @Override
  public Ticker getTicker(CurrencyPair pair, Object... args) throws IOException {
    try {
      List<BitrueTicker24h> lst = ticker24h(pair);
      return lst!=null && lst.size() > 0 ? lst.get(0).toTicker(): null;
    } catch (BitrueException e) {
      throw BitrueErrorAdapter.adapt(e);
    }
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
    try {
      Long fromId = tradesArgument(args, 0, Long::valueOf);
      Long startTime = tradesArgument(args, 1, Long::valueOf);
      Long endTime = tradesArgument(args, 2, Long::valueOf);
      Integer limit = tradesArgument(args, 3, Integer::valueOf);
      List<BitrueAggTrades> aggTrades =
          bitrue.aggTrades(BitrueAdapters.toSymbol(pair), fromId, startTime, endTime, limit);
      List<Trade> trades =
          aggTrades.stream()
              .map(
                  at ->
                      new Trade.Builder()
                          .type(BitrueAdapters.convertType(at.buyerMaker))
                          .originalAmount(at.quantity)
                          .currencyPair(pair)
                          .price(at.price)
                          .timestamp(at.getTimestamp())
                          .id(Long.toString(at.aggregateTradeId))
                          .build())
              .collect(Collectors.toList());
      return new Trades(trades, TradeSortType.SortByTimestamp);
    } catch (BitrueException e) {
      throw BitrueErrorAdapter.adapt(e);
    }
  }

  private <T extends Number> T tradesArgument(
      Object[] args, int index, Function<String, T> converter) {
    if (index >= args.length) {
      return null;
    }
    Object arg = args[index];
    if (arg == null) {
      return null;
    }
    String argStr = arg.toString();
    try {
      return converter.apply(argStr);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException(
          "Argument on index " + index + " is not a number: " + argStr, e);
    }
  }

}
