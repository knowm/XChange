package org.knowm.xchange.bitmex.service;

import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexPlaceOrderParameters;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

import java.util.*;

public class BitmexTradeService extends BitmexTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitmexTradeService(BitmexExchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException {

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
  public OpenOrders getOpenOrders(OpenOrdersParams params) throws ExchangeException {
    List<LimitOrder> limitOrders = new ArrayList<>();

    for (LimitOrder order : getOpenOrders().getOpenOrders()) {
      if (params.accept(order)) {
        limitOrders.add(order);
      }
    }

    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException {
    String symbol =
        marketOrder.getCurrencyPair().base.getCurrencyCode()
            + marketOrder.getCurrencyPair().counter.getCurrencyCode();

    return placeOrder(
            new BitmexPlaceOrderParameters.Builder(symbol)
                .setSide(getSide(marketOrder.getType()))
                .setOrderQuantity(marketOrder.getOriginalAmount())
                .build())
        .getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException {
    String symbol =
        limitOrder.getCurrencyPair().base.getCurrencyCode()
            + limitOrder.getCurrencyPair().counter.getCurrencyCode();

    return placeOrder(
            new BitmexPlaceOrderParameters.Builder(symbol)
                .setOrderQuantity(limitOrder.getOriginalAmount())
                .setPrice(limitOrder.getLimitPrice())
                .setSide(getSide(limitOrder.getType()))
                .setClOrdId(limitOrder.getId())
                .build())
        .getId();
  }

  private static BitmexSide getSide(Order.OrderType type) {
    return type == null ? null : type == Order.OrderType.ASK ? BitmexSide.SELL : BitmexSide.BUY;
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws ExchangeException {
    String symbol =
        stopOrder.getCurrencyPair().base.getCurrencyCode()
            + stopOrder.getCurrencyPair().counter.getCurrencyCode();

    return placeOrder(
            new BitmexPlaceOrderParameters.Builder(symbol)
                .setSide(getSide(stopOrder.getType()))
                .setOrderQuantity(stopOrder.getOriginalAmount())
                .setStopPrice(stopOrder.getStopPrice())
                .setClOrdId(stopOrder.getId())
                .build())
        .getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException {
    List<BitmexPrivateOrder> orders = cancelBitmexOrder(orderId);

    if (orders.isEmpty()) return true;
    return orders.get(0).getId().equals(orderId);
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws ExchangeException {

    String filter = "{\"orderID\": [\"" + String.join("\",\"", orderIds) + "\"]}";

    List<BitmexPrivateOrder> privateOrders = getBitmexOrders(null, filter, null, null, null);

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
              privateOrder.getAvgPx(),
              privateOrder.getCumQty(),
              null,
              status));
    }

    return orders;
  }
}
