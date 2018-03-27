package org.knowm.xchange.bitcoincore;

import java.math.BigDecimal;
import org.knowm.xchange.bitcoincore.dto.account.BitcoinCoreBalanceResponse;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

public class BitcoinCoreAdapters {
  public static AccountInfo adaptAccountInfo(
      BitcoinCoreBalanceResponse available, BitcoinCoreBalanceResponse unconfirmed) {
    BigDecimal total = available.getAmount().add(unconfirmed.getAmount());
    Balance btc = new Balance(Currency.BTC, total, available.getAmount(), unconfirmed.getAmount());
    Wallet wallet = new Wallet(btc);
    return new AccountInfo(wallet);
  }
}
