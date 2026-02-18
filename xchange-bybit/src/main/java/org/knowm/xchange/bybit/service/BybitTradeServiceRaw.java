package org.knowm.xchange.bybit.service;

import static org.knowm.xchange.bybit.BybitAdapters.convertToBybitSymbol;
import static org.knowm.xchange.bybit.BybitAdapters.createBybitExceptionFromResult;
import static org.knowm.xchange.bybit.BybitResilience.GLOBAL_RATE_LIMITER;

import io.github.resilience4j.ratelimiter.RateLimiter;
import java.io.IOException;
import org.knowm.xchange.bybit.BybitExchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.BybitResult;
import org.knowm.xchange.bybit.dto.account.BybitCancelAllOrdersPayload;
import org.knowm.xchange.bybit.dto.account.BybitCancelAllOrdersResponse;
import org.knowm.xchange.bybit.dto.trade.BybitAmendOrderPayload;
import org.knowm.xchange.bybit.dto.trade.BybitCancelOrderPayload;
import org.knowm.xchange.bybit.dto.trade.BybitOrderResponse;
import org.knowm.xchange.bybit.dto.trade.BybitPlaceOrderPayload;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetail;
import org.knowm.xchange.bybit.dto.trade.details.BybitOrderDetails;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.instrument.Instrument;

public class BybitTradeServiceRaw extends BybitBaseService {

  protected BybitTradeServiceRaw(
      BybitExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  BybitResult<BybitOrderDetails<BybitOrderDetail>> getBybitOrder(
      BybitCategory category, Instrument instrument, String orderId) throws IOException {
    String symbol = null;
    if (instrument != null) {
      symbol = convertToBybitSymbol(instrument);
    }

    BybitResult<BybitOrderDetails<BybitOrderDetail>> bybitOrder =
        bybitAuthenticated.getOrders(
            apiKey,
            signatureCreator,
            exchange.getTimeStampFactory(),
            category.getValue(),
            symbol,
            orderId);

    if (!bybitOrder.isSuccess()) {
      throw createBybitExceptionFromResult(bybitOrder);
    }
    return bybitOrder;
  }

  BybitResult<BybitOrderResponse> amendOrder(BybitAmendOrderPayload payload, BybitCategory category)
      throws IOException {
    RateLimiter rateLimiter = getAmendOrderRateLimiter(category);
    BybitResult<BybitOrderResponse> amendOrder =
        decorateApiCall(
                () ->
                    bybitAuthenticated.amendOrder(
                        apiKey, signatureCreator, exchange.getTimeStampFactory(), payload))
            .withRateLimiter(rateLimiter)
            .withRateLimiter(rateLimiter(GLOBAL_RATE_LIMITER))
            .call();
    if (!amendOrder.isSuccess()) {
      throw createBybitExceptionFromResult(amendOrder);
    }
    return amendOrder;
  }

  BybitResult<BybitOrderResponse> placeOrder(BybitPlaceOrderPayload payload, BybitCategory category)
      throws IOException {
    BybitResult<BybitOrderResponse> placeOrder =
        decorateApiCall(
                () ->
                    bybitAuthenticated.placeOrder(
                        apiKey, signatureCreator, exchange.getTimeStampFactory(), payload))
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
            () ->
                bybitAuthenticated.cancelOrder(
                    apiKey, signatureCreator, exchange.getTimeStampFactory(), payload))
        .withRateLimiter(rateLimiter)
        .withRateLimiter(rateLimiter(GLOBAL_RATE_LIMITER))
        .call();
  }

  BybitResult<BybitCancelAllOrdersResponse> cancelAllOrders(
      String category,
      String symbol,
      String baseCoin,
      String settleCoin,
      String orderFilter,
      String stopOrderType)
      throws IOException {
    BybitCancelAllOrdersPayload payload =
        new BybitCancelAllOrdersPayload(
            category, symbol, baseCoin, settleCoin, orderFilter, stopOrderType);
    BybitResult<BybitCancelAllOrdersResponse> response =
        bybitAuthenticated.cancelAllOrders(
            apiKey, signatureCreator, exchange.getTimeStampFactory(), payload);
    if (!response.isSuccess()) {
      throw createBybitExceptionFromResult(response);
    }
    return response;
  }
}
