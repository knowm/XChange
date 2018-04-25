package org.knowm.xchange.bitbay.v3.service;

import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.bitbay.v3.BitbayExchange;
import org.knowm.xchange.bitbay.v3.dto.BitbayBalances.BitbayBalance;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance.Builder;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class BitbayAccountService extends BitbayAccountServiceRaw implements AccountService {
  public BitbayAccountService(BitbayExchange bitbayExchange) {
    super(bitbayExchange);
  }

  @Override
  public AccountInfo getAccountInfo() {
    List<Wallet> wallets = new ArrayList<>();

    for (BitbayBalance balance : balances()) {
      Wallet wallet =
          Wallet.build(
              balance.getId(),
              new Builder()
                  .setCurrency(Currency.valueOf(balance.getCurrency()))
                  .setTotal(balance.getTotalFunds())
                  .setAvailable(balance.getAvailableFunds())
                  .setFrozen(balance.getLockedFunds())
                  .createBalance());
      wallets.add(wallet);
    }

    return AccountInfo.build(wallets);
  }
}
