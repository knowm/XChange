package org.knowm.xchange.abucoins.service;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.dto.account.AbucoinsAccount;
import org.knowm.xchange.abucoins.dto.account.AbucoinsPaymentMethod;

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
  
  public AbucoinsPaymentMethod[] getPaymentMethods() throws IOException {
    return abucoinsAuthenticated.getPaymentMethods(exchange.getExchangeSpecification().getApiKey(),
						   signatureCreator,
						   exchange.getExchangeSpecification().getPassword(),
						   signatureCreator.timestamp());
  }
}
