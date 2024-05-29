package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitCancelOrderPayload;
import org.knowm.xchange.bybit.dto.trade.BybitPlaceOrderPayload;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails;

public class BybitTradeServiceRaw extends BybitBaseService {

  public BybitTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BybitResult<BybitOrderDetails<BybitOrderDetail>> getBybitOrder(
      BybitCategory category, String orderId) throws IOException {
    BybitResult<BybitOrderDetails<BybitOrderDetail>> order =
        bybitAuthenticated.getOpenOrders(
            apiKey, signatureCreator, nonceFactory, category.getValue(), orderId);
    if (!order.isSuccess()) {
      throw createBybitExceptionFromResult(order);
    }
    return order;
  }

  public BybitResult<BybitOrderResponse> placeMarketOrder(
      BybitCategory category, String symbol, BybitSide side, BigDecimal qty, String orderLinkId)
      throws IOException {
    BybitPlaceOrderPayload payload = new BybitPlaceOrderPayload(category.getValue(),
        symbol, side.getValue(), BybitOrderType.MARKET.getValue(), qty, orderLinkId);
    BybitResult<BybitOrderResponse> placeOrder =
        bybitAuthenticated.placeMarketOrder(
            apiKey,
            signatureCreator,
            nonceFactory,
            payload);
    if (!placeOrder.isSuccess()) {
      throw createBybitExceptionFromResult(placeOrder);
    }
    return placeOrder;
  }

  public BybitResult<BybitOrderResponse> placeLimitOrder(
      BybitCategory category, String symbol, BybitSide side, BigDecimal qty, BigDecimal limitPrice,
      String orderLinkId)
      throws IOException {
    BybitPlaceOrderPayload payload = new BybitPlaceOrderPayload(category.getValue(),
        symbol, side.getValue(), BybitOrderType.LIMIT.getValue(), qty, orderLinkId, limitPrice);
    BybitResult<BybitOrderResponse> placeOrder =
        bybitAuthenticated.placeLimitOrder(
            apiKey,
            signatureCreator,
            nonceFactory,
            payload);
    if (!placeOrder.isSuccess()) {
      throw createBybitExceptionFromResult(placeOrder);
    }
    return placeOrder;
  }

  public BybitResult<BybitOrderResponse> cancelOrder(BybitCategory category,String symbol,
  String orderId, String orderLinkId) throws IOException {
    BybitCancelOrderPayload payload = new BybitCancelOrderPayload(category, symbol, orderId, orderLinkId);
    BybitResult<BybitOrderResponse> cancelOrder =
        bybitAuthenticated.cancelOrder(
            apiKey,
            signatureCreator,
            nonceFactory,
            payload);
    if (!cancelOrder.isSuccess()) {
      throw createBybitExceptionFromResult(cancelOrder);
    }
    return cancelOrder;
  }
}
