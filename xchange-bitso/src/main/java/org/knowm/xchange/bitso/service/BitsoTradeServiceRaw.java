package org.knowm.xchange.bitso.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitso.BitsoAuthenticated;
import org.knowm.xchange.bitso.dto.trade.BitsoOrder;
import org.knowm.xchange.bitso.dto.trade.BitsoUserTransaction;
import si.mazi.rescu.RestProxyFactory;

/** @author Piotr Ładyżyński */
public class BitsoTradeServiceRaw extends BitsoBaseService {

  private final BitsoAuthenticated bitsoAuthenticated;
  private final BitsoDigest signatureCreator;

  /** @param exchange */
  public BitsoTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitsoAuthenticated =
        RestProxyFactory.createProxy(
            BitsoAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
    this.signatureCreator =
        BitsoDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getUserName(),
            exchange.getExchangeSpecification().getApiKey());
  }

  public BitsoOrder[] getBitsoOpenOrders() throws IOException {

    return bitsoAuthenticated.getOpenOrders(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        exchange.getNonceFactory());
  }

  public BitsoOrder sellBitsoOrder(BigDecimal originalAmount, BigDecimal price) throws IOException {

    return bitsoAuthenticated.sell(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        exchange.getNonceFactory(),
        originalAmount,
        price);
  }

  public BitsoOrder buyBitoOrder(BigDecimal originalAmount, BigDecimal price) throws IOException {

    return bitsoAuthenticated.buy(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        exchange.getNonceFactory(),
        originalAmount,
        price);
  }

  public boolean cancelBitsoOrder(String orderId) throws IOException {

    return bitsoAuthenticated.cancelOrder(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        exchange.getNonceFactory(),
        orderId);
  }

  public BitsoUserTransaction[] getBitsoUserTransactions(Long numberOfTransactions)
      throws IOException {

    return bitsoAuthenticated.getUserTransactions(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        exchange.getNonceFactory(),
        numberOfTransactions);
  }

  public BitsoUserTransaction[] getBitsoUserTransactions(
      Long numberOfTransactions, Long offset, String sort) throws IOException {

    return bitsoAuthenticated.getUserTransactions(
        exchange.getExchangeSpecification().getApiKey(),
        signatureCreator,
        exchange.getNonceFactory(),
        numberOfTransactions,
        offset,
        sort);
  }
}
