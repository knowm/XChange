package org.knowm.xchange.tradeogre.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class TradeOgreAccountService extends TradeOgreAccountServiceRaw implements AccountService {
  public TradeOgreAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    Wallet wallet = Wallet.Builder.from(getTradeOgreBalances()).build();
    return new AccountInfo(wallet);
  }
}
