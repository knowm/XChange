package org.knowm.xchange.bitcoincore;

import java.math.BigDecimal;
import java.util.Arrays;
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
    Wallet wallet = Wallet.Builder.from(Arrays.asList(btc)).build();
    return new AccountInfo(wallet);
  }
}
