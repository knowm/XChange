package org.knowm.xchange.cexio.service;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cexio.dto.CexIORequest;
import org.knowm.xchange.cexio.dto.account.CexIOBalanceInfo;
import org.knowm.xchange.cexio.dto.account.GHashIOHashrate;
import org.knowm.xchange.cexio.dto.account.GHashIOWorker;
import org.knowm.xchange.exceptions.ExchangeException;

public class CexIOAccountServiceRaw extends CexIOBaseService {

  public CexIOAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public CexIOBalanceInfo getCexIOAccountInfo() throws IOException {
    CexIOBalanceInfo info = cexIOAuthenticated.getBalance(signatureCreator, new CexIORequest());

    if (info.getError() != null) {
      throw new ExchangeException("Error getting balance. " + info.getError());
    }

    return info;
  }

  public GHashIOHashrate getHashrate() throws IOException {
    return cexIOAuthenticated.getHashrate(signatureCreator);
  }

  public Map<String, GHashIOWorker> getWorkers() throws IOException {
    return cexIOAuthenticated.getWorkers(signatureCreator).getWorkers();
  }

}
