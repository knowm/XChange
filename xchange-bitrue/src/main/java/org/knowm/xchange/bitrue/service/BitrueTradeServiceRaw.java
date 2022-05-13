package org.knowm.xchange.bitrue.service;

import org.knowm.xchange.bitrue.BitrueAdapters;
import org.knowm.xchange.bitrue.BitrueAuthenticated;
import org.knowm.xchange.bitrue.BitrueExchange;
import org.knowm.xchange.bitrue.dto.BitrueException;
import org.knowm.xchange.bitrue.dto.trade.*;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.knowm.xchange.bitrue.BitrueResilience.*;
import static org.knowm.xchange.client.ResilienceRegistries.NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME;

public class BitrueTradeServiceRaw extends BitrueBaseService {

  protected BitrueTradeServiceRaw(
      BitrueExchange exchange,
      BitrueAuthenticated binance,
      ResilienceRegistries resilienceRegistries) {
    super(exchange, binance, resilienceRegistries);
  }

  public List<BitrueOrder> openOrders() throws BitrueException, IOException {
    return openOrders(null);
  }

  public List<BitrueOrder> openOrders(CurrencyPair pair) throws BitrueException, IOException {
    return decorateApiCall(
            () ->
                bitrue.openOrders(
                    Optional.ofNullable(pair).map(BitrueAdapters::toSymbol).orElse(null),
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("openOrders"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), openOrdersPermits(pair))
        .call();
  }

  public BitrueNewOrder newOrder(
      CurrencyPair pair,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      BigDecimal price,
      String newClientOrderId,
      BigDecimal stopPrice,
      BigDecimal icebergQty,
      BitrueNewOrder.NewOrderResponseType newOrderRespType)
      throws IOException, BitrueException {
    return decorateApiCall(
            () ->
                bitrue.newOrder(
                    BitrueAdapters.toSymbol(pair),
                    side,
                    type,
                    timeInForce,
                    quantity,
                    price,
                    newClientOrderId,
                    stopPrice,
                    icebergQty,
                    newOrderRespType,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("newOrder", NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME))
        .withRateLimiter(rateLimiter(ORDERS_PER_SECOND_RATE_LIMITER))
        .withRateLimiter(rateLimiter(ORDERS_PER_DAY_RATE_LIMITER))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }



  public BitrueOrder orderStatus(CurrencyPair pair, Long orderId, String origClientOrderId)
      throws IOException, BitrueException {
    return decorateApiCall(
            () ->
                bitrue.orderStatus(
                    BitrueAdapters.toSymbol(pair),
                    orderId,
                    origClientOrderId,
                    getRecvWindow(),
                    getTimestampFactory(),
                    super.apiKey,
                    super.signatureCreator))
        .withRetry(retry("orderStatus"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public BitrueCancelledOrder cancelOrder(
      CurrencyPair pair, Long orderId, String origClientOrderId, String newClientOrderId)
      throws IOException, BitrueException {
    return decorateApiCall(
            () ->
                bitrue.cancelOrder(
                    BitrueAdapters.toSymbol(pair),
                    orderId,
                    origClientOrderId,
                    newClientOrderId,
                    getRecvWindow(),
                    getTimestampFactory(),
                    super.apiKey,
                    super.signatureCreator))
        .withRetry(retry("cancelOrder"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public List<BitrueCancelledOrder> cancelAllOpenOrders(CurrencyPair pair)
      throws IOException, BitrueException {
    return decorateApiCall(
            () ->
                bitrue.cancelAllOpenOrders(
                    BitrueAdapters.toSymbol(pair),
                    getRecvWindow(),
                    getTimestampFactory(),
                    super.apiKey,
                    super.signatureCreator))
        .withRetry(retry("cancelAllOpenOrders"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public List<BitrueOrder> allOrders(CurrencyPair pair, Long orderId, Integer limit)
      throws BitrueException, IOException {
    return decorateApiCall(
            () ->
                bitrue.allOrders(
                    BitrueAdapters.toSymbol(pair),
                    orderId,
                    limit,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("allOrders"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public List<BitrueTrade> myTrades(
      CurrencyPair pair, Long orderId, Long startTime, Long endTime, Long fromId, Integer limit)
      throws BitrueException, IOException {
    return decorateApiCall(
            () ->
                bitrue.myTrades(
                    BitrueAdapters.toSymbol(pair),
                    orderId,
                    startTime,
                    endTime,
                    fromId,
                    limit,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("myTrades"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), myTradesPermits(limit))
        .call();
  }



  protected int openOrdersPermits(CurrencyPair pair) {
    return pair != null ? 1 : 40;
  }

  protected int myTradesPermits(Integer limit) {
    if (limit != null && limit > 500) {
      return 10;
    }
    return 5;
  }
}
