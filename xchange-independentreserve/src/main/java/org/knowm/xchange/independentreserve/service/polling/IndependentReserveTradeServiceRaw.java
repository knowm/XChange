package org.knowm.xchange.independentreserve.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.independentreserve.IndependentReserveAuthenticated;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveCancelOrderRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveCancelOrderResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveOpenOrderRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveOpenOrdersResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReservePlaceLimitOrderRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReservePlaceLimitOrderResponse;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTradeHistoryRequest;
import org.knowm.xchange.independentreserve.dto.trade.IndependentReserveTradeHistoryResponse;
import org.knowm.xchange.independentreserve.service.IndependentReserveDigest;
import org.knowm.xchange.independentreserve.util.ExchangeEndpoint;

import si.mazi.rescu.RestProxyFactory;

/**
 * Author: Kamil Zbikowski Date: 4/13/15
 */
public class IndependentReserveTradeServiceRaw extends IndependentReserveBasePollingService {
  private final String TRADE_HISTORY_PAGE_SIZE = "50";
  private final IndependentReserveDigest signatureCreator;
  private final IndependentReserveAuthenticated independentReserveAuthenticated;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected IndependentReserveTradeServiceRaw(Exchange exchange) {
    super(exchange);

    this.independentReserveAuthenticated = RestProxyFactory.createProxy(IndependentReserveAuthenticated.class,
        exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = IndependentReserveDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getApiKey(), exchange.getExchangeSpecification().getSslUri());
  }

  /**
   * @param currencyPair - currency pair
   * @param pageNumber -
   * @return
   * @throws IOException
   */
  public IndependentReserveOpenOrdersResponse getIndependentReserveOpenOrders(CurrencyPair currencyPair, Integer pageNumber) throws IOException {
    if (pageNumber <= 0) {
      throw new IllegalArgumentException("Page number in IndependentReserve should be positive.");
    }
    Long nonce = exchange.getNonceFactory().createValue();
    String apiKey = exchange.getExchangeSpecification().getApiKey();
    IndependentReserveOpenOrderRequest independentReserveOpenOrderRequest = new IndependentReserveOpenOrderRequest(apiKey, nonce,
        currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), pageNumber.toString(), TRADE_HISTORY_PAGE_SIZE);

    independentReserveOpenOrderRequest.setSignature(
        signatureCreator.digestParamsToString(ExchangeEndpoint.GET_OPEN_ORDERS, nonce, independentReserveOpenOrderRequest.getParameters()));

    IndependentReserveOpenOrdersResponse openOrders = independentReserveAuthenticated.getOpenOrders(independentReserveOpenOrderRequest);

    return openOrders;
  }

  public String independentReservePlaceLimitOrder(CurrencyPair currencyPair, Order.OrderType type, BigDecimal limitPrice, BigDecimal tradableAmount)
      throws IOException {
    Long nonce = exchange.getNonceFactory().createValue();
    String apiKey = exchange.getExchangeSpecification().getApiKey();

    String orderType = null;
    if (type == Order.OrderType.ASK) {
      orderType = "LimitOffer";
    } else if (type == Order.OrderType.BID) {
      orderType = "LimitBid";
    }

    IndependentReservePlaceLimitOrderRequest independentReservePlaceLimitOrderRequest = new IndependentReservePlaceLimitOrderRequest(apiKey, nonce,
        currencyPair.base.getCurrencyCode(), currencyPair.counter.getCurrencyCode(), orderType, limitPrice.toString(), tradableAmount.toString());
    independentReservePlaceLimitOrderRequest.setSignature(
        signatureCreator.digestParamsToString(ExchangeEndpoint.PLACE_LIMIT_ORDER, nonce, independentReservePlaceLimitOrderRequest.getParameters()));

    IndependentReservePlaceLimitOrderResponse independentReservePlaceLimitOrderResponse = independentReserveAuthenticated
        .placeLimitOrder(independentReservePlaceLimitOrderRequest);

    return independentReservePlaceLimitOrderResponse.getOrderGuid();
  }

  public boolean independentReserveCancelOrder(String orderId) throws IOException {
    Long nonce = exchange.getNonceFactory().createValue();
    String apiKey = exchange.getExchangeSpecification().getApiKey();

    IndependentReserveCancelOrderRequest independentReserveCancelOrderRequest = new IndependentReserveCancelOrderRequest(apiKey, nonce, orderId);

    independentReserveCancelOrderRequest.setSignature(
        signatureCreator.digestParamsToString(ExchangeEndpoint.CANCEL_ORDER, nonce, independentReserveCancelOrderRequest.getParameters()));

    IndependentReserveCancelOrderResponse independentReserveCancelOrderResponse = independentReserveAuthenticated
        .cancelOrder(independentReserveCancelOrderRequest);

    if (independentReserveCancelOrderResponse.getStatus() != null) {
      return independentReserveCancelOrderResponse.getStatus().equals("Cancelled")
          || independentReserveCancelOrderResponse.getStatus().equals("PartiallyFilledAndCancelled")
          || independentReserveCancelOrderResponse.getStatus().equals("Expired")
          || independentReserveCancelOrderResponse.getStatus().equals("Expired");
    } else {
      return false;
    }
  }

  public IndependentReserveTradeHistoryResponse getIndependentReserveTradeHistory(Integer pageNumber) throws IOException {
    if (pageNumber <= 0) {
      throw new IllegalArgumentException("Page number in IndependentReserve should be positive.");
    }
    Long nonce = exchange.getNonceFactory().createValue();
    String apiKey = exchange.getExchangeSpecification().getApiKey();

    IndependentReserveTradeHistoryRequest independentReserveTradeHistoryRequest = new IndependentReserveTradeHistoryRequest(apiKey, nonce,
        pageNumber.toString(), TRADE_HISTORY_PAGE_SIZE);

    independentReserveTradeHistoryRequest.setSignature(
        signatureCreator.digestParamsToString(ExchangeEndpoint.GET_TRADES, nonce, independentReserveTradeHistoryRequest.getParameters()));

    IndependentReserveTradeHistoryResponse trades = independentReserveAuthenticated.getTradeHistory(independentReserveTradeHistoryRequest);

    return trades;
  }

}
