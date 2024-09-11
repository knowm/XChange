package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;
import static org.knowm.xchange.bybit.dto.trade.BybitAdvancedOrder.TpslMode.FULL;
import static org.knowm.xchange.bybit.dto.trade.BybitAdvancedOrder.TpslMode.PARTIAL;
import static org.knowm.xchange.bybit.dto.trade.BybitOrderType.MARKET;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.trade.BybitAdvancedOrder.SlTriggerBy;
import org.knowm.xchange.bybit.dto.trade.BybitAdvancedOrder.TimeInForce;
import org.knowm.xchange.bybit.dto.trade.BybitCancelOrderPayload;
import org.knowm.xchange.bybit.dto.trade.BybitAmendOrderPayload;
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
    BybitPlaceOrderPayload payload = new BybitPlaceOrderPayload(category,
        symbol, side, MARKET, qty, orderLinkId);
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
      String orderLinkId, boolean reduceOnly)
      throws IOException {
    BybitPlaceOrderPayload payload = new BybitPlaceOrderPayload(category,
        symbol, side, BybitOrderType.LIMIT, qty, orderLinkId, limitPrice);
    payload.setReduceOnly(String.valueOf(reduceOnly));
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

  public BybitResult<BybitOrderResponse> amendOrder(BybitCategory category, String symbol, String orderId,
      String orderLinkId, String triggerPrice, String qty, String price, String tpslMode, String takeProfit,
      String stopLoss, String tpTriggerBy,String slTriggerBy,String triggerBy,String tpLimitPrice,
  String slLimitPrice) throws IOException {
    //if only userId is used, don't need to send id
    if(orderId!= null && orderId.isEmpty())
      orderId = null;
    BybitAmendOrderPayload payload = new BybitAmendOrderPayload(category, symbol,orderId,orderLinkId,triggerPrice,qty,price,
    tpslMode, takeProfit, stopLoss, tpTriggerBy, slTriggerBy, triggerBy, tpLimitPrice, slLimitPrice);
    BybitResult<BybitOrderResponse> amendOrder =
    bybitAuthenticated.amendOrder(
        apiKey,
        signatureCreator,
        nonceFactory,
        payload);
    if (!amendOrder.isSuccess()) {
      throw createBybitExceptionFromResult(amendOrder);
    }
    return amendOrder;
  }


  public BybitResult<BybitOrderResponse> placeAdvancedOrder(BybitCategory category, String symbol,
      BybitSide side, BybitOrderType orderType, BigDecimal qty, BigDecimal limitPrice,
      String orderLinkId, BigDecimal stopLoss, SlTriggerBy slTriggerBy, BigDecimal slLimitPrice,
      BybitOrderType slOrderType, boolean reduceOnly, int positionIdx, TimeInForce timeInForce)
      throws IOException {

    BybitPlaceOrderPayload payload = new BybitPlaceOrderPayload(category, symbol, side, orderType,
        qty, orderLinkId, positionIdx, limitPrice);
    if (stopLoss != null && slTriggerBy != null && slLimitPrice != null && slOrderType != null) {
      payload.setStopLoss(stopLoss.toString());
      payload.setSlTriggerBy(slTriggerBy.getValue());
      payload.setSlLimitPrice(slLimitPrice.toString());
      payload.setSlOrderType(slOrderType.getValue());
      if (slOrderType.equals(MARKET))
        payload.setTpslMode(FULL.getValue());
      else
        payload.setTpslMode(PARTIAL.getValue());
    }
    if(reduceOnly)
      payload.setReduceOnly("true");
    if (timeInForce != null) {
      payload.setTimeInForce(timeInForce);
    }
    BybitResult<BybitOrderResponse> placeOrder = bybitAuthenticated.placeAdvancedOrder(
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
