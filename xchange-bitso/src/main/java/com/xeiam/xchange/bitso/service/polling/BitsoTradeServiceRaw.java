package com.xeiam.xchange.bitso.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitso.BitsoAuthenticated;
import com.xeiam.xchange.bitso.dto.trade.BitsoOrder;
import com.xeiam.xchange.bitso.dto.trade.BitsoUserTransaction;
import com.xeiam.xchange.bitso.service.BitsoDigest;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author Piotr Ładyżyński
 */
public class BitsoTradeServiceRaw extends BitsoBasePollingService {

  private final BitsoAuthenticated bitsoAuthenticated;
  private final BitsoDigest signatureCreator;

  /**
   * @param exchange
   */
  public BitsoTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitsoAuthenticated = RestProxyFactory.createProxy(BitsoAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = BitsoDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public BitsoOrder[] getBitsoOpenOrders() throws IOException {

    return bitsoAuthenticated.getOpenOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
  }

  public BitsoOrder sellBitsoOrder(BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return bitsoAuthenticated.sell(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), tradableAmount,
        price);
  }

  public BitsoOrder buyBitoOrder(BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return bitsoAuthenticated.buy(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), tradableAmount,
        price);
  }

  public boolean cancelBitsoOrder(String orderId) throws IOException {

    return bitsoAuthenticated.cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), orderId);
  }

  public BitsoUserTransaction[] getBitsoUserTransactions(Long numberOfTransactions) throws IOException {

    return bitsoAuthenticated.getUserTransactions(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        numberOfTransactions);
  }

  public BitsoUserTransaction[] getBitsoUserTransactions(Long numberOfTransactions, Long offset, String sort) throws IOException {

    return bitsoAuthenticated.getUserTransactions(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        numberOfTransactions, offset, sort);
  }

}
