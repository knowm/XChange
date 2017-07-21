package org.knowm.xchange.jubi.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.jubi.JubiAuthernticated;
import org.knowm.xchange.jubi.dto.trade.JubiOrderHistory;
import org.knowm.xchange.jubi.dto.trade.JubiOrderStatus;
import org.knowm.xchange.jubi.dto.trade.JubiOrderType;
import org.knowm.xchange.jubi.dto.trade.JubiTradeResult;
import org.knowm.xchange.service.trade.params.orders.DefaultOpenOrdersParamCurrencyPair;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Dzf on 2017/7/16.
 */
public class JubiTradeServiceRaw extends JubiBaseService {
  private final JubiAuthernticated jubiAuthernticated;
  private final ParamsDigest signatureCreator;

  public JubiTradeServiceRaw(Exchange exchange) {
    super(exchange);
    this.jubiAuthernticated = RestProxyFactory.createProxy(JubiAuthernticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = JubiPostBodyDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public JubiOrderHistory getJubiOrderHistory(CurrencyPair currencyPair, Date startTime) throws IOException {
    String coinType = currencyPair != null ? currencyPair.base.getCurrencyCode().toLowerCase() : "";
    long since = startTime != null ? startTime.getTime() : 0;
    return jubiAuthernticated.getOrderHistory(coinType, exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(), since, "all", signatureCreator);
  }

  public JubiOrderHistory getJubiOpenOrder(CurrencyPair currencyPair) throws IOException {
    String coinType = currencyPair != null ? currencyPair.base.getCurrencyCode().toLowerCase() : "";
    return jubiAuthernticated.getOrderHistory(coinType, exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(), 0, "open", signatureCreator);
  }

  public JubiTradeHistoryParams createJubiTradeHistoryParams(CurrencyPair currencyPair) {
    return new JubiTradeHistoryParams(currencyPair, null);
  }

  public JubiTradeHistoryParams createJubiTradeHistoryParams(CurrencyPair currencyPair, Date date) {
    return new JubiTradeHistoryParams(currencyPair, date);
  }

  public OpenOrdersParams createJubiOpenOrdersParams(CurrencyPair currencyPair) {
    return new DefaultOpenOrdersParamCurrencyPair(currencyPair);
  }

  public JubiOrderStatus getJubiOrderStatus(BigDecimal orderId, CurrencyPair currencyPair) throws IOException {
    String coinType = currencyPair != null ? currencyPair.base.getCurrencyCode().toLowerCase() : "";
    return jubiAuthernticated.getOrderStatus(coinType, orderId, exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(), signatureCreator);
  }

  public JubiTradeResult placeJubiOrder(CurrencyPair currencyPair, BigDecimal amount, BigDecimal price, JubiOrderType type) throws IOException {
    String coinType = currencyPair != null ? currencyPair.base.getCurrencyCode().toLowerCase() : "";
    return jubiAuthernticated.placeOrder(amount, coinType, exchange.getExchangeSpecification().getApiKey(), exchange.getNonceFactory(),
            price, type.name().toLowerCase(), signatureCreator);
  }

  public boolean cancelJubiOrder(CurrencyPair currencyPair, BigDecimal id) throws IOException {
    String coinType = currencyPair != null ? currencyPair.base.getCurrencyCode().toLowerCase() : "";
    JubiTradeResult result = jubiAuthernticated.cancelOrder(coinType, id, exchange.getExchangeSpecification().getApiKey(),
            exchange.getNonceFactory(), signatureCreator);
    return result.isSuccess();
  }
}
