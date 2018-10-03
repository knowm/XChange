package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcmarkets.BTCMarketsAuthenticated;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsBaseResponse;
import org.knowm.xchange.btcmarkets.dto.trade.*;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BTCMarketsTradeServiceRaw extends BTCMarketsBaseService {

  private final BTCMarketsAuthenticated btcm;
  private final BTCMarketsDigest signer;
  private final SynchronizedValueFactory<Long> nonceFactory;

  public BTCMarketsTradeServiceRaw(Exchange exchange) {
    super(exchange);
    final ExchangeSpecification spec = exchange.getExchangeSpecification();
    this.btcm =
        RestProxyFactory.createProxy(
            BTCMarketsAuthenticated.class, spec.getSslUri(), getClientConfig());
    this.signer = new BTCMarketsDigest(spec.getSecretKey());
    this.nonceFactory = exchange.getNonceFactory();
  }

  public BTCMarketsPlaceOrderResponse placeBTCMarketsOrder(
      CurrencyPair currencyPair,
      BigDecimal amount,
      BigDecimal price,
      BTCMarketsOrder.Side side,
      BTCMarketsOrder.Type type)
      throws IOException {
    return btcm.placeOrder(
        exchange.getExchangeSpecification().getApiKey(),
        nonceFactory,
        this.signer,
        new BTCMarketsOrder(
            amount,
            price,
            currencyPair.counter.getCurrencyCode(),
            currencyPair.base.getCurrencyCode(),
            side,
            type,
            newReqId()));
  }

  public BTCMarketsOrders getBTCMarketsOpenOrders(
      CurrencyPair currencyPair, Integer limit, Long since) throws IOException {
    BTCMarketsOpenOrdersAndTradeHistoryRequest request =
        new BTCMarketsOpenOrdersAndTradeHistoryRequest(
            currencyPair.counter.getCurrencyCode(),
            currencyPair.base.getCurrencyCode(),
            limit,
            since);
    return btcm.getOpenOrders(
        exchange.getExchangeSpecification().getApiKey(), nonceFactory, signer, request);
  }

  public BTCMarketsBaseResponse cancelBTCMarketsOrder(Long orderId) throws IOException {
    return btcm.cancelOrder(
        exchange.getExchangeSpecification().getApiKey(),
        nonceFactory,
        signer,
        new BTCMarketsCancelOrderRequest(orderId));
  }

  public BTCMarketsTradeHistory getBTCMarketsUserTransactions(
      CurrencyPair currencyPair, Integer limit, Long since) throws IOException {
    BTCMarketsOpenOrdersAndTradeHistoryRequest request =
        new BTCMarketsOpenOrdersAndTradeHistoryRequest(
            currencyPair.counter.getCurrencyCode(),
            currencyPair.base.getCurrencyCode(),
            limit,
            since);
    return btcm.getTradeHistory(
        exchange.getExchangeSpecification().getApiKey(), nonceFactory, signer, request);
  }

  public BTCMarketsOrders getOrderDetails(List<Long> orderIds) throws IOException {
    BTCMarketsOrderDetailsRequest request = new BTCMarketsOrderDetailsRequest(orderIds);
    return btcm.getOrderDetails(
        exchange.getExchangeSpecification().getApiKey(), nonceFactory, signer, request);
  }

  private String newReqId() {
    return UUID.randomUUID().toString();
  }
}
