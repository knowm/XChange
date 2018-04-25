package org.knowm.xchange.dto.account.impl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.account.Balance;

/**
 * DTO representing a wallet
 *
 * <p>A wallet has a set of current balances in various currencies held on the exchange.
 */
public final class Wallet implements org.knowm.xchange.dto.account.Wallet {

  private final Map<Currency, Balance> balances;
  private String id;
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
    this.name = name == null ? id : name;
    this.balances =
        balances.stream().collect(Collectors.toMap(b -> b.getCurrency(), Function.identity()));
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

  /** A unique identifier for this wallet */
  /** @return The wallet id */
  @Override
  public String getId() {

    return id;
  }

  /** A descriptive name for this wallet. Defaults to {@link #id} */
  /** @return A descriptive name for the wallet */
  @Override
  public String getName() {

    return name;
  }

  /** The keys represent the currency of the wallet. */
  /** @return The available balances (amount and currency) */
  @Override
  public Map<Currency, Balance> getBalances() {
    return Collections.unmodifiableMap(balances);
  }

  @Override
  public boolean equals(Object object) {

    if (object == this) return true;
    if (!(object instanceof Wallet)) return false;

    org.knowm.xchange.dto.account.Wallet wallet = (org.knowm.xchange.dto.account.Wallet) object;
    return Objects.equals(getId(), wallet.getId())
        && Objects.equals(getName(), wallet.getName())
        && getBalances().equals(wallet.getBalances());
  }

  @Override
  public String toString() {
    return "Wallet [id="
        + getId()
        + ", name="
        + getName()
        + ", balances="
        + getBalances().values()
        + "]";
  }
}
