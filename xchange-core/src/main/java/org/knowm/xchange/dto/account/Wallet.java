package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import org.knowm.xchange.currency.Currency;

public interface Wallet extends Serializable {
  static Wallet build(String id, String name, Collection<Balance> balances) {
    return new org.knowm.xchange.dto.account.impl.Wallet(id, name, balances);
  }

  static Wallet build(String id, Collection<Balance> balances) {
    return new org.knowm.xchange.dto.account.impl.Wallet(id, balances);
  }

  static Wallet build(String id, Balance... balances) {
    return new org.knowm.xchange.dto.account.impl.Wallet(id, balances);
  }

  static Wallet build(Collection<Balance> balances) {
    return new org.knowm.xchange.dto.account.impl.Wallet(balances);
  }

  static Wallet build(Balance... balances) {
    return new org.knowm.xchange.dto.account.impl.Wallet(balances);
  }
  /** A unique identifier for this wallet */
  /** @return The wallet id */
  String getId();

  /** A descriptive name for this wallet. Defaults to {@link #id} */
  /** @return A descriptive name for the wallet */
  String getName();

  /** The keys represent the currency of the wallet. */
  /** @return The available balances (amount and currency) */
  Map<Currency, Balance> getBalances();
  /**
   * Returns the balance for the specified currency.
   *
   * @param currency a {@link org.knowm.xchange.currency.Currency}.
   * @return the balance of the specified currency, or a zero balance if currency not present
   */
  default Balance getBalance(Currency currency) {
    org.knowm.xchange.dto.account.Balance balance = this.getBalances().get(currency);
    return balance == null ? Balance.valueOf(currency) : balance;
  }
}
