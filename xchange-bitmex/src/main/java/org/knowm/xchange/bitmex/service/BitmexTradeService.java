package org.knowm.xchange.bitmex.service;

import static org.knowm.xchange.bitmex.dto.trade.BitmexSide.fromOrderType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.knowm.xchange.bitmex.BitmexAdapters;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexPlaceOrderParameters;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.StopOrder;
import org.knowm.xchange.exceptions.ExchangeException;
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
  public OpenOrders getOpenOrders() throws ExchangeException {

    List<BitmexPrivateOrder> bitmexOrders =
        super.getBitmexOrders(null, "{\"open\": true}", null, null, null);

    return new OpenOrders(
        bitmexOrders.stream().map(BitmexAdapters::adaptOrder).collect(Collectors.toList()));
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
    String symbol = BitmexAdapters.adaptCurrencyPairToSymbol(marketOrder.getCurrencyPair());

    return placeOrder(
            new BitmexPlaceOrderParameters.Builder(symbol)
                .setSide(fromOrderType(marketOrder.getType()))
                .setOrderQuantity(marketOrder.getOriginalAmount())
                .build())
        .getId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException {
    String symbol = BitmexAdapters.adaptCurrencyPairToSymbol(limitOrder.getCurrencyPair());

    return placeOrder(
            new BitmexPlaceOrderParameters.Builder(symbol)
                .setOrderQuantity(limitOrder.getOriginalAmount())
                .setPrice(limitOrder.getLimitPrice())
                .setSide(fromOrderType(limitOrder.getType()))
                .setClOrdId(limitOrder.getId())
                .build())
        .getId();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) throws ExchangeException {
    String symbol = BitmexAdapters.adaptCurrencyPairToSymbol(stopOrder.getCurrencyPair());

    return placeOrder(
            new BitmexPlaceOrderParameters.Builder(symbol)
                .setSide(fromOrderType(stopOrder.getType()))
                .setOrderQuantity(stopOrder.getOriginalAmount())
                .setStopPrice(stopOrder.getStopPrice())
                .setClOrdId(stopOrder.getId())
                .build())
        .getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException {
    List<BitmexPrivateOrder> orders = cancelBitmexOrder(orderId);

    if (orders.isEmpty()) {
      return true;
    }
    return orders.get(0).getId().equals(orderId);
  }

  public boolean cancelOrder(CancelOrderParams params) throws ExchangeException {

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
  public Collection<Order> getOrder(String... orderIds) throws ExchangeException {

    String filter = "{\"orderID\": [\"" + String.join("\",\"", orderIds) + "\"]}";

    List<BitmexPrivateOrder> privateOrders = getBitmexOrders(null, filter, null, null, null);
    return privateOrders.stream().map(BitmexAdapters::adaptOrder).collect(Collectors.toList());
  }
}
