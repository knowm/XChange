package org.knowm.xchange.anx.v2.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.anx.ANXUtils;
import org.knowm.xchange.anx.v2.ANXV2;
import org.knowm.xchange.anx.v2.dto.ANXException;
import org.knowm.xchange.anx.v2.dto.trade.ANXGenericResponse;
import org.knowm.xchange.anx.v2.dto.trade.ANXOpenOrder;
import org.knowm.xchange.anx.v2.dto.trade.ANXOpenOrderWrapper;
import org.knowm.xchange.anx.v2.dto.trade.ANXOrderResultWrapper;
import org.knowm.xchange.anx.v2.dto.trade.ANXTradeResultWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.utils.Assert;
import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.IRestProxyFactory;

public class ANXTradeServiceRaw extends ANXBaseService {

  private final ANXV2 anxV2;
  private final ANXV2Digest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  protected ANXTradeServiceRaw(Exchange exchange, IRestProxyFactory restProxyFactory) {

    super(exchange);

    Assert.notNull(
        exchange.getExchangeSpecification().getSslUri(),
        "Exchange specification URI cannot be null");
    this.anxV2 =
        restProxyFactory.createProxy(
            ANXV2.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    this.signatureCreator =
        ANXV2Digest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public ANXOpenOrder[] getANXOpenOrders(String baseCurrency, String counterCurrency)
      throws IOException {

    try {
      ANXOpenOrderWrapper anxOpenOrderWrapper =
          anxV2.getOpenOrders(
              ANXUtils.urlEncode(exchange.getExchangeSpecification().getApiKey()),
              signatureCreator,
              exchange.getNonceFactory(),
              baseCurrency,
              counterCurrency);
      return anxOpenOrderWrapper.getANXOpenOrders();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXOpenOrder[] getANXOpenOrders() throws IOException {

    try {
      ANXOpenOrderWrapper anxOpenOrderWrapper =
          anxV2.getOpenOrders(
              ANXUtils.urlEncode(exchange.getExchangeSpecification().getApiKey()),
              signatureCreator,
              exchange.getNonceFactory());
      return anxOpenOrderWrapper.getANXOpenOrders();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXGenericResponse placeANXMarketOrder(MarketOrder marketOrder) throws IOException {

    try {
      ANXGenericResponse anxGenericResponse =
          anxV2.placeOrder(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              marketOrder.getCurrencyPair().base.getCurrencyCode(),
              marketOrder.getCurrencyPair().counter.getCurrencyCode(),
              marketOrder.getType().equals(Order.OrderType.BID) ? "bid" : "ask",
              marketOrder.getOriginalAmount(),
              null);
      return anxGenericResponse;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXGenericResponse placeANXLimitOrder(
      CurrencyPair currencyPair, String type, BigDecimal amount, BigDecimal price)
      throws IOException {

    try {

      ANXGenericResponse anxGenericResponse =
          anxV2.placeOrder(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              currencyPair.base.getCurrencyCode(),
              currencyPair.counter.getCurrencyCode(),
              type,
              amount,
              price);

      return anxGenericResponse;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXGenericResponse cancelANXOrder(
      String orderId, String baseCurrency, String counterCurrency) throws IOException {

    try {

      ANXGenericResponse anxGenericResponse =
          anxV2.cancelOrder(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              orderId,
              baseCurrency,
              counterCurrency);
      return anxGenericResponse;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXTradeResultWrapper getExecutedANXTrades(Long from, Long to) throws IOException {

    try {

      ANXTradeResultWrapper anxTradeResultWrapper =
          anxV2.getExecutedTrades(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              from,
              to);
      return anxTradeResultWrapper;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXOrderResultWrapper getANXOrderResult(
      String orderId, String type, String baseCurrency, String counterCurrency) throws IOException {

    try {

      ANXOrderResultWrapper anxOrderResultWrapper =
          anxV2.getOrderResult(
              exchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              exchange.getNonceFactory(),
              baseCurrency,
              counterCurrency,
              orderId,
              type);
      return anxOrderResultWrapper;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }
}
