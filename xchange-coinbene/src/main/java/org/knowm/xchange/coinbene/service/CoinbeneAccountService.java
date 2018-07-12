package org.knowm.xchange.coinbene.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbene.dto.CoinbeneAdapters;
import org.knowm.xchange.coinbene.dto.account.CoinbeneCoinBalances;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.service.account.AccountService;

public class CoinbeneAccountService extends CoinbeneAccountServiceRaw implements AccountService {

  public static final String EXCHANGE_ACCOUNT = "exchange";

  public CoinbeneAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    // Account name by default
    return getAccountInfo(EXCHANGE_ACCOUNT);
  }

  public AccountInfo getAccountInfo(String account) throws IOException {
    CoinbeneCoinBalances balances = getCoinbeneBalances(account);

    return CoinbeneAdapters.adaptAccountInfo(balances);
  }
}
