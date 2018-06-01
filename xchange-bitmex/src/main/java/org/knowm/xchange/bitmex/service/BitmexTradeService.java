package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BitmexTradeService extends BitmexTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    List<BitmexPrivateOrder> bitmexOrders = super.getBitmexOrders();

    List<LimitOrder> limitOrders = new ArrayList<>();

    for (BitmexPrivateOrder order : bitmexOrders) {
      if (order.getOrderStatus() == BitmexPrivateOrder.OrderStatus.Filled
          || order.getOrderStatus() == BitmexPrivateOrder.OrderStatus.Canceled) {
        continue;
      }

      Order.OrderType type =
          order.getSide() == BitmexSide.BUY ? Order.OrderType.BID : Order.OrderType.ASK;
      CurrencyPair pair = new CurrencyPair(order.getCurrency(), order.getSettleCurrency());

      LimitOrder limitOrder =
          new LimitOrder(
              type, order.getVolume(), pair, order.getId(), order.getTimestamp(), order.getPrice());
      limitOrders.add(limitOrder);
    }

    return new OpenOrders(limitOrders);
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws IOException {
    List<LimitOrder> limitOrders = new ArrayList<>();

    for (LimitOrder order : getOpenOrders().getOpenOrders()) {
      if (params.accept(order)) {
        limitOrders.add(order);
      }
    }

    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    String symbol =
        marketOrder.getCurrencyPair().base.getCurrencyCode()
            + marketOrder.getCurrencyPair().counter.getCurrencyCode();
    BitmexPrivateOrder order = placeMarketOrder(symbol, marketOrder.getOriginalAmount(), null);
    return order.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    String symbol =
        limitOrder.getCurrencyPair().base.getCurrencyCode()
            + limitOrder.getCurrencyPair().counter.getCurrencyCode();
    BitmexPrivateOrder order =
        placeLimitOrder(symbol, limitOrder.getOriginalAmount(), limitOrder.getLimitPrice(), null);
    return order.getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    String symbol =
        stopOrder.getCurrencyPair().base.getCurrencyCode()
            + stopOrder.getCurrencyPair().counter.getCurrencyCode();
    BitmexPrivateOrder order =
        placeStopOrder(symbol, stopOrder.getOriginalAmount(), stopOrder.getStopPrice(), null);
    return order.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    return cancelBitmexOrder(orderId);
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    String filter = "{\"orderID\": [\"" + String.join("\",\"", orderIds) + "\"]}";

    List<BitmexPrivateOrder> privateOrders = getBitmexOrders(null, filter);

    Set<Order> orders = new HashSet<>();

    for (BitmexPrivateOrder privateOrder : privateOrders) {
      Order.OrderType type =
          privateOrder.getSide() == BitmexSide.BUY ? Order.OrderType.BID : Order.OrderType.ASK;
      Order.OrderStatus status = BitmexAdapters.adaptOrderStatus(privateOrder.getOrderStatus());
      CurrencyPair pair =
          new CurrencyPair(privateOrder.getCurrency(), privateOrder.getSettleCurrency());

      orders.add(
          new LimitOrder(
              type,
              privateOrder.getVolume(),
              pair,
              privateOrder.getId(),
              privateOrder.getTimestamp(),
              privateOrder.getPrice(),
              null,
              null,
              null,
              status));
    }

    return orders;
  }
}
