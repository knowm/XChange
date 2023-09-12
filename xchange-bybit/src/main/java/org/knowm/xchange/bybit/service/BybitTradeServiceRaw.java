package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.BybitSide;

public class BybitTradeServiceRaw extends BybitBaseService {

  public BybitTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BybitResult<BybitOrderDetails> getBybitOrder(BybitCategory category, String orderId)
      throws IOException {
    BybitResult<BybitOrderDetails> order =
        bybitAuthenticated.getOpenOrders(
            apiKey, category.getValue(), orderId, nonceFactory, signatureCreator);
    if (!order.isSuccess()) {
      throw createBybitExceptionFromResult(order);
    }
    return order;
  }

  public BybitResult<BybitOrderResponse> placeOrder(
      BybitCategory category,
      String symbol,
      BybitSide side,
      BybitOrderType orderType,
      BigDecimal qty)
      throws IOException {
    BybitResult<BybitOrderResponse> placeOrder =
        bybitAuthenticated.placeOrder(
            apiKey,
            category.getValue(),
            symbol,
            side.getValue(),
            orderType.getValue(),
            qty.longValue(),
            nonceFactory,
            signatureCreator);
    if (!placeOrder.isSuccess()) {
      throw createBybitExceptionFromResult(placeOrder);
    }
    return placeOrder;
  }
}
