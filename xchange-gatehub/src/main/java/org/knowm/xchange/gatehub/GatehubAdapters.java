package org.knowm.xchange.gatehub;

import static java.math.BigDecimal.ZERO;

import java.util.ArrayList;
import java.util.Collection;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.gatehub.dto.Balance;

public class GatehubAdapters {
  public static Collection<org.knowm.xchange.dto.account.Balance> adaptBalances(Collection<Balance> ghBalances) {
    Collection<org.knowm.xchange.dto.account.Balance> balances = new ArrayList<>();
    for (Balance b : ghBalances) {
      balances.add(new org.knowm.xchange.dto.account.Balance(new Currency(b.getVault().getAssetCode()), b.getTotal(), b.getAvailable(), ZERO, ZERO, ZERO, ZERO, b.getPending()));
    }
    return balances;
  }
}
