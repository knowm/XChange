package org.knowm.xchange.taurus.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsSorted;
import org.knowm.xchange.taurus.TaurusAuthenticated;
import org.knowm.xchange.taurus.dto.trade.TaurusOrder;
import org.knowm.xchange.taurus.dto.trade.TaurusUserTransaction;

import si.mazi.rescu.RestProxyFactory;

public class TaurusTradeServiceRaw extends TaurusBaseService {

  private final TaurusAuthenticated taurusAuthenticated;
  private final TaurusDigest signatureCreator;

  public TaurusTradeServiceRaw(Exchange exchange) {
    super(exchange);
    this.taurusAuthenticated = RestProxyFactory.createProxy(TaurusAuthenticated.class, exchange.getExchangeSpecification().getSslUri(),
        getClientConfig());
    this.signatureCreator = TaurusDigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public TaurusOrder[] getTaurusOpenOrders() throws IOException {
    return taurusAuthenticated.getOpenOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
  }

  public TaurusOrder sellTaurusOrder(BigDecimal originalAmount, BigDecimal price) throws IOException {
    return taurusAuthenticated.sell(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), originalAmount,
        price);
  }

  public TaurusOrder buyTaurusOrder(BigDecimal originalAmount, BigDecimal price) throws IOException {
    return taurusAuthenticated.buy(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), originalAmount,
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
