package org.knowm.xchange.bitbns.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbns.BitbnsAdapters;
import org.knowm.xchange.bitbns.dto.BitbnsLimitOrder;
import org.knowm.xchange.bitbns.dto.BitbnsOrderPlaceStatusResponse;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public class BitbnsTradeService extends BitbnsTradeServiceRaw implements TradeService {

  public BitbnsTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    BitbnsOrderPlaceStatusResponse newOrder = placeCoindcxLimitOrder(limitOrder);

    // The return value contains details of any trades that have been immediately executed as a
    // result
    // of this order. Make these available to the application if it has provided a GeminiLimitOrder.
    if (limitOrder instanceof BitbnsLimitOrder) {
      BitbnsLimitOrder raw = (BitbnsLimitOrder) limitOrder;
      raw.setResponse(newOrder);
    }

    return String.valueOf(newOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    //    return cancelGeminiOrder(orderId);
    return false;
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {
    if (orderParams instanceof CancelOrderByIdParams) {
      return cancelOrder(((CancelOrderByIdParams) orderParams).getOrderId());
    } else {
      return false;
    }
  }

  @Override
  public Collection<Order> getOrder(String... orderIds) throws IOException {

    Collection<Order> orders = new ArrayList<>(orderIds.length);

    //    for (String orderId : orderIds) {
    orders.add(
        BitbnsAdapters.adaptOrder(super.getOrderStatus(orderIds[0], orderIds[1]), orderIds[1]));
    //    }

    return orders;
  }
}
