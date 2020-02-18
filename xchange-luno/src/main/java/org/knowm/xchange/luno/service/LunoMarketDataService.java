package org.knowm.xchange.luno.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.marketdata.Trades.TradeSortType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.luno.LunoUtil;
import org.knowm.xchange.luno.dto.marketdata.LunoOrderBook;
import org.knowm.xchange.luno.dto.marketdata.LunoTicker;
import org.knowm.xchange.luno.dto.marketdata.LunoTrades;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class LunoMarketDataService extends LunoBaseService implements MarketDataService {

  public LunoMarketDataService(Exchange exchange) {
    super(exchange);
  }

  private static List<LimitOrder> convert(
      Map<BigDecimal, BigDecimal> map, CurrencyPair currencyPair, OrderType type) {
    List<LimitOrder> result = new ArrayList<>();
    for (Entry<BigDecimal, BigDecimal> e : map.entrySet()) {
      result.add(new LimitOrder(type, e.getValue(), currencyPair, null, null, e.getKey()));
    }
    return result;
  }

  @Override
  public Ticker getTicker(CurrencyPair currencyPair, Object... args) throws IOException {
    LunoTicker t = lunoAPI.ticker(LunoUtil.toLunoPair(currencyPair));
    return new Ticker.Builder()
        .currencyPair(currencyPair)
        .ask(t.ask)
        .bid(t.bid)
        .last(t.lastTrade)
        .timestamp(t.getTimestamp())
        .volume(t.rolling24HourVolume)
        .build();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    LunoOrderBook ob = lunoAPI.orderbook(LunoUtil.toLunoPair(currencyPair));
    return new OrderBook(
        ob.getTimestamp(),
        convert(ob.getAsks(), currencyPair, OrderType.ASK),
        convert(ob.getBids(), currencyPair, OrderType.BID));
  }

  @Override
  public Trades getTrades(CurrencyPair currencyPair, Object... args) throws IOException {
    Long since = null;
    if (args != null && args.length >= 1) {
      Object arg0 = args[0];
      if (arg0 instanceof Long) {
        since = (Long) arg0;
      } else if (arg0 instanceof Date) {
        since = ((Date) arg0).getTime();
      } else {
        throw new ExchangeException("args[0] must be of type Long or Date!");
      }
    }

    LunoTrades lunoTrades = lunoAPI.trades(LunoUtil.toLunoPair(currencyPair), since);
    List<Trade> list = new ArrayList<>();
    for (org.knowm.xchange.luno.dto.marketdata.LunoTrades.Trade lt : lunoTrades.getTrades()) {
      list.add(
          new Trade.Builder()
              .type(lt.buy ? OrderType.BID : OrderType.ASK)
              .originalAmount(lt.volume)
              .currencyPair(currencyPair)
              .price(lt.price)
              .timestamp(lt.getTimestamp())
              .build());
    }
    return new Trades(list, TradeSortType.SortByTimestamp);
  }
}
