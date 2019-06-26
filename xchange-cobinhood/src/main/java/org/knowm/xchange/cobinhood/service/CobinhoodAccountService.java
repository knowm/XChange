package org.knowm.xchange.cobinhood.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cobinhood.CobinhoodAdapters;
import org.knowm.xchange.cobinhood.dto.account.CobinhoodCoinBalances;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

public class CobinhoodAccountService extends CobinhoodAccountServiceRaw implements AccountService {

  public CobinhoodAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    CobinhoodCoinBalances balances = getCobinhoodBalances();

    return CobinhoodAdapters.adaptAccountInfo(balances);
  }
}
