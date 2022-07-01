package org.knowm.xchange.dragonex.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dragonex.dto.marketdata.Depth;
import org.knowm.xchange.dragonex.dto.marketdata.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class DragonexMarketDataService extends DragonexMarketDataServiceRaw
    implements MarketDataService {

  public DragonexMarketDataService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public Ticker getTicker(CurrencyPair pair, Object... args) throws IOException {
    org.knowm.xchange.dragonex.dto.marketdata.Ticker t =
        super.ticker(exchange.symbolId(pair)).get(0);
    return new Ticker.Builder()
        .currencyPair(pair)
        .volume(t.totalVolume)
        .quoteVolume(t.totalAmount)
        .high(t.maxPrice)
        .low(t.minPrice)
        .open(t.openPrice)
        .last(t.closePrice)
        .timestamp(new Date(t.timestamp * 1000))
        .build();
  }

  @Override
  public OrderBook getOrderBook(CurrencyPair pair, Object... args) throws IOException {
    long symbolId = exchange.symbolId(pair);
    BiFunction<OrderType, Order, LimitOrder> f =
        (t, o) -> new LimitOrder(t, o.volume, pair, null, null, o.price);
    Depth d = super.marketDepth(symbolId, 50); // currently the max count of orders: 50
    List<LimitOrder> bids =
        d.getBuys().stream().map(o -> f.apply(OrderType.BID, o)).collect(Collectors.toList());
    List<LimitOrder> asks =
        d.getSells().stream().map(o -> f.apply(OrderType.ASK, o)).collect(Collectors.toList());
    return new OrderBook(null, asks, bids);
  }
}
