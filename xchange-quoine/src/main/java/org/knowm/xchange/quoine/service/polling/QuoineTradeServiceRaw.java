package org.knowm.xchange.quoine.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.quoine.QuoineUtils;
import org.knowm.xchange.quoine.dto.trade.QuoineNewMarginOrderRequest;
import org.knowm.xchange.quoine.dto.trade.QuoineNewOrderRequest;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderDetailsResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrderResponse;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersList;

import si.mazi.rescu.HttpStatusIOException;

/**
 * @author gnandiga
 */
public class QuoineTradeServiceRaw extends QuoineBasePollingService {

  private boolean useMargin;
  private int leverageLevel;

  /**
   * @param exchange
   */
  public QuoineTradeServiceRaw(Exchange exchange, boolean useMargin) {

    super(exchange);

    this.useMargin = useMargin;

    if (useMargin) {
      leverageLevel = (Integer) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("Leverage_Level");
    } else {
      leverageLevel = 0;
    }
  }

  public QuoineOrderResponse placeLimitOrder(CurrencyPair currencyPair, String type, BigDecimal tradableAmount, BigDecimal price) throws IOException {

    QuoineNewOrderRequest quoineNewOrderRequest = useMargin
        ? new QuoineNewMarginOrderRequest("limit", QuoineUtils.toPairString(currencyPair), type, tradableAmount, price, leverageLevel)
        : new QuoineNewOrderRequest("limit", QuoineUtils.toPairString(currencyPair), type, tradableAmount, price);
    try {
      return quoine.placeOrder(contentType, contentMD5Creator, getDate(), getNonce(), signatureCreator, quoineNewOrderRequest);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public QuoineOrderResponse placeMarketOrder(CurrencyPair currencyPair, String type, BigDecimal tradableAmount) throws IOException {

    QuoineNewOrderRequest quoineNewOrderRequest = useMargin
        ? new QuoineNewMarginOrderRequest("market", QuoineUtils.toPairString(currencyPair), type, tradableAmount, null, leverageLevel)
        : new QuoineNewOrderRequest("market", QuoineUtils.toPairString(currencyPair), type, tradableAmount, null);
    try {
      return quoine.placeOrder(contentType, contentMD5Creator, getDate(), getNonce(), signatureCreator, quoineNewOrderRequest);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public QuoineOrderResponse cancelQuoineOrder(String orderID) throws IOException {

    try {
      return quoine.cancelOrder(contentType, contentMD5Creator, getDate(), getNonce(), signatureCreator, orderID);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public QuoineOrderDetailsResponse getQuoineOrderDetails(String orderID) throws IOException {

    try {
      return quoine.orderDetails(contentType, contentMD5Creator, getDate(), getNonce(), signatureCreator, orderID);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public QuoineOrdersList listQuoineOrders(String currencyPair) throws IOException {

    try {
      return quoine.listOrders(contentType, contentMD5Creator, getDate(), getNonce(), signatureCreator, currencyPair);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }
}
