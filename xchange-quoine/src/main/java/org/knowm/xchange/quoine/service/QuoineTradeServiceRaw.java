package org.knowm.xchange.quoine.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.quoine.dto.trade.QuoineExecution;
import org.knowm.xchange.quoine.dto.trade.QuoineExecutionsResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineNewMarginOrderRequest;
import org.knowm.xchange.quoine.dto.trade.QuoineNewOrderRequest;
import org.knowm.xchange.quoine.dto.trade.QuoineNewOrderRequestWrapper;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderDetailsResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersList;
import org.knowm.xchange.quoine.dto.trade.QuoineTradesResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineTransaction;
import org.knowm.xchange.quoine.dto.trade.QuoineTransactionsResponse;

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

    int productId = productId(currencyPair);

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

    int productId = productId(currencyPair);

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

  public List<QuoineExecution> executions(CurrencyPair currencyPair, Integer limit, Integer page) throws IOException {
    int productId = productId(currencyPair);
    QuoineExecutionsResponse response = quoine.executions(QUOINE_API_VERSION, signatureCreator, contentType, productId, limit, page, 1);
    return response.models;
  }

  public List<QuoineTrade> trades(Currency fundingCurrency, Integer limit, Integer page) throws IOException {
    QuoineTradesResponse response = quoine.trades(QUOINE_API_VERSION, signatureCreator, contentType, fundingCurrency == null ? null : fundingCurrency.getCurrencyCode(), "null", limit, page);
    return response.models;
  }

  public List<QuoineTransaction> transactions(Currency currency, Integer limit, Integer page) throws IOException {
    QuoineTransactionsResponse transactions = quoine.transactions(QUOINE_API_VERSION, signatureCreator, contentType, currency == null ? null : currency.getCurrencyCode(), limit, page);
    return transactions.models;
  }

}
