package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
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
      BybitCategory category, String symbol, BybitSide side, BigDecimal qty) throws IOException {
    BybitResult<BybitOrderResponse> placeOrder =
        bybitAuthenticated.placeMarketOrder(
            apiKey,
            signatureCreator,
            nonceFactory,
            category.getValue(),
            symbol,
            side.getValue(),
            BybitOrderType.MARKET.getValue(),
            qty);
    if (!placeOrder.isSuccess()) {
      throw createBybitExceptionFromResult(placeOrder);
    }
    return placeOrder;
  }

  public BybitResult<BybitOrderResponse> placeLimitOrder(
      BybitCategory category, String symbol, BybitSide side, BigDecimal qty, BigDecimal limitPrice)
      throws IOException {
    BybitResult<BybitOrderResponse> placeOrder =
        bybitAuthenticated.placeLimitOrder(
            apiKey,
            signatureCreator,
            nonceFactory,
            category.getValue(),
            symbol,
            side.getValue(),
            BybitOrderType.LIMIT.getValue(),
            qty,
            limitPrice,
            0,
            false);
    if (!placeOrder.isSuccess()) {
      throw createBybitExceptionFromResult(placeOrder);
    }
    return placeOrder;
  }
}
