package org.knowm.xchange.quadrigacx.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.quadrigacx.QuadrigaCxAuthenticated;
import org.knowm.xchange.quadrigacx.QuadrigaCxUtils;
import org.knowm.xchange.quadrigacx.dto.trade.QuadrigaCxOrder;
import org.knowm.xchange.quadrigacx.dto.trade.QuadrigaCxUserTransaction;

import si.mazi.rescu.RestProxyFactory;

public class QuadrigaCxTradeServiceRaw extends QuadrigaCxBaseService {

  private final QuadrigaCxAuthenticated quadrigacxAuthenticated;
  private final QuadrigaCxDigest signatureCreator;

  /**
   * @param exchange
   */
  public QuadrigaCxTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.quadrigacxAuthenticated = RestProxyFactory.createProxy(QuadrigaCxAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = QuadrigaCxDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public QuadrigaCxOrder[] getQuadrigaCxOpenOrders(CurrencyPair currencyPair) throws IOException {

    return quadrigacxAuthenticated.getOpenOrders(QuadrigaCxUtils.currencyPairToBook(currencyPair), exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory());
  }

  public QuadrigaCxOrder sellQuadrigaCxOrder(CurrencyPair currencyPair, BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return quadrigacxAuthenticated.sell(QuadrigaCxUtils.currencyPairToBook(currencyPair), exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory(), tradableAmount, price);
  }

  public QuadrigaCxOrder buyQuadrigaCxOrder(CurrencyPair currencyPair, BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return quadrigacxAuthenticated.buy(QuadrigaCxUtils.currencyPairToBook(currencyPair), exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory(), tradableAmount, price);
  }

  public QuadrigaCxOrder sellQuadrigaCxOrder(CurrencyPair currencyPair, BigDecimal tradableAmount) throws IOException {

    return quadrigacxAuthenticated.sell(QuadrigaCxUtils.currencyPairToBook(currencyPair), exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory(), tradableAmount, null);
  }

  public QuadrigaCxOrder buyQuadrigaCxOrder(CurrencyPair currencyPair, BigDecimal tradableAmount) throws IOException {

    return quadrigacxAuthenticated.buy(QuadrigaCxUtils.currencyPairToBook(currencyPair), exchange.getExchangeSpecification().getApiKey(),
        signatureCreator, exchange.getNonceFactory(), tradableAmount, null);
  }

  public boolean cancelQuadrigaCxOrder(String orderId) throws IOException {

    return quadrigacxAuthenticated.cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        orderId);
  }

  public QuadrigaCxUserTransaction[] getQuadrigaCxUserTransactions(CurrencyPair currencyPair, Long numberOfTransactions, Long offset,
      String sort) throws IOException {

    return quadrigacxAuthenticated.getUserTransactions(QuadrigaCxUtils.currencyPairToBook(currencyPair),
        exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), numberOfTransactions, offset, sort);
  }

}
