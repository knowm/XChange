package org.knowm.xchange.lykke.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.lykke.LykkeAdapter;
import org.knowm.xchange.service.account.AccountService;

public class LykkeAccountService extends LykkeAccountServiceRaw implements AccountService {

  public LykkeAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return LykkeAdapter.adaptAccountInfo(getWallets());
  }

}
