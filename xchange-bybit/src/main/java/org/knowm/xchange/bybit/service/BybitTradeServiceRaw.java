package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;
import static org.knowm.xchange.bybit.BybitResilience.GLOBAL_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_AMEND_OPTION_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_AMEND_SPOT_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CANCEL_LINEAR_AND_INVERSE_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CANCEL_OPTION_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CANCEL_SPOT_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CREATE_LINEAR_AND_INVERSE_RATE_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CREATE_OPTION_LIMITER;
import static org.knowm.xchange.bybit.BybitResilience.ORDER_CREATE_SPOT_RATE_LIMITER;
import static org.knowm.xchange.bybit.dto.trade.BybitOrder.TpslMode.FULL;
import static org.knowm.xchange.bybit.dto.trade.BybitOrder.TpslMode.PARTIAL;
import static org.knowm.xchange.bybit.dto.trade.BybitOrderType.MARKET;

import io.github.resilience4j.ratelimiter.RateLimiter;
import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitCancelAllOrdersPayload;
import org.knowm.xchange.bybit.dto.account.BybitCancelAllOrdersResponse;

import org.knowm.xchange.bybit.dto.trade.BybitOrder.SlTriggerBy;

import org.knowm.xchange.bybit.dto.trade.BybitAmendOrderPayload;
import org.knowm.xchange.bybit.dto.trade.BybitCancelOrderPayload;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
import org.knowm.xchange.bybit.dto.trade.BybitOrderType;
import org.knowm.xchange.bybit.dto.trade.BybitPlaceOrderPayload;
import org.knowm.xchange.bybit.dto.trade.BybitSide;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails;
import org.knowm.xchange.bybit.dto.trade.details.BybitTimeInForce;
import org.knowm.xchange.client.ResilienceRegistries;

public class BybitTradeServiceRaw extends BybitBaseService {

