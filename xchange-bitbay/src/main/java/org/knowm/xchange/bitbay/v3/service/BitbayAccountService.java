package org.knowm.xchange.bitbay.v3.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.bitbay.v3.BitbayExchange;
import org.knowm.xchange.bitbay.v3.dto.BitbayBalances;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class BitbayAccountService extends BitbayAccountServiceRaw implements AccountService {
  public BitbayAccountService(BitbayExchange bitbayExchange) {
    super(bitbayExchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<Wallet> wallets = new ArrayList<>();

    for (BitbayBalances.BitbayBalance balance : balances()) {
      Wallet wallet =
          new Wallet(
              balance.getId(),
              new Balance(
                  Currency.valueOf(balance.getCurrency()),
                  balance.getTotalFunds(),
                  balance.getAvailableFunds(),
                  balance.getLockedFunds()));
      wallets.add(wallet);
    }

    return new AccountInfo(wallets);
  }
}
