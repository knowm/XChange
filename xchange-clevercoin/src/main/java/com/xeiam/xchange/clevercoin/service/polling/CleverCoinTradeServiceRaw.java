package com.xeiam.xchange.clevercoin.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.clevercoin.CleverCoinAuthenticated;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinCancelOrder;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinOpenOrder;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinOrder;
import com.xeiam.xchange.clevercoin.dto.trade.CleverCoinUserTransaction;
import com.xeiam.xchange.clevercoin.service.CleverCoinDigest;

import si.mazi.rescu.RestProxyFactory;

/**
 * @author gnandiga
 */
public class CleverCoinTradeServiceRaw extends CleverCoinBasePollingService {

  private final CleverCoinAuthenticated cleverCoinAuthenticated;
  private final CleverCoinDigest signatureCreator;

  /**
   * @param exchange
   */
  public CleverCoinTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.cleverCoinAuthenticated = RestProxyFactory.createProxy(CleverCoinAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = CleverCoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getApiKey());
  }

  public CleverCoinOrder[] getCleverCoinOpenOrders() throws IOException {

    return cleverCoinAuthenticated.getOpenOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
  }

  public CleverCoinOpenOrder createCleverCoinOrder(String type, BigDecimal tradableAmount, BigDecimal price) throws IOException {

    return cleverCoinAuthenticated.createLimitedOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        type, tradableAmount, price);
  }

  public CleverCoinCancelOrder cancelCleverCoinOrder(int orderId) throws IOException {

    return cleverCoinAuthenticated.cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        orderId);
  }

  public CleverCoinUserTransaction[] getCleverCoinUserTransactions(int count) throws IOException {

    return cleverCoinAuthenticated.getUserTransactions(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        count);
  }

}
