package org.knowm.xchange.kucoin;

import static org.knowm.xchange.kucoin.KucoinExceptionClassifier.classifyingExceptions;
import static org.knowm.xchange.kucoin.KucoinResilience.CANCEL_ALL_ORDERS_RATE_LIMITER;
import static org.knowm.xchange.kucoin.KucoinResilience.PRIVATE_REST_ENDPOINT_RATE_LIMITER;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.kucoin.dto.request.OrderCreateApiRequest;
import org.knowm.xchange.kucoin.dto.response.HistOrdersResponse;
import org.knowm.xchange.kucoin.dto.response.OrderCancelByClientOrderIdResponse;
import org.knowm.xchange.kucoin.dto.response.OrderCancelResponse;
import org.knowm.xchange.kucoin.dto.response.OrderCreateResponse;
import org.knowm.xchange.kucoin.dto.response.OrderResponse;
import org.knowm.xchange.kucoin.dto.response.Pagination;
import org.knowm.xchange.kucoin.dto.response.TradeResponse;

public class KucoinTradeServiceRaw extends KucoinBaseService {

  protected KucoinTradeServiceRaw(
      KucoinExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public Pagination<OrderResponse> getKucoinOpenOrders(String symbol, int page, int pageSize)
      throws IOException {
    return getKucoinOrders(symbol, "active", page, pageSize);
  }

  public Pagination<OrderResponse> getKucoinClosedOrders(String symbol, int page, int pageSize)
      throws IOException {
    return getKucoinOrders(symbol, "done", page, pageSize);
  }

  public Pagination<OrderResponse> getKucoinOrders(
      String symbol, String status, int page, int pageSize) throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () -> decorateApiCall(() -> orderApi.queryOrders(
            apiKey,
            digest,
            nonceFactory,
            passphrase,
            apiKeyVersion,
            symbol,
            null,
            null,
            status,
            null,
            null,
            pageSize,
            page))
            .withRetry(retry("getKucoinOrders"))
            .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
            .call());
  }

  public OrderResponse getKucoinOrder(String id) throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () ->
            decorateApiCall(
                () ->
                    orderApi.getOrder(
                        apiKey,
                        digest,
                        nonceFactory,
                        passphrase,
                        apiKeyVersion,
                        id))
                .withRetry(retry("getKucoinOrder"))
                .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
                .call()
    );
  }

  public Pagination<TradeResponse> getKucoinFills(
      String symbol, String orderId, int page, int pageSize, Long startAt, Long endAt)
      throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () ->
            decorateApiCall(
                () ->
                    fillApi.queryTrades(
                        apiKey,
                        digest,
                        nonceFactory,
                        passphrase,
                        apiKeyVersion,
                        symbol,
                        orderId,
                        null,
                        null,
                        startAt,
                        endAt,
                        pageSize,
                        page))
                .withRetry(retry("getKucoinFills"))
                .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
                .call()
    );
  }

  public Pagination<HistOrdersResponse> getKucoinHistOrders(
      String symbol, int page, int pageSize, Long startAt, Long endAt) throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () -> decorateApiCall(
            () ->
                histOrdersApi.queryHistOrders(
                    apiKey,
                    digest,
                    nonceFactory,
                    passphrase,
                    apiKeyVersion,
                    symbol,
                    null,
                    startAt,
                    endAt,
                    pageSize,
                    page))
            .withRetry(retry("getKucoinHistOrders"))
            .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
            .call()
    );
  }

  public OrderCancelResponse kucoinCancelAllOrders(String symbol) throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () -> decorateApiCall(
            () ->
                orderApi.cancelOrders(
                    apiKey,
                    digest,
                    nonceFactory,
                    passphrase,
                    apiKeyVersion,
                    symbol))
            .withRetry(retry("kucoinCancelAllOrders"))
            .withRateLimiter(rateLimiter(CANCEL_ALL_ORDERS_RATE_LIMITER))
            .call()
    );
  }

  public OrderCancelResponse kucoinCancelOrder(String orderId) throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () -> decorateApiCall(
            () ->
                orderApi.cancelOrder(
                    apiKey,
                    digest,
                    nonceFactory,
                    passphrase,
                    apiKeyVersion,
                    orderId))
            .withRetry(retry("kucoinCancelOrder"))
            .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
            .call()
    );
  }

  public OrderCancelByClientOrderIdResponse kucoinCancelOrderByClientOrderId(String clientOrderId) throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () -> decorateApiCall(
            () ->
                orderCancelByClientOrderIdAPI.cancelOrderByClientOrderId(
                    apiKey,
                    digest,
                    nonceFactory,
                    passphrase,
                    apiKeyVersion,
                    clientOrderId))
            .withRetry(retry("kucoinCancelOrder"))
            .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
            .call()
    );
  }

  public OrderCreateResponse kucoinCreateOrder(OrderCreateApiRequest opsRequest)
      throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () -> decorateApiCall(
            () ->
                orderApi.createOrder(
                    apiKey,
                    digest,
                    nonceFactory,
                    passphrase,
                    apiKeyVersion,
                    opsRequest))
            .withRetry(retry("kucoinCreateOrder"))
            .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
            .call()
    );
  }

  public List<OrderResponse> getKucoinRecentOrders() throws IOException {
    this.checkAuthenticated();
    return classifyingExceptions(
        () -> decorateApiCall(
            () ->
                limitOrderAPI.getRecentOrders(
                    this.apiKey,
                    this.digest,
                    this.nonceFactory,
                    this.passphrase,
                    this.apiKeyVersion))
            .withRetry(retry("getKucoinRecentOrders"))
            .withRateLimiter(rateLimiter(PRIVATE_REST_ENDPOINT_RATE_LIMITER))
            .call()
    );
  }
}
