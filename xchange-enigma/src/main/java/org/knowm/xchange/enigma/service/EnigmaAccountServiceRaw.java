package org.knowm.xchange.enigma.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.enigma.dto.account.EnigmaBalance;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawFundsRequest;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawal;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawalRequest;

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
    return this.enigmaAuthenticated.getAllWithdrawals(accessToken());
  }

  public EnigmaWithdrawal withdrawal(EnigmaWithdrawalRequest withdrawalRequest) {
    return this.enigmaAuthenticated.withdrawal(accessToken(), withdrawalRequest);
  }

  public EnigmaWithdrawal withdrawal(EnigmaWithdrawFundsRequest withdrawalRequest) {
    return this.enigmaAuthenticated.withdrawal(accessToken(), withdrawalRequest);
  }

  public List<Object> requestDepositAddress(Currency currency) {
    return this.enigmaAuthenticated.depositAddress(accessToken(), currency.getCurrencyCode());
  }
}
