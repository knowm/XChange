package org.knowm.xchange.exx.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exx.EXXAdapters;
import org.knowm.xchange.service.account.AccountService;

public class EXXAccountService extends EXXAccountServiceRaw implements AccountService {
  public EXXAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {

    return EXXAdapters.convertBalance(super.getExxAccountInfo());
  }
}
