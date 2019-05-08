package org.knowm.xchange.enigma.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

public class EnigmaAccountService extends EnigmaAccountServiceRaw implements AccountService {

  public EnigmaAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    AccountInfo accountInfo =
        new AccountInfo(this.exchange.getExchangeSpecification().getUserName());
    return accountInfo;
  }
}
