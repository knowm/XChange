package org.knowm.xchange.cexio.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.CexIOAuthenticated;
import org.knowm.xchange.cexio.dto.account.CexIOBalanceInfo;
import org.knowm.xchange.cexio.dto.account.GHashIOHashrate;
import org.knowm.xchange.cexio.dto.account.GHashIOWorker;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author timmolter
 */
public class CexIOAccountServiceRaw extends CexIOBaseService {

  public CexIOAccountServiceRaw(Exchange exchange) {
    super(exchange);
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
