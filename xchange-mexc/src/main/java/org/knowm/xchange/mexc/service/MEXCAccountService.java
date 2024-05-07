package org.knowm.xchange.mexc.service;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.mexc.MEXCAdapters;
import org.knowm.xchange.mexc.dto.MEXCResult;
import org.knowm.xchange.mexc.dto.account.MEXCBalance;
import org.knowm.xchange.service.account.AccountService;

public class MEXCAccountService extends MEXCAccountServiceRaw implements AccountService {

  public MEXCAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    try {
      MEXCResult<Map<String, MEXCBalance>> walletBalances = getWalletBalances();
      Map<String, MEXCBalance> walletBalancesResult = walletBalances.getData();
      return new AccountInfo(MEXCAdapters.adaptMEXCBalances(walletBalancesResult));
    } catch (MEXCException e) {
      throw new ExchangeException(e);
    }
  }
}