  protected BybitTradeServiceRaw(BybitExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

 BybitResult<BybitOrderDetails<BybitOrderDetail>> getBybitOrder(
      BybitCategory category, String orderId) throws IOException {
    BybitResult<BybitOrderDetails<BybitOrderDetail>> order =
        bybitAuthenticated.getOpenOrders(
            apiKey, signatureCreator, nonceFactory, category.getValue(), orderId);
    if (!order.isSuccess()) {
      throw createBybitExceptionFromResult(order);
    }
    return order;
  }

//  BybitResult<BybitOrderResponse> placeMarketOrder(
//      BybitCategory category, String symbol, BybitSide side, BigDecimal qty, String orderLinkId)
//      throws IOException {
//    BybitPlaceOrderPayload payload =
//        new BybitPlaceOrderPayload(category, symbol, side, MARKET, qty, orderLinkId);
//    BybitResult<BybitOrderResponse> placeOrder =
//        decorateApiCall(
//            () -> bybitAuthenticated.placeMarketOrder(apiKey, signatureCreator, nonceFactory,
//                payload))
//            .withRateLimiter(getCreateOrderRateLimiter(category))
//            .withRateLimiter(rateLimiter(GLOBAL_RATE_LIMITER))
//            .call();
//    if (!placeOrder.isSuccess()) {
//      throw createBybitExceptionFromResult(placeOrder);
//    }
//    return placeOrder;
//  }

//BybitResult<BybitOrderResponse> placeLimitOrder(
//      BybitCategory category,
//      String symbol,
//      BybitSide side,
//      BigDecimal qty,
//      BigDecimal limitPrice,
//      String orderLinkId,
//      boolean reduceOnly)
//      throws IOException {
//    BybitPlaceOrderPayload payload =
//        new BybitPlaceOrderPayload(
//            category, symbol, side, BybitOrderType.LIMIT, qty, orderLinkId, limitPrice);
//    payload.setReduceOnly(String.valueOf(reduceOnly));
//    BybitResult<BybitOrderResponse> placeOrder =
//        decorateApiCall(
//            () -> bybitAuthenticated.placeLimitOrder(apiKey, signatureCreator, nonceFactory,
//                payload))
//            .withRateLimiter(getCreateOrderRateLimiter(category))
//            .withRateLimiter(rateLimiter(GLOBAL_RATE_LIMITER))
//            .call();
//    if (!placeOrder.isSuccess()) {
//      throw createBybitExceptionFromResult(placeOrder);
//    }
//    return placeOrder;
//  }

 BybitResult<BybitOrderResponse> amendOrder(
      BybitCategory category,
      String symbol,
      String orderId,
      String orderLinkId,
      String triggerPrice,
      String qty,
      String price,
      String tpslMode,
      String takeProfit,
      String stopLoss,
      String tpTriggerBy,
      String slTriggerBy,
      String triggerBy,
      String tpLimitPrice,
      String slLimitPrice)
      throws IOException {
    // if only userId is used, don't need to send id
    if (orderId != null && orderId.isEmpty()) {
      orderId = null;
    }
    RateLimiter rateLimiter = getAmendOrderRateLimiter(category);
    BybitAmendOrderPayload payload =
        new BybitAmendOrderPayload(
            category,
            symbol,
            orderId,
            orderLinkId,
            triggerPrice,
            qty,
            price,
            tpslMode,
            takeProfit,
            stopLoss,
            tpTriggerBy,
            slTriggerBy,
            triggerBy,
            tpLimitPrice,
            slLimitPrice);
    BybitResult<BybitOrderResponse> amendOrder =
        decorateApiCall(
            () -> bybitAuthenticated.amendOrder(apiKey, signatureCreator, nonceFactory, payload))
            .withRateLimiter(rateLimiter)
            .withRateLimiter(rateLimiter(GLOBAL_RATE_LIMITER))
            .call();
    if (!amendOrder.isSuccess()) {
      throw createBybitExceptionFromResult(amendOrder);
    }
    return amendOrder;
  }

  BybitResult<BybitOrderResponse> placeOrder(
      BybitCategory category,
      String symbol,
      BybitSide side,
      BybitOrderType orderType,
      BigDecimal qty,
      BigDecimal limitPrice,
      String orderLinkId,
      BigDecimal stopLoss,
      SlTriggerBy slTriggerBy,
      BigDecimal slLimitPrice,
      BybitOrderType slOrderType,
      boolean reduceOnly,
      int positionIdx,
      BybitTimeInForce timeInForce)
      throws IOException {

    BybitPlaceOrderPayload payload =
        new BybitPlaceOrderPayload(
            category, symbol, side, orderType, qty, orderLinkId, positionIdx, limitPrice);
    if (stopLoss != null && slTriggerBy != null && slLimitPrice != null && slOrderType != null) {
      payload.setStopLoss(stopLoss.toString());
      payload.setSlTriggerBy(slTriggerBy.getValue());
      payload.setSlLimitPrice(slLimitPrice.toString());
      payload.setSlOrderType(slOrderType.getValue());
      if (slOrderType.equals(MARKET)) {
        payload.setTpslMode(FULL.getValue());
      } else {
        payload.setTpslMode(PARTIAL.getValue());
      }
    }
    if (reduceOnly) {
       payload.setReduceOnly("true");
    }
    if (timeInForce != null) {
      payload.setTimeInForce(timeInForce.getValue());
    }
    BybitResult<BybitOrderResponse> placeOrder =
        decorateApiCall(() -> bybitAuthenticated.placeOrder(apiKey, signatureCreator, nonceFactory, payload))
            .withRateLimiter(getCreateOrderRateLimiter(category))
            .withRateLimiter(rateLimiter(GLOBAL_RATE_LIMITER))
            .call();
    if (!placeOrder.isSuccess()) {
      throw createBybitExceptionFromResult(placeOrder);
    }
    return placeOrder;
  }

  BybitResult<BybitOrderResponse> cancelOrder(
      BybitCategory category, String symbol, String orderId, String orderLinkId)
      throws IOException {
    RateLimiter rateLimiter = getCancelOrderRateLimiter(category);
    BybitCancelOrderPayload payload =
        new BybitCancelOrderPayload(category, symbol, orderId, orderLinkId);
    return decorateApiCall(
        () -> bybitAuthenticated.cancelOrder(apiKey, signatureCreator, nonceFactory, payload))
        .withRateLimiter(rateLimiter)
        .withRateLimiter(rateLimiter(GLOBAL_RATE_LIMITER))
        .call();
  }

  BybitResult<BybitCancelAllOrdersResponse> cancelAllOrders(String category, String symbol,
      String baseCoin, String settleCoin, String orderFilter, String stopOrderType)
      throws IOException {
    BybitCancelAllOrdersPayload payload = new BybitCancelAllOrdersPayload(category, symbol,
        baseCoin, settleCoin, orderFilter, stopOrderType);
    BybitResult<BybitCancelAllOrdersResponse> response =
        bybitAuthenticated.cancelAllOrders(apiKey, signatureCreator, nonceFactory, payload);
    if (!response.isSuccess()) {
      throw createBybitExceptionFromResult(response);
    }
    return response;
  }

  private RateLimiter getCreateOrderRateLimiter(BybitCategory category) {
    switch (category) {
      case LINEAR:
      case INVERSE:
        return rateLimiter(ORDER_CREATE_LINEAR_AND_INVERSE_RATE_LIMITER);
      case SPOT:
        return rateLimiter(ORDER_CREATE_SPOT_RATE_LIMITER);
      case OPTION:
        return rateLimiter(ORDER_CREATE_OPTION_LIMITER);
    }
    return null;
  }

  private RateLimiter getCancelOrderRateLimiter(BybitCategory category) {
    switch (category) {
      case LINEAR:
      case INVERSE:
        return rateLimiter(ORDER_CANCEL_LINEAR_AND_INVERSE_RATE_LIMITER);
      case SPOT:
        return rateLimiter(ORDER_CANCEL_SPOT_RATE_LIMITER);
      case OPTION:
        return rateLimiter(ORDER_CANCEL_OPTION_LIMITER);
    }
    return null;
  }
  private RateLimiter getAmendOrderRateLimiter(BybitCategory category) {
    switch (category) {
      case LINEAR:
      case INVERSE:
        return rateLimiter(ORDER_AMEND_LINEAR_AND_INVERSE_RATE_LIMITER);
      case SPOT:
        return rateLimiter(ORDER_AMEND_SPOT_RATE_LIMITER);
      case OPTION:
        return rateLimiter(ORDER_AMEND_OPTION_LIMITER);
    }
    return null;
  }
}
