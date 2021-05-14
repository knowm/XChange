package org.knowm.xchange.ftx.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.ftx.FtxAdapters;
import org.knowm.xchange.service.account.AccountService;

public class FtxAccountService extends FtxAccountServiceRaw implements AccountService {

  public FtxAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    return FtxAdapters.adaptAccountInfo(getFtxAccountInformation(), getFtxWalletBalances());
  }
}
