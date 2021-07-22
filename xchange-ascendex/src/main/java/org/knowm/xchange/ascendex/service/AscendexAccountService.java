package org.knowm.xchange.ascendex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.AscendexAdapters;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;

public class AscendexAccountService extends AscendexAccountServiceRaw implements AccountService {

  public AscendexAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return AscendexAdapters.adaptAccountInfo(getBitmaxCashAccountBalance());
  }
}
