package org.knowm.xchange.abucoins.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsBalanceInfo;
import org.knowm.xchange.exceptions.ExchangeException;

public class AbucoinsAccountServiceRaw extends AbucoinsBaseService {

  public AbucoinsAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }
  
  public AbucoinsAccount[] getAbucoinsAccounts() throws IOException {
    AbucoinsAccount[] account = abucoinsAuthenticated.getAccounts(exchange.getExchangeSpecification().getApiKey(),
                                                                  signatureCreator,
                                                                  exchange.getExchangeSpecification().getPassword(),
                                                                  signatureCreator.timestamp());
    return account;
  }

  public AbucoinsBalanceInfo getAbucoinsAccountInfo() throws IOException {
    AbucoinsBalanceInfo info = null;  //cexIOAuthenticated.getBalance(signatureCreator, new AbucoinsRequest());

    if (info.getError() != null) {
      throw new ExchangeException("Error getting balance. " + info.getError());
    }

    return info;
  }
/*
  public GHashIOHashrate getHashrate() throws IOException {
    return cexIOAuthenticated.getHashrate(signatureCreator);
  }

  public Map<String, GHashIOWorker> getWorkers() throws IOException {
    return cexIOAuthenticated.getWorkers(signatureCreator).getWorkers();
  }
    */
}
