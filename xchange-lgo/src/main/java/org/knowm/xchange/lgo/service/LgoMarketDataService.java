package org.knowm.xchange.lgo.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.lgo.LgoExchange;
import org.knowm.xchange.lgo.dto.marketdata.LgoOrderbook;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class LgoMarketDataService extends LgoMarketDataServiceRaw implements MarketDataService {

  public LgoMarketDataService(LgoExchange exchange) {
    super(exchange);
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair currencyPair, Object... args) throws IOException {
    LgoOrderbook orderBook = super.getOrderBook(currencyPair);
    return convertOrderBook(orderBook, currencyPair);
  }

  private static OrderBook convertOrderBook(LgoOrderbook ob, CurrencyPair pair) {
    List<LimitOrder> bids =
        ob.market.bids.entrySet().stream()
            .map(
                e ->
                    new LimitOrder(Order.OrderType.BID, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    List<LimitOrder> asks =
        ob.market.asks.entrySet().stream()
            .map(
                e ->
                    new LimitOrder(Order.OrderType.ASK, e.getValue(), pair, null, null, e.getKey()))
            .collect(Collectors.toList());
    return new OrderBook(null, asks, bids);
  }
}
