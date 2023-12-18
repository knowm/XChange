package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.BybitOrderRequest;

public class BybitTradeServiceRaw extends BybitBaseService {

  public BybitTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BybitResult<BybitOrderDetails> getBybitOrder(String orderId) throws IOException {
    BybitResult<BybitOrderDetails> order =
        bybitAuthenticated.getOrder(apiKey, orderId, nonceFactory, signatureCreator);
    if (!order.isSuccess()) {
      throw createBybitExceptionFromResult(order);
    }
    return order;
  }

  public BybitResult<BybitOrderRequest> placeOrder(
      String symbol, long qty, String side, String type) throws IOException {
    BybitResult<BybitOrderRequest> placeOrder =
        bybitAuthenticated.placeOrder(
            apiKey, symbol, qty, side, type, nonceFactory, signatureCreator);
    if (!placeOrder.isSuccess()) {
      throw createBybitExceptionFromResult(placeOrder);
    }
    return placeOrder;
  }
}
