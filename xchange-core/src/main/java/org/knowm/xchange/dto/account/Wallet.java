package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.knowm.xchange.currency.Currency;

/**
 * DTO representing a wallet
 *
 * <p>A wallet has a set of current balances in various currencies held on the exchange.
 */
public final class Wallet implements Serializable {

  /** The keys represent the currency of the wallet. */
  private final Map<Currency, Balance> balances;
  /** A unique identifier for this wallet */
  private String id;
  /** A descriptive name for this wallet. Defaults to {@link #id} */
  private String name;

  /**
   * Constructs a {@link Wallet}.
   *
   * @param id the wallet id
   * @param name a descriptive name for the wallet
   * @param balances the balances, the currencies of the balances should not be duplicated.
   */
  public Wallet(String id, String name, Collection<Balance> balances) {

    this.id = id;
    if (name == null) {
      this.name = id;
    } else {
      this.name = name;
    }
    if (balances.size() == 0) {
      this.balances = Collections.emptyMap();
    } else if (balances.size() == 1) {
      Balance balance = balances.iterator().next();
      this.balances = Collections.singletonMap(balance.getCurrency(), balance);
    } else {
      this.balances = new HashMap<>();
      for (Balance balance : balances) {
        if (this.balances.containsKey(balance.getCurrency()))
          // this class could merge balances, but probably better to catch mistakes and let the
          // exchange merge them
          throw new IllegalArgumentException("duplicate balances in wallet");
        this.balances.put(balance.getCurrency(), balance);
      }
    }
  }

  /** @see #Wallet(String, String, Collection) */
  public Wallet(String id, Collection<Balance> balances) {

    this(id, null, balances);
  }

  /** @see #Wallet(String, String, Collection) */
  public Wallet(String id, Balance... balances) {

    this(id, null, Arrays.asList(balances));
  }

  /** @see #Wallet(String, String, Collection) */
  public Wallet(Collection<Balance> balances) {

    this(null, null, balances);
  }

  /** @see #Wallet(String, String, Collection) */
  public Wallet(Balance... balances) {

    this(null, balances);
  }

  /** @return The wallet id */
  public String getId() {

    return id;
  }

  /** @return A descriptive name for the wallet */
  public String getName() {

    return name;
  }

  /** @return The available balances (amount and currency) */
  public Map<Currency, Balance> getBalances() {

    return Collections.unmodifiableMap(balances);
  }

  /**
   * Returns the balance for the specified currency.
   *
   * @param currency a {@link Currency}.
   * @return the balance of the specified currency, or a zero balance if currency not present
   */
  public Balance getBalance(Currency currency) {

    Balance balance = this.balances.get(currency);
    return balance == null ? Balance.zero(currency) : balance;
  }

  @Override
  public boolean equals(Object object) {

    if (object == this) return true;
    if (!(object instanceof Wallet)) return false;

    Wallet wallet = (Wallet) object;
    return Objects.equals(id, wallet.id)
        && Objects.equals(name, wallet.name)
        && balances.equals(wallet.balances);
  }

  @Override
  public String toString() {

    return "Wallet [id=" + id + ", name=" + name + ", balances=" + balances.values() + "]";
  }
}
