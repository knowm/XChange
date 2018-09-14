package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelAllOrders;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.DefaultCancelOrderParamId;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;

public class BitmexTradeService extends BitmexTradeServiceRaw implements TradeService {

  public BitmexTradeService(BitmexExchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    List<BitmexPrivateOrder> bitmexOrders = super.getBitmexOrders(null, "{\"open\": true}");

    return new OpenOrders(
        bitmexOrders.stream().map(BitmexAdapters::adaptOrder).collect(Collectors.toList()));
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
    String symbol = BitmexAdapters.adaptCurrencyPairToSymbol(marketOrder.getCurrencyPair());
    BitmexSide side = getSide(marketOrder.getType());

    BitmexPrivateOrder order =
        placeMarketOrder(symbol, side, marketOrder.getOriginalAmount(), null);
    return order.getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {
    String symbol = BitmexAdapters.adaptCurrencyPairToSymbol(limitOrder.getCurrencyPair());
    BitmexSide side = getSide(limitOrder.getType());
    BitmexPrivateOrder order =
        placeLimitOrder(
            symbol,
            limitOrder.getOriginalAmount(),
            limitOrder.getLimitPrice(),
            side,
            limitOrder.getId(),
            null);
    return order.getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws IOException {
    String symbol = BitmexAdapters.adaptCurrencyPairToSymbol(stopOrder.getCurrencyPair());
    BitmexPrivateOrder order =
        placeStopOrder(
            symbol,
            getSide(stopOrder.getType()),
            stopOrder.getOriginalAmount(),
            stopOrder.getStopPrice(),
            null,
            stopOrder.getId(),
            null,
            null);
    return order.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    List<BitmexPrivateOrder> orders = cancelBitmexOrder(orderId);

    if (orders.isEmpty()) {
      return true;
    }
    return orders.get(0).getId().equals(orderId);
  }

  @Override
  public boolean cancelOrder(CancelOrderParams params) throws IOException {

    if (params instanceof DefaultCancelOrderParamId) {
      DefaultCancelOrderParamId paramsWithId = (DefaultCancelOrderParamId) params;
      return cancelOrder(paramsWithId.getOrderId());
    }

    if (params instanceof CancelAllOrders) {
      List<BitmexPrivateOrder> orders = cancelAllOrders();
      return !orders.isEmpty();
    }

    throw new NotYetImplementedForExchangeException(
        String.format("Unexpected type of parameter: %s", params));
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    String filter = "{\"orderID\": [\"" + String.join("\",\"", orderIds) + "\"]}";

    List<BitmexPrivateOrder> privateOrders = getBitmexOrders(null, filter);
    return privateOrders.stream().map(BitmexAdapters::adaptOrder).collect(Collectors.toList());
  }

  private static BitmexSide getSide(Order.OrderType type) {
    return type == null ? null : type == Order.OrderType.ASK ? BitmexSide.SELL : BitmexSide.BUY;
  }
}
