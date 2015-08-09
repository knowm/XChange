package com.xeiam.xchange.taurus.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsSorted;
import com.xeiam.xchange.taurus.TaurusAuthenticated;
import com.xeiam.xchange.taurus.dto.trade.TaurusOrder;
import com.xeiam.xchange.taurus.dto.trade.TaurusUserTransaction;
import com.xeiam.xchange.taurus.service.TaurusDigest;

import si.mazi.rescu.RestProxyFactory;

public class TaurusTradeServiceRaw extends TaurusBasePollingService {

  private final TaurusAuthenticated taurusAuthenticated;
  private final TaurusDigest signatureCreator;

  public TaurusTradeServiceRaw(Exchange exchange) {
    super(exchange);
    this.taurusAuthenticated = RestProxyFactory.createProxy(TaurusAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = TaurusDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public TaurusOrder[] getTaurusOpenOrders() throws IOException {
    return taurusAuthenticated.getOpenOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
  }

  public TaurusOrder sellTaurusOrder(BigDecimal tradableAmount, BigDecimal price) throws IOException {
    return taurusAuthenticated.sell(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), tradableAmount,
        price);
  }

  public TaurusOrder buyTaurusOrder(BigDecimal tradableAmount, BigDecimal price) throws IOException {
    return taurusAuthenticated.buy(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), tradableAmount,
        price);
  }

  public boolean cancelTaurusOrder(String orderId) throws IOException {
    return taurusAuthenticated.cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), orderId);
  }

  public TaurusUserTransaction[] getTaurusUserTransactions(Integer offset, Integer limit, TradeHistoryParamsSorted.Order sort) throws IOException {
    return taurusAuthenticated.getUserTransactions(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(),
        limit, offset, sort);
  }
}
