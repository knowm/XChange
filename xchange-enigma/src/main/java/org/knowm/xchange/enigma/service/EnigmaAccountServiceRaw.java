package org.knowm.xchange.enigma.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.enigma.dto.account.EnigmaBalance;
import org.knowm.xchange.enigma.dto.marketdata.EnigmaProduct;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawal;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawalRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class EnigmaAccountServiceRaw extends EnigmaBaseService {

  public EnigmaAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public Map<String, BigDecimal> getRiskLimits() throws IOException {
    return this.enigmaAuthenticated.getAccountRiskLimits(accessToken());
  }

  public EnigmaBalance getBalance() throws IOException {
    return new EnigmaBalance(
        this.enigmaAuthenticated.getBalance(
            accessToken(),
            this.exchange
                .getExchangeSpecification()
                .getExchangeSpecificParametersItem("infra")
                .toString()));
  }


  public List<EnigmaWithdrawal> getWithdrawals() {
    return this.enigmaAuthenticated.getAllWithdrawals(
        accessToken(),
        this.exchange
            .getExchangeSpecification()
            .getExchangeSpecificParametersItem("infra")
            .toString());
  }

  public EnigmaWithdrawal withdrawal(EnigmaWithdrawalRequest withdrawalRequest) {
    return this.enigmaAuthenticated.withdrawal(accessToken(), withdrawalRequest);
  }
}
