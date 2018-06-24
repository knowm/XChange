package org.knowm.xchange.gatecoin.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.gatecoin.GatecoinAuthenticated;
import org.knowm.xchange.gatecoin.dto.trade.GatecoinPlaceOrderRequest;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinCancelOrderResult;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinOrderResult;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinPlaceOrderResult;
import org.knowm.xchange.gatecoin.dto.trade.Results.GatecoinTradeHistoryResult;
import si.mazi.rescu.RestProxyFactory;

/** @author sumedha */
public class GatecoinTradeServiceRaw extends GatecoinBaseService {
  private final GatecoinAuthenticated gatecoinAuthenticated;
  private final GatecoinDigest signatureCreator;

  public GatecoinTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.gatecoinAuthenticated =
        RestProxyFactory.createProxy(
            GatecoinAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.signatureCreator =
        GatecoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public GatecoinOrderResult getGatecoinOpenOrders() throws IOException {

    return gatecoinAuthenticated.getOpenOrders(
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, getNow());
  }

  public GatecoinPlaceOrderResult placeGatecoinOrder(
      BigDecimal originalAmount, BigDecimal price, String way, String code) throws IOException {
    GatecoinPlaceOrderRequest placeOrderRequest =
        new GatecoinPlaceOrderRequest(originalAmount, price, way, code);
    return gatecoinAuthenticated.placeOrder(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        getNow(),
        placeOrderRequest);
  }

  public GatecoinCancelOrderResult cancelGatecoinOrder(String orderId) throws IOException {

    return gatecoinAuthenticated.cancelOrder(
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, getNow(), orderId);
  }

  public GatecoinCancelOrderResult cancelAllGatecoinOrders() throws IOException {

    return gatecoinAuthenticated.cancelAllOrders(
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, getNow());
  }

  public GatecoinTradeHistoryResult getGatecoinUserTrades(Integer count, Long transactionId)
      throws IOException {
    return gatecoinAuthenticated.getUserTrades(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        getNow(),
        count,
        transactionId);
  }

  public GatecoinTradeHistoryResult getGatecoinUserTrades(int count) throws IOException {
    return gatecoinAuthenticated.getUserTrades(
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, getNow(), count);
  }

  public GatecoinTradeHistoryResult getGatecoinUserTrades() throws IOException {
    return gatecoinAuthenticated.getUserTrades(
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, getNow());
  }
}
