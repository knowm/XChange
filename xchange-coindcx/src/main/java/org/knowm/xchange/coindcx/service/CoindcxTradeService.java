package org.knowm.xchange.coindcx.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindcx.CoindcxAdapters;
import org.knowm.xchange.coindcx.dto.CoindcxLimitOrder;
import org.knowm.xchange.coindcx.dto.CoindcxOrderStatusResponse;
import org.knowm.xchange.coindcx.dto.CoindcxOrderType;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;

public class CoindcxTradeService extends CoindcxTradeServiceRaw implements TradeService {

  public CoindcxTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    return getOpenOrders(createOpenOrdersParams());
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    CoindcxOrderStatusResponse newOrder = placeCoindcxLimitOrder(limitOrder, CoindcxOrderType.LIMIT);

    // The return value contains details of any trades that have been immediately executed as a
    // result
    // of this order. Make these available to the application if it has provided a GeminiLimitOrder.
    if (limitOrder instanceof CoindcxLimitOrder) {
    	CoindcxLimitOrder raw = (CoindcxLimitOrder) limitOrder;
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

    for (String orderId : orderIds) {
      orders.add(CoindcxAdapters.adaptOrder(super.getGeminiOrderStatus(orderId)));
    }

    return orders;
  }
 
}
