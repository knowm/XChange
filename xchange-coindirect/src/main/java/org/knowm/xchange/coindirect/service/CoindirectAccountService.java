package org.knowm.xchange.coindirect.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.CoindirectAdapters;
import org.knowm.xchange.coindirect.dto.account.CoindirectWallet;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.service.account.AccountService;

public class CoindirectAccountService extends CoindirectAccountServiceRaw
    implements AccountService {
  /**
   * Constructor
   *
   * @param exchange
   */
  public CoindirectAccountService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    List<CoindirectWallet> coindirectWallets = listCoindirectWallets(1000);

    Wallet wallet;
    Balance balance;

    List<Wallet> wallets = new ArrayList<>();

    for (CoindirectWallet coindirectWallet : coindirectWallets) {
      balance =
          new Balance(
              CoindirectAdapters.toCurrency(coindirectWallet.currency.code),
              coindirectWallet.balance);
      wallet =
          new Wallet(
              String.valueOf(coindirectWallet.id),
              coindirectWallet.description,
              Arrays.asList(balance));
      wallets.add(wallet);
    }

    return new AccountInfo(wallets);
  }
}
