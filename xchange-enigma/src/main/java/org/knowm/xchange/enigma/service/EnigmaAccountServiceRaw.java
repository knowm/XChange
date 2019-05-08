package org.knowm.xchange.enigma.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.enigma.dto.account.EnigmaLoginRequest;
import org.knowm.xchange.enigma.dto.account.EnigmaLoginResponse;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawalRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawalResponse;

public class EnigmaAccountServiceRaw extends EnigmaBaseService {

  public EnigmaAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public EnigmaLoginResponse login() throws IOException {
    ExchangeSpecification currentSpec = exchange.getExchangeSpecification();
    String username = currentSpec.getUserName();
    String password = currentSpec.getPassword();
    EnigmaLoginResponse loginResponse = null;

    loginResponse = this.enigmaAuthenticated.login(new EnigmaLoginRequest(username, password));
    currentSpec.setApiKey(loginResponse.getKey());
    exchange.applySpecification(currentSpec);
    return loginResponse;
  }

  public Map<String, BigDecimal> getRiskLimits() throws IOException {
    return this.enigmaAuthenticated.getAccountRiskLimits(accessToken());
  }

  public Map<String, BigDecimal> getBalance(String infrastructure) throws IOException {
    return this.enigmaAuthenticated.getBalance(accessToken(), infrastructure);
  }

  public List<EnigmaProduct> getProducts() throws IOException {
    return this.enigmaAuthenticated.getProducts(accessToken());
  }

  public List<EnigmaWithdrawalResponse> getWithdrawals() throws IOException {
    return this.enigmaAuthenticated.getAllWithdrawals(accessToken());
  }

  public EnigmaWithdrawalResponse withdrawal(EnigmaWithdrawalRequest withdrawalRequest)
      throws IOException {
    return this.enigmaAuthenticated.withdrawal(accessToken(), withdrawalRequest);
  }
}
