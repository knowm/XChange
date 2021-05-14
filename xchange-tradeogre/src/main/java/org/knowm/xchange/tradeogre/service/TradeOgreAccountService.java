package org.knowm.xchange.tradeogre.service;

import java.io.IOException;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.tradeogre.TradeOgreExchange;

public class TradeOgreAccountService extends TradeOgreAccountServiceRaw implements AccountService {
  public TradeOgreAccountService(TradeOgreExchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    Wallet wallet = Wallet.Builder.from(getTradeOgreBalances()).build();
    return new AccountInfo(wallet);
  }
}
