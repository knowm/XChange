package org.knowm.xchange.dto.account.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.knowm.xchange.dto.account.Wallet;

/**
 * DTO representing account information
 *
 * <p>Account information is anything particular associated with the user's login
 */
public final class AccountInfo implements org.knowm.xchange.dto.account.AccountInfo {

  /** The name on the account */
  private final String username;

  /**
   * The current fee this account must pay as a fraction of the value of each trade. Null if there
   * is no such fee.
   */
  private final BigDecimal tradingFee;

  /** The wallets owned by this account */
  private final Map<String, org.knowm.xchange.dto.account.Wallet> wallets;

  /**
   * Constructs an {@link org.knowm.xchange.dto.account.AccountInfo}.
   *
   * @param username the user name.
   * @param tradingFee the trading fee.
   * @param wallets the user's wallets
   */
  public AccountInfo(
      String username,
      BigDecimal tradingFee,
      Collection<org.knowm.xchange.dto.account.Wallet> wallets) {

    this.username = username;
    this.tradingFee = tradingFee;

    if (wallets.size() == 0) {
      this.wallets = Collections.emptyMap();
    } else if (wallets.size() == 1) {
      org.knowm.xchange.dto.account.Wallet wallet = wallets.iterator().next();
      this.wallets = Collections.singletonMap(wallet.getId(), wallet);
    } else {
      this.wallets = new HashMap<>();
      for (org.knowm.xchange.dto.account.Wallet wallet : wallets) {
        if (this.wallets.containsKey(wallet.getId())) {
          throw new IllegalArgumentException("duplicate wallets passed to AccountInfo");
        }
        this.wallets.put(wallet.getId(), wallet);
      }
    }
  }

  /** @see #AccountInfo(String, BigDecimal, Collection) */
  private AccountInfo(
      String username, BigDecimal tradingFee, org.knowm.xchange.dto.account.Wallet... wallets) {

    this(username, tradingFee, Arrays.asList(wallets));
  }

  /** Gets all wallets in this account */
  @Override
  public Map<String, org.knowm.xchange.dto.account.Wallet> getWallets() {

    return Collections.unmodifiableMap(wallets);
  }

  /** Gets wallet for accounts which don't use multiple wallets with ids */
  @Override
  public org.knowm.xchange.dto.account.Wallet getWallet() {

    if (wallets.size() != 1) {
      throw new UnsupportedOperationException(wallets.size() + " wallets in account");
    }

    return getWallet(wallets.keySet().iterator().next());
  }

  /** Gets the wallet with a specific id */
  @Override
  public Wallet getWallet(String id) {

    return wallets.get(id);
  }

  /** @return The user name */
  @Override
  public String getUsername() {

    return username;
  }

  /**
   * Returns the current trading fee
   *
   * @return The trading fee
   */
  @Override
  public BigDecimal getTradingFee() {

    return tradingFee;
  }

  @Override
  public String toString() {

    return "AccountInfo [username="
        + username
        + ", tradingFee="
        + tradingFee
        + ", wallets="
        + wallets
        + "]";
  }
}
