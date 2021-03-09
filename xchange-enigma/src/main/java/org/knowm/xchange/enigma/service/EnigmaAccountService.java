package org.knowm.xchange.enigma.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.enigma.EnigmaAdapters;
import org.knowm.xchange.enigma.dto.trade.EnigmaWithdrawFundsRequest;
import org.knowm.xchange.service.account.AccountService;

public class EnigmaAccountService extends EnigmaAccountServiceRaw implements AccountService {

  public EnigmaAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return EnigmaAdapters.adaptAccountInfo(
        getBalance(), this.exchange.getExchangeSpecification().getUserName());
  }

  @Override
  public String withdrawFunds(Currency currency, BigDecimal amount, String address)
      throws IOException {
    EnigmaWithdrawFundsRequest fundsRequest = new EnigmaWithdrawFundsRequest();
    fundsRequest.setAmount(amount);
    fundsRequest.setCurrency(currency.getCurrencyCode());
    fundsRequest.setWithdrawalTypeId(1);
    fundsRequest.setAddress(address);
    return super.withdrawal(fundsRequest).getWithdrawalKey();
  }

  @Override
  public String requestDepositAddress(Currency currency, String... args) throws IOException {
    List<Object> address = super.requestDepositAddress(currency);
    return address.isEmpty() ? "" : address.get(0).toString();
  }
}
