package org.knowm.xchange.binance.service;

import static org.knowm.xchange.binance.BinanceResilience.ORDERS_PER_DAY_RATE_LIMITER;
import static org.knowm.xchange.binance.BinanceResilience.ORDERS_PER_SECOND_RATE_LIMITER;
import static org.knowm.xchange.binance.BinanceResilience.REQUEST_WEIGHT_RATE_LIMITER;
import static org.knowm.xchange.client.ResilienceRegistries.NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.futures.BinancePosition;
import org.knowm.xchange.binance.dto.trade.*;
import org.knowm.xchange.binance.dto.trade.futures.BinanceFutureNewOrder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.instrument.Instrument;

public class BinanceTradeServiceRaw extends BinanceBaseService {

  protected BinanceTradeServiceRaw(
      BinanceExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public List<BinanceOrder> openOrdersAllProducts() throws BinanceException, IOException {
    return openOrdersAllProducts(null);
  }

  public List<BinanceOrder> openOrdersAllProducts(Instrument pair)
      throws BinanceException, IOException {
    if (exchange.isPortfolioMarginEnabled()) {
      return decorateApiCall(
              () ->
                  (pair instanceof FuturesContract)
                      ? (BinanceAdapters.isInverse(pair)
                          ? binanceFutures.futureOpenPortfolioMarginInverseOrders(
                              Optional.of(pair).map(BinanceAdapters::toInverseSymbol).orElse(null),
                              getRecvWindow(),
                              getTimestampFactory(),
                              apiKey,
                              signatureCreator)
                          : binanceFutures.futureOpenPortfolioMarginOrders(
                              Optional.of(pair).map(BinanceAdapters::toSymbol).orElse(null),
                              getRecvWindow(),
                              getTimestampFactory(),
                              apiKey,
                              signatureCreator))
                      : binance.openOrders(
                          Optional.ofNullable(pair).map(BinanceAdapters::toSymbol).orElse(null),
                          getRecvWindow(),
                          getTimestampFactory(),
                          apiKey,
                          signatureCreator))
          .withRetry(retry("openOrders"))
          .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), openOrdersPermits(pair))
          .call();
    } else {
      return decorateApiCall(
              () ->
                  (pair instanceof FuturesContract)
                      ? (BinanceAdapters.isInverse(pair)
                          ? inverseBinanceFutures.futureOpenInverseOrders(
                              Optional.of(pair).map(BinanceAdapters::toInverseSymbol).orElse(null),
                              getRecvWindow(),
                              getTimestampFactory(),
                              apiKey,
                              signatureCreator)
                          : binanceFutures.futureOpenOrders(
                              Optional.of(pair).map(BinanceAdapters::toSymbol).orElse(null),
                              getRecvWindow(),
                              getTimestampFactory(),
                              apiKey,
                              signatureCreator))
                      : binance.openOrders(
                          Optional.ofNullable(pair).map(BinanceAdapters::toSymbol).orElse(null),
                          getRecvWindow(),
                          getTimestampFactory(),
                          apiKey,
                          signatureCreator))
          .withRetry(retry("openOrders"))
          .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), openOrdersPermits(pair))
          .call();
    }
  }

  public BinanceNewOrder newOrder(
      Instrument pair,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      BigDecimal quoteOrderQty,
      BigDecimal price,
      String newClientOrderId,
      BigDecimal stopPrice,
      Long trailingDelta,
      BigDecimal icebergQty,
      BinanceNewOrder.NewOrderResponseType newOrderRespType)
      throws IOException, BinanceException {
    return decorateApiCall(
            () ->
                binance.newOrder(
                    BinanceAdapters.toSymbol(pair),
                    side,
                    type,
                    timeInForce,
                    quantity,
                    quoteOrderQty,
                    price,
                    newClientOrderId,
                    stopPrice,
                    trailingDelta,
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

  public BinanceFutureNewOrder newPortfolioMarginFutureOrder(
      Instrument pair,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      boolean reduceOnly,
      BigDecimal price,
      String newClientOrderId,
      BinanceNewOrder.NewOrderResponseType newOrderRespType)
      throws IOException, BinanceException {
    return decorateApiCall(
            () ->
                binanceFutures.newPortfolioMarginLinearOrder(
                    BinanceAdapters.toSymbol(pair),
                    side,
                    type,
                    timeInForce,
                    quantity,
                    reduceOnly,
                    price,
                    newClientOrderId,
                    newOrderRespType,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("newFutureOrder", NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME))
        .withRateLimiter(rateLimiter(ORDERS_PER_SECOND_RATE_LIMITER))
        .withRateLimiter(rateLimiter(ORDERS_PER_DAY_RATE_LIMITER))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public BinanceFutureNewOrder newPortfolioMarginInverseFutureOrder(
      Instrument pair,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      boolean reduceOnly,
      BigDecimal price,
      String newClientOrderId,
      BinanceNewOrder.NewOrderResponseType newOrderRespType)
      throws IOException, BinanceException {
    return decorateApiCall(
            () ->
                binanceFutures.newPortfolioMarginInverseOrder(
                    BinanceAdapters.toSymbol(pair, true),
                    side,
                    type,
                    timeInForce,
                    quantity,
                    reduceOnly,
                    price,
                    newClientOrderId,
                    newOrderRespType,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("newFutureOrder", NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME))
        .withRateLimiter(rateLimiter(ORDERS_PER_SECOND_RATE_LIMITER))
        .withRateLimiter(rateLimiter(ORDERS_PER_DAY_RATE_LIMITER))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public BinanceFutureNewOrder newFutureOrder(
      Instrument pair,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      boolean reduceOnly,
      BigDecimal price,
      String newClientOrderId,
      BigDecimal stopPrice,
      boolean closePosition,
      BigDecimal activationPrice,
      BigDecimal callbackRate,
      BinanceNewOrder.NewOrderResponseType newOrderRespType)
      throws IOException, BinanceException {
    return decorateApiCall(
            () ->
                binanceFutures.newOrder(
                    BinanceAdapters.toSymbol(pair),
                    side,
                    type,
                    timeInForce,
                    quantity,
                    reduceOnly,
                    price,
                    newClientOrderId,
                    stopPrice,
                    closePosition,
                    activationPrice,
                    callbackRate,
                    newOrderRespType,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("newFutureOrder", NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME))
        .withRateLimiter(rateLimiter(ORDERS_PER_SECOND_RATE_LIMITER))
        .withRateLimiter(rateLimiter(ORDERS_PER_DAY_RATE_LIMITER))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public BinanceFutureNewOrder newInverseFutureOrder(
      Instrument pair,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      boolean reduceOnly,
      BigDecimal price,
      String newClientOrderId,
      BigDecimal stopPrice,
      boolean closePosition,
      BigDecimal activationPrice,
      BigDecimal callbackRate,
      BinanceNewOrder.NewOrderResponseType newOrderRespType)
      throws IOException, BinanceException {
    return decorateApiCall(
            () ->
                inverseBinanceFutures.newInverseOrder(
                    BinanceAdapters.toSymbol(pair, true),
                    side,
                    type,
                    timeInForce,
                    quantity,
                    reduceOnly,
                    price,
                    newClientOrderId,
                    stopPrice,
                    closePosition,
                    activationPrice,
                    callbackRate,
                    newOrderRespType,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("newFutureOrder", NON_IDEMPOTENT_CALLS_RETRY_CONFIG_NAME))
        .withRateLimiter(rateLimiter(ORDERS_PER_SECOND_RATE_LIMITER))
        .withRateLimiter(rateLimiter(ORDERS_PER_DAY_RATE_LIMITER))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public void testNewOrder(
      Instrument pair,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      BigDecimal quoteOrderQty,
      BigDecimal price,
      String newClientOrderId,
      BigDecimal stopPrice,
      Long trailingDelta,
      BigDecimal icebergQty)
      throws IOException, BinanceException {
    decorateApiCall(
            () ->
                binance.testNewOrder(
                    BinanceAdapters.toSymbol(pair),
                    side,
                    type,
                    timeInForce,
                    quantity,
                    quoteOrderQty,
                    price,
                    newClientOrderId,
                    stopPrice,
                    trailingDelta,
                    icebergQty,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("testNewOrder"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public BinanceOrder orderStatusAllProducts(
      Instrument pair, Long orderId, String origClientOrderId)
      throws IOException, BinanceException {
    if (exchange.isPortfolioMarginEnabled()) {

      return decorateApiCall(
              () ->
                  (pair instanceof FuturesContract)
                      ? (BinanceAdapters.isInverse(pair)
                          ? binanceFutures.futurePortfolioMarginInverseOrderStatus(
                              BinanceAdapters.toInverseSymbol(pair),
                              orderId,
                              origClientOrderId,
                              getRecvWindow(),
                              getTimestampFactory(),
                              super.apiKey,
                              super.signatureCreator)
                          : binanceFutures.futurePortfolioMarginOrderStatus(
                              BinanceAdapters.toSymbol(pair),
                              orderId,
                              origClientOrderId,
                              getRecvWindow(),
                              getTimestampFactory(),
                              super.apiKey,
                              super.signatureCreator))
                      : binance.orderStatus(
                          BinanceAdapters.toSymbol(pair),
                          orderId,
                          origClientOrderId,
                          getRecvWindow(),
                          getTimestampFactory(),
                          super.apiKey,
                          super.signatureCreator))
          .withRetry(retry("orderStatus"))
          .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
          .call();
    } else {
      return decorateApiCall(
              () ->
                  (pair instanceof FuturesContract)
                      ? (BinanceAdapters.isInverse(pair)
                          ? inverseBinanceFutures.futureInverseOrderStatus(
                              BinanceAdapters.toInverseSymbol(pair),
                              orderId,
                              origClientOrderId,
                              getRecvWindow(),
                              getTimestampFactory(),
                              super.apiKey,
                              super.signatureCreator)
                          : binanceFutures.futureOrderStatus(
                              BinanceAdapters.toSymbol(pair),
                              orderId,
                              origClientOrderId,
                              getRecvWindow(),
                              getTimestampFactory(),
                              super.apiKey,
                              super.signatureCreator))
                      : binance.orderStatus(
                          BinanceAdapters.toSymbol(pair),
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
  }

  public BinanceCancelledOrder cancelOrderAllProducts(
      Instrument pair, Long orderId, String origClientOrderId, String newClientOrderId)
      throws IOException, BinanceException {
    if (exchange.isPortfolioMarginEnabled()) {

      return decorateApiCall(
              () ->
                  (pair instanceof FuturesContract)
                      ? (BinanceAdapters.isInverse(pair)
                          ? binanceFutures.cancelPortfolioMarginInverseFutureOrder(
                              BinanceAdapters.toSymbol(pair, true),
                              orderId,
                              origClientOrderId,
                              getRecvWindow(),
                              getTimestampFactory(),
                              super.apiKey,
                              super.signatureCreator)
                          : binanceFutures.cancelPortfolioMarginFutureOrder(
                              BinanceAdapters.toSymbol(pair),
                              orderId,
                              origClientOrderId,
                              getRecvWindow(),
                              getTimestampFactory(),
                              super.apiKey,
                              super.signatureCreator))
                      : binance.cancelOrder(
                          BinanceAdapters.toSymbol(pair),
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
    } else {
      return decorateApiCall(
              () ->
                  (pair instanceof FuturesContract)
                      ? (BinanceAdapters.isInverse(pair)
                          ? binanceFutures.cancelInverseFutureOrder(
                              BinanceAdapters.toSymbol(pair, true),
                              orderId,
                              origClientOrderId,
                              getRecvWindow(),
                              getTimestampFactory(),
                              super.apiKey,
                              super.signatureCreator)
                          : binanceFutures.cancelFutureOrder(
                              BinanceAdapters.toSymbol(pair),
                              orderId,
                              origClientOrderId,
                              getRecvWindow(),
                              getTimestampFactory(),
                              super.apiKey,
                              super.signatureCreator))
                      : binance.cancelOrder(
                          BinanceAdapters.toSymbol(pair),
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
  }

  public List<BinanceCancelledOrder> cancelAllOpenOrdersAllProducts(Instrument pair)
      throws IOException, BinanceException {
    return decorateApiCall(
            () ->
                (pair instanceof FuturesContract)
                    ? binanceFutures.cancelAllFutureOpenOrders(
                        BinanceAdapters.toSymbol(pair),
                        getRecvWindow(),
                        getTimestampFactory(),
                        super.apiKey,
                        super.signatureCreator)
                    : binance.cancelAllOpenOrders(
                        BinanceAdapters.toSymbol(pair),
                        getRecvWindow(),
                        getTimestampFactory(),
                        super.apiKey,
                        super.signatureCreator))
        .withRetry(retry("cancelAllOpenOrders"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public List<BinanceOrder> allOrders(CurrencyPair pair, Long orderId, Integer limit)
      throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                binance.allOrders(
                    BinanceAdapters.toSymbol(pair),
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

  public List<BinanceTrade> myTradesAllProducts(
      Instrument pair, Long orderId, Long startTime, Long endTime, Long fromId, Integer limit)
      throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                (pair instanceof FuturesContract)
                    ? binanceFutures.myFutureTrades(
                        BinanceAdapters.toSymbol(pair),
                        orderId,
                        startTime,
                        endTime,
                        fromId,
                        limit,
                        getRecvWindow(),
                        getTimestampFactory(),
                        apiKey,
                        signatureCreator)
                    : binance.myTrades(
                        BinanceAdapters.toSymbol(pair),
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

  public List<BinancePosition> openPositions() throws BinanceException, IOException {
    return decorateApiCall(
            () ->
                binanceFutures.futuresAccount(
                    getRecvWindow(), getTimestampFactory(), apiKey, signatureCreator))
        .withRetry(retry("futures-account"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER), 5)
        .call()
        .getPositions();
  }

  /**
   * Retrieves the dust log from Binance. If you have many currencies with low amount (=dust) that
   * cannot be traded, because their amount is less than the minimum amount required for trading
   * them, you can convert all these currencies at once into BNB with the button "Convert Small
   * Balance to BNB".
   *
   * @param startTime optional. If set, also the endTime must be set. If neither time is set, the
   *     100 most recent dust logs are returned.
   * @param endTime optional. If set, also the startTime must be set. If neither time is set, the
   *     100 most recent dust logs are returned.
   * @return
   * @throws IOException
   */
  public BinanceDustLog getDustLog(Long startTime, Long endTime) throws IOException {

    if (((startTime != null) && (endTime == null)) || (startTime == null) && (endTime != null))
      throw new ExchangeException(
          "You need to specify both, the start and the end date, or none of them");

    return decorateApiCall(
            () ->
                binance.getDustLog(
                    startTime,
                    endTime,
                    getRecvWindow(),
                    getTimestampFactory(),
                    apiKey,
                    signatureCreator))
        .withRetry(retry("myDustLog"))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public BinanceListenKey startUserDataStream() throws IOException {
    return decorateApiCall(() -> binance.startUserDataStream(apiKey))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public void keepAliveDataStream(String listenKey) throws IOException {
    decorateApiCall(() -> binance.keepAliveUserDataStream(apiKey, listenKey))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  public void closeDataStream(String listenKey) throws IOException {
    decorateApiCall(() -> binance.closeUserDataStream(apiKey, listenKey))
        .withRateLimiter(rateLimiter(REQUEST_WEIGHT_RATE_LIMITER))
        .call();
  }

  protected int openOrdersPermits(Instrument pair) {
    return pair != null ? 1 : 40;
  }

  protected int myTradesPermits(Integer limit) {
    if (limit != null && limit > 500) {
      return 10;
    }
    return 5;
  }
}
