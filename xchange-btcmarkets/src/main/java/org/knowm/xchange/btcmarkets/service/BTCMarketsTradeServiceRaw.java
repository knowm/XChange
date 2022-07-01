package org.knowm.xchange.btcmarkets.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.btcmarkets.dto.BTCMarketsBaseResponse;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsCancelOrderRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOpenOrdersRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrder;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrderDetailsRequest;
import org.knowm.xchange.btcmarkets.dto.trade.BTCMarketsOrders;
import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsPlaceOrderRequest;
import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsPlaceOrderResponse;
import org.knowm.xchange.btcmarkets.dto.v3.trade.BTCMarketsTradeHistoryResponse;
import org.knowm.xchange.currency.CurrencyPair;

public class BTCMarketsTradeServiceRaw extends BTCMarketsBaseService {

  public BTCMarketsTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BTCMarketsPlaceOrderResponse placeBTCMarketsOrder(
      String marketId,
      BigDecimal amount,
      BigDecimal price,
      BTCMarketsOrder.Side side,
      BTCMarketsOrder.Type type,
      String timeInForce,
      boolean postOnly,
      String clientOrderId)
      throws IOException {
    return btcmv3.placeOrder(
        exchange.getExchangeSpecification().getApiKey(),
        nonceFactory,
        this.signerV3,
        new BTCMarketsPlaceOrderRequest(
            marketId,
            price.toPlainString(),
            amount.toPlainString(),
            type.toString(),
            side.toString(),
            null,
            null,
            timeInForce,
            postOnly,
            null,
            clientOrderId));
  }

  public BTCMarketsOrders getBTCMarketsOpenOrders(
      CurrencyPair currencyPair, Integer limit, Long since) throws IOException {
    BTCMarketsOpenOrdersRequest request =
        new BTCMarketsOpenOrdersRequest(
            currencyPair.counter.getCurrencyCode(),
            currencyPair.base.getCurrencyCode(),
            limit,
            since);
    return btcm.getOpenOrders(
        exchange.getExchangeSpecification().getApiKey(), nonceFactory, signerV1, request);
  }

  public BTCMarketsBaseResponse cancelBTCMarketsOrder(Long orderId) throws IOException {
    return btcm.cancelOrder(
        exchange.getExchangeSpecification().getApiKey(),
        nonceFactory,
        signerV1,
        new BTCMarketsCancelOrderRequest(orderId));
  }

  public List<BTCMarketsTradeHistoryResponse> getBTCMarketsUserTransactions(
      CurrencyPair currencyPair, String before, String after, Integer limit) throws IOException {
    String marketId = null;
    if (currencyPair != null) {
      marketId = currencyPair.base.getCurrencyCode() + "-" + currencyPair.counter.getCurrencyCode();
    }
    return btcmv3.trades(
        exchange.getExchangeSpecification().getApiKey(),
        nonceFactory,
        signerV3,
        marketId,
        before,
        after,
        limit);
  }

  public BTCMarketsOrders getOrderDetails(List<Long> orderIds) throws IOException {
    BTCMarketsOrderDetailsRequest request = new BTCMarketsOrderDetailsRequest(orderIds);
    return btcm.getOrderDetails(
        exchange.getExchangeSpecification().getApiKey(), nonceFactory, signerV1, request);
  }

  private String newReqId() {
    return UUID.randomUUID().toString();
  }
}
