package org.knowm.xchange.cexio.service;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIOAuthenticated;
import org.knowm.xchange.cexio.dto.account.CexIOBalanceInfo;
import org.knowm.xchange.cexio.dto.account.GHashIOHashrate;
import org.knowm.xchange.cexio.dto.account.GHashIOWorker;
import org.knowm.xchange.exceptions.ExchangeException;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

/**
 * @author timmolter
 */
public class CexIOAccountServiceRaw extends CexIOBaseService {

  private final CexIOAuthenticated cexIOAuthenticated;
  private ParamsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public CexIOAccountServiceRaw(Exchange exchange) {

    super(exchange);
    this.cexIOAuthenticated = RestProxyFactory.createProxy(CexIOAuthenticated.class, exchange.getExchangeSpecification().getSslUri());
    signatureCreator = CexIODigest.createInstance(exchange.getExchangeSpecification().getSecretKey(),
        exchange.getExchangeSpecification().getUserName(), exchange.getExchangeSpecification().getApiKey());
  }

  public CexIOBalanceInfo getCexIOAccountInfo() throws IOException {

    CexIOBalanceInfo info = cexIOAuthenticated.getBalance(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory());
    if (info.getError() != null) {
      throw new ExchangeException("Error getting balance. " + info.getError());
    }

    return info;
  }

  public GHashIOHashrate getHashrate() throws IOException {

    return cexIOAuthenticated.getHashrate(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
  }

  public Map<String, GHashIOWorker> getWorkers() throws IOException {

    return cexIOAuthenticated.getWorkers(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory()).getWorkers();
  }

}
