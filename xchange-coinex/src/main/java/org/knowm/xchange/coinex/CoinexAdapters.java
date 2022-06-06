package org.knowm.xchange.coinex;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.coinex.dto.account.CoinexBalanceInfo;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;

public class CoinexAdapters {

  public static Wallet adaptWallet(Map<String, CoinexBalanceInfo> coinexBalances) {
    List<Balance> balances = new ArrayList<>(coinexBalances.size());
    for (Map.Entry<String, CoinexBalanceInfo> balancePair : coinexBalances.entrySet()) {
      Currency currency = new Currency(balancePair.getKey());
      BigDecimal total =
          balancePair.getValue().getAvailable().add(balancePair.getValue().getFrozen());
      Balance balance =
          new Balance(
              currency,
              total,
              balancePair.getValue().getAvailable(),
              balancePair.getValue().getFrozen());
      balances.add(balance);
    }

    return Wallet.Builder.from(balances).build();
  }
}
