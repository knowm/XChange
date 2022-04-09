package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.knowm.xchange.currency.Currency;

/**
 * DTO representing a wallet
 *
 * <p>A wallet has a set of current balances in various currencies held on the exchange.
 */
public final class Wallet implements Serializable {

  private static final long serialVersionUID = -4136681413143690633L;

  public enum WalletFeature {
    /** The wallet has the ability to deposit external funds and withdraw funds allocated on it */
    FUNDING,
    /** You can trade funds allocated to this wallet */
    TRADING,
    /** You can do margin trading with funds allocated to this wallet */
    MARGIN_TRADING,
    /** You can fund other margin traders with funds allocated to this wallet to earn an interest */
    MARGIN_FUNDING
  }

  /** The keys represent the currency of the wallet. */
  private final Map<Currency, Balance> balances;
  /** A unique identifier for this wallet */
  private String id;
  /** A descriptive name for this wallet. Defaults to {@link #id} */
  private String name;
  /** Features supported by this wallet */
  private final Set<WalletFeature> features;
  /** Maximum leverage for margin trading supported by this wallet */
  private BigDecimal maxLeverage = BigDecimal.ZERO;
  /** Current leverage for margin trading done on this wallet */
  private BigDecimal currentLeverage = BigDecimal.ZERO;

  /**
   * Constructs a {@link Wallet}.
   *
   * @param id the wallet id
   * @param name a descriptive name for the wallet
   * @param balances the balances, the currencies of the balances should not be duplicated.
   * @param features all the features that wallet supports
   *     <p>maxLeverage and currentLeverage are BigDecimal.ZERO for the default constructor
   */
  public Wallet(
      String id,
      String name,
      Collection<Balance> balances,
      Set<WalletFeature> features,
      BigDecimal maxLeverage,
      BigDecimal currentLeverage) {

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
    this.features = features;
    this.maxLeverage = maxLeverage;
    this.currentLeverage = currentLeverage;
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

  /** @return All wallet operation features */
  public Set<WalletFeature> getFeatures() {
    return features;
  }

  /** @return Max leverage of wallet */
  public BigDecimal getMaxLeverage() {
    return maxLeverage;
  }

  /** @return current leverage of wallet */
  public BigDecimal getCurrentLeverage() {
    return currentLeverage;
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
  public int hashCode() {
    return Objects.hash(id, name, balances);
  }

  @Override
  public String toString() {
    return "Wallet{"
        + "balances="
        + balances
        + ", id='"
        + id
        + '\''
        + ", name='"
        + name
        + '\''
        + ", walletFeatures="
        + features
        + ", maxLeverage="
        + maxLeverage
        + ", currentLeverage="
        + currentLeverage
        + '}';
  }

  public static class Builder {

    private Collection<Balance> balances;

    private String id;

    private String name;
    /** These are the default wallet features */
    private Set<WalletFeature> features =
        Stream.of(WalletFeature.TRADING, WalletFeature.FUNDING).collect(Collectors.toSet());

    private BigDecimal maxLeverage = BigDecimal.ZERO;

    private BigDecimal currentLeverage = BigDecimal.ZERO;

    public static Builder from(Collection<Balance> balances) {
      return new Builder().balances(balances);
    }

    private Builder balances(Collection<Balance> balances) {
      this.balances = balances;
      return this;
    }

    public Builder id(String id) {

      this.id = id;
      return this;
    }

    public Builder name(String name) {

      this.name = name;
      return this;
    }

    public Builder features(Set<WalletFeature> features) {

      this.features = features;
      return this;
    }

    public Builder maxLeverage(BigDecimal maxLeverage) {

      this.maxLeverage = maxLeverage;
      return this;
    }

    public Builder currentLeverage(BigDecimal currentLeverage) {

      this.currentLeverage = currentLeverage;
      return this;
    }

    public Wallet build() {

      return new Wallet(id, name, balances, features, maxLeverage, currentLeverage);
    }
  }
}
