package org.knowm.xchange.enigma.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.enigma.EnigmaAdapters;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;

public class EnigmaAccountService extends EnigmaAccountServiceRaw implements AccountService {

  public EnigmaAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return EnigmaAdapters.adaptAccountInfo(
        getBalance(), this.exchange.getExchangeSpecification().getUserName());
  }
}
