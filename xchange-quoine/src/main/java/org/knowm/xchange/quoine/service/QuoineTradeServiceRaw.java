package org.knowm.xchange.quoine.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.quoine.QuoineUtils;
import org.knowm.xchange.quoine.dto.trade.QuoineNewMarginOrderRequest;
import org.knowm.xchange.quoine.dto.trade.QuoineNewOrderRequest;
import org.knowm.xchange.quoine.dto.trade.QuoineNewOrderRequestWrapper;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderDetailsResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersList;

import si.mazi.rescu.HttpStatusIOException;

/**
 * @author gnandiga
 */
public class QuoineTradeServiceRaw extends QuoineBaseService {

  private boolean useMargin;
  private int leverageLevel;

  /**
   * @param exchange
   */
  public QuoineTradeServiceRaw(Exchange exchange, boolean useMargin) {

    super(exchange);

    this.useMargin = useMargin;

    if (useMargin) {
      leverageLevel = Integer.valueOf((String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("Leverage_Level"));
    } else {
      leverageLevel = 0;
    }
  }

  public QuoineOrderResponse placeLimitOrder(CurrencyPair currencyPair, String type, BigDecimal tradableAmount, BigDecimal price) throws IOException {

    int productId = QuoineUtils.toID(currencyPair);

    QuoineNewOrderRequest quoineNewOrderRequest = useMargin
        ? new QuoineNewMarginOrderRequest("limit", productId, type, tradableAmount, price, leverageLevel, currencyPair.counter.getCurrencyCode())
        : new QuoineNewOrderRequest("limit", productId, type, tradableAmount, price);
    try {
      return quoine.placeOrder(QUOINE_API_VERSION, signatureCreator, contentType, new QuoineNewOrderRequestWrapper(quoineNewOrderRequest));
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public QuoineOrderResponse placeMarketOrder(CurrencyPair currencyPair, String type, BigDecimal tradableAmount) throws IOException {

    int productId = QuoineUtils.toID(currencyPair);

    QuoineNewOrderRequest quoineNewOrderRequest = useMargin
        ? new QuoineNewMarginOrderRequest("market", productId, type, tradableAmount, null, leverageLevel, currencyPair.counter.getCurrencyCode())
        : new QuoineNewOrderRequest("market", productId, type, tradableAmount, null);
    try {
      return quoine.placeOrder(QUOINE_API_VERSION, signatureCreator, contentType, new QuoineNewOrderRequestWrapper(quoineNewOrderRequest));
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public QuoineOrderResponse cancelQuoineOrder(String orderID) throws IOException {

    try {
      return quoine.cancelOrder(QUOINE_API_VERSION, signatureCreator, contentType, orderID);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public QuoineOrderDetailsResponse getQuoineOrderDetails(String orderID) throws IOException {

    try {
      return quoine.orderDetails(QUOINE_API_VERSION, signatureCreator, contentType, orderID);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public QuoineOrdersList listQuoineOrders() throws IOException {

    try {
      return quoine.listOrders(QUOINE_API_VERSION, signatureCreator, contentType);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }
}
