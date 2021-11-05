package org.knowm.xchange.ascendex.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ascendex.AscendexAdapters;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

public class AscendexAccountService extends AscendexAccountServiceRaw implements AccountService {

  public AscendexAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return AscendexAdapters.adaptAccountInfo(getAscendexCashAccountBalance());
  }
}
