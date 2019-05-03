package org.knowm.xchange.enigma.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.enigma.dto.account.EnigmaBalance;
import org.knowm.xchange.enigma.dto.account.EnigmaLoginRequest;
import org.knowm.xchange.enigma.dto.account.EnigmaLoginResponse;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.trade.EnigmaRiskLimit;
import si.mazi.rescu.HttpStatusIOException;

public class EnigmaAccountServiceRaw extends EnigmaBaseService {

  public EnigmaAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public EnigmaLoginResponse login() throws IOException {
    ExchangeSpecification currentSpec = exchange.getExchangeSpecification();
    String username = currentSpec.getUserName();
    String password = currentSpec.getPassword();
    EnigmaLoginResponse loginResponse = null;

    try {
      loginResponse = this.enigmaAuthenticated.login(new EnigmaLoginRequest(username, password));
    } catch (HttpStatusIOException httpStatusIOException) {
      throw handleError(httpStatusIOException);
    }
    currentSpec.setApiKey(loginResponse.getKey());
    exchange.applySpecification(currentSpec);
    return loginResponse;
  }

  public EnigmaRiskLimit getRiskLimits() throws IOException {
    try {
      return this.enigmaAuthenticated.getAccountRiskLimits(accessToken());
    } catch (HttpStatusIOException httpStatusIOException) {
      throw handleError(httpStatusIOException);
    }
  }

  public EnigmaBalance getBalance(String infrastructure) throws IOException {
    try {
      return this.enigmaAuthenticated.getBalance(accessToken(), infrastructure);
    } catch (HttpStatusIOException httpStatusIOException) {
      throw handleError(httpStatusIOException);
    }
  }

  public List<EnigmaProduct> getProducts() throws IOException {
    try {
      return this.enigmaAuthenticated.getProducts(accessToken());
    } catch (HttpStatusIOException httpStatusIOException) {
      throw handleError(httpStatusIOException);
    }
  }
}
