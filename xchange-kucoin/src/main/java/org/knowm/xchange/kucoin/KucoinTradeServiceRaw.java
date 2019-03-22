package org.knowm.xchange.kucoin;

import static org.knowm.xchange.kucoin.KucoinExceptionClassifier.classifyingExceptions;

import java.io.IOException;
import org.knowm.xchange.kucoin.dto.request.OrderCreateApiRequest;
import org.knowm.xchange.kucoin.dto.response.OrderCancelResponse;
import org.knowm.xchange.kucoin.dto.response.OrderCreateResponse;
import org.knowm.xchange.kucoin.dto.response.OrderResponse;
import org.knowm.xchange.kucoin.dto.response.Pagination;
import org.knowm.xchange.kucoin.dto.response.TradeResponse;

public class KucoinTradeServiceRaw extends KucoinBaseService {

  protected KucoinTradeServiceRaw(KucoinExchange exchange) {
    super(exchange);
  }

  public Pagination<OrderResponse> getKucoinOpenOrders(String symbol, int page, int pageSize)
      throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () ->
            orderApi.queryOrders(
                apiKey,
                digest,
                nonceFactory,
                passphrase,
                symbol,
                null,
                null,
                "active",
                null,
                null,
                pageSize,
                page));
  }

  public Pagination<TradeResponse> getKucoinFills(String symbol, int page, int pageSize)
      throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () ->
            fillApi.queryTrades(
                apiKey,
                digest,
                nonceFactory,
                passphrase,
                symbol,
                null,
                null,
                null,
                null,
                null,
                pageSize,
                page));
  }

  public OrderCancelResponse kucoinCancelAllOrders(String symbol) throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () -> orderApi.cancelOrders(apiKey, digest, nonceFactory, passphrase, symbol));
  }

  public OrderCancelResponse kucoinCancelOrder(String orderId) throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () -> orderApi.cancelOrder(apiKey, digest, nonceFactory, passphrase, orderId));
  }

  public OrderCreateResponse kucoinCreateOrder(OrderCreateApiRequest opsRequest)
      throws IOException {
    checkAuthenticated();
    return classifyingExceptions(
        () -> orderApi.createOrder(apiKey, digest, nonceFactory, passphrase, opsRequest));
  }
}
