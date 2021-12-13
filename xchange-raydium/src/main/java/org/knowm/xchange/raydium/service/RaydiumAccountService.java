package org.knowm.xchange.raydium.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;

public class RaydiumAccountService extends RaydiumAccountServiceRaw implements AccountService {

  public RaydiumAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return new AccountInfo();
  }
}
