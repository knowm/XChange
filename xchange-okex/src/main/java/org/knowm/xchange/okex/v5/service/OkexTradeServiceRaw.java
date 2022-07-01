package org.knowm.xchange.okex.v5.service;

import static org.knowm.xchange.okex.v5.OkexAuthenticated.amendBatchOrderPath;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.amendOrderPath;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.cancelBatchOrderPath;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.cancelOrderPath;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.orderDetailsPath;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.placeBatchOrderPath;
import static org.knowm.xchange.okex.v5.OkexAuthenticated.placeOrderPath;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.okex.v5.OkexExchange;
import org.knowm.xchange.okex.v5.dto.OkexException;
import org.knowm.xchange.okex.v5.dto.OkexResponse;
import org.knowm.xchange.okex.v5.dto.trade.OkexAmendOrderRequest;
import org.knowm.xchange.okex.v5.dto.trade.OkexCancelOrderRequest;
import org.knowm.xchange.okex.v5.dto.trade.OkexOrderDetails;
import org.knowm.xchange.okex.v5.dto.trade.OkexOrderRequest;
import org.knowm.xchange.okex.v5.dto.trade.OkexOrderResponse;
import org.knowm.xchange.utils.DateUtils;

/** Author: Max Gao (gaamox@tutanota.com) Created: 08-06-2021 */
public class OkexTradeServiceRaw extends OkexBaseService {
  public OkexTradeServiceRaw(OkexExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
  }

  public OkexResponse<List<OkexOrderDetails>> getOkexPendingOrder(
      String instrumentType,
      String underlying,
      String instrumentId,
      String orderType,
      String state,
      String after,
      String before,
      String limit)
      throws IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.getPendingOrders(
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated"),
                      instrumentType,
                      underlying,
                      instrumentId,
                      orderType,
                      state,
                      after,
                      before,
                      limit))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexOrderDetails>> getOkexOrder(String instrumentId, String orderId)
      throws IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.getOrderDetails(
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated"),
                      instrumentId,
                      orderId,
                      null))
          .withRateLimiter((rateLimiter(orderDetailsPath)))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  public OkexResponse<List<OkexOrderDetails>> getOrderHistory(
      String instrumentType,
      String instrumentId,
      String orderType,
      String after,
      String before,
      String limit)
      throws IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.getOrderHistory(
                      instrumentType,
                      instrumentId,
                      orderType,
                      "filled",
                      after,
                      before,
                      limit,
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated")))
          .withRateLimiter((rateLimiter(orderDetailsPath)))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  /** https://www.okex.com/docs-v5/en/#rest-api-trade-place-order */
  public OkexResponse<List<OkexOrderResponse>> placeOkexOrder(OkexOrderRequest order)
      throws IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.placeOrder(
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated"),
                      order))
          .withRateLimiter(rateLimiter(placeOrderPath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  /** https://www.okex.com/docs-v5/en/#rest-api-trade-place-multiple-orders */
  public OkexResponse<List<OkexOrderResponse>> placeOkexOrder(List<OkexOrderRequest> orders)
      throws IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.placeBatchOrder(
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated"),
                      orders))
          .withRateLimiter(rateLimiter(placeBatchOrderPath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  /** https://www.okex.com/docs-v5/en/#rest-api-trade-cancel-order */
  public OkexResponse<List<OkexOrderResponse>> cancelOkexOrder(OkexCancelOrderRequest order)
      throws IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.cancelOrder(
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated"),
                      order))
          .withRateLimiter(rateLimiter(cancelOrderPath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  /** https://www.okex.com/docs-v5/en/#rest-api-trade-cancel-multiple-orders */
  public OkexResponse<List<OkexOrderResponse>> cancelOkexOrder(List<OkexCancelOrderRequest> orders)
      throws IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.cancelBatchOrder(
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated"),
                      orders))
          .withRateLimiter(rateLimiter(cancelBatchOrderPath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  /** https://www.okex.com/docs-v5/en/#rest-api-trade-amend-order */
  public OkexResponse<List<OkexOrderResponse>> amendOkexOrder(OkexAmendOrderRequest order)
      throws IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.amendOrder(
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated"),
                      order))
          .withRateLimiter(rateLimiter(amendOrderPath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }

  /** https://www.okex.com/docs-v5/en/#rest-api-trade-amend-multiple-orders */
  public OkexResponse<List<OkexOrderResponse>> amendOkexOrder(List<OkexAmendOrderRequest> orders)
      throws IOException {
    try {
      return decorateApiCall(
              () ->
                  okexAuthenticated.amendBatchOrder(
                      exchange.getExchangeSpecification().getApiKey(),
                      signatureCreator,
                      DateUtils.toUTCISODateString(new Date()),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("passphrase"),
                      (String)
                          exchange
                              .getExchangeSpecification()
                              .getExchangeSpecificParametersItem("simulated"),
                      orders))
          .withRateLimiter(rateLimiter(amendBatchOrderPath))
          .call();
    } catch (OkexException e) {
      throw handleError(e);
    }
  }
}
