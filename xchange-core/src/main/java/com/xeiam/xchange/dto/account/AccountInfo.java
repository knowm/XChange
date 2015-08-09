package com.xeiam.xchange.dto.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.trade.Wallet;

/**
 * <p>
 * DTO representing account information
 * </p>
 * <p>
 * Account information is associated with the current balances in various currencies held on the exchange.
 * </p>
 */
public final class AccountInfo {

  // TODO get rid of this field?
  private final String username;
  // TODO get rid of this field?
  private final BigDecimal tradingFee;

  /**
   * @deprecated Use {@link #walletsMap} instead, this field will be deleted in XChange 4.0.0.
   */
  @Deprecated
  private final List<Wallet> wallets;

  /**
   * TODO: from XChange 4.0.0, rename to wallets.
   * <p>
   * The keys represent the currency of the wallet.
   */
  private final Map<String, Wallet> walletsMap;

  /**
   * Constructor
   *
   * @param username The user name
   * @param wallets The available wallets
   * @deprecated Use {@link #AccountInfo(String, Map)} instead, this constructor will be deleted in XChange 4.0.0.
   */
  @Deprecated
  public AccountInfo(String username, List<Wallet> wallets) {

    this(username, null, wallets);
  }

  /**
   * @see #AccountInfo(String, BigDecimal, Iterable).
   */
  public AccountInfo(String username, Iterable<Wallet> wallets) {
    this(username, null, wallets);
  }

  /**
   * @see #AccountInfo(String, BigDecimal, Map).
   */
  public AccountInfo(String username, Map<String, Wallet> wallets) {
    this(username, null, wallets);
  }

  /**
   * Constructor
   *
   * @param username The user name
   * @param tradingFee the trading fee
   * @param wallets The available wallets
   * @deprecated Use {@link #AccountInfo(String, BigDecimal, Map)} instead, this constructor will be deleted in XChange 4.0.0.
   */
  @Deprecated
  public AccountInfo(String username, BigDecimal tradingFee, List<Wallet> wallets) {

    this.username = username;
    this.tradingFee = tradingFee;
    this.wallets = wallets;
    this.walletsMap = new HashMap<String, Wallet>();
    for (Wallet wallet : wallets) {
      this.walletsMap.put(wallet.getCurrency(), wallet);
    }
  }

  /**
   * Constructs an {@link AccountInfo}.
   *
   * @param username the user name.
   * @param tradingFee the trading fee.
   * @param wallets the wallets, the currencies of the wallets should not be duplicated.
   */
  public AccountInfo(String username, BigDecimal tradingFee, Iterable<Wallet> wallets) {
    this.username = username;
    this.tradingFee = tradingFee;
    this.wallets = new ArrayList<Wallet>();
    this.walletsMap = new HashMap<String, Wallet>();
    for (Wallet wallet : wallets) {
      this.wallets.add(wallet);
      this.walletsMap.put(wallet.getCurrency(), wallet);
    }
  }

  /**
   * Constructs an {@link AccountInfo}.
   *
   * @param username the user name.
   * @param tradingFee the trading fee.
   * @param wallets the wallets, key is {@link Currencies}.
   */
  public AccountInfo(String username, BigDecimal tradingFee, Map<String, Wallet> wallets) {
    this.username = username;
    this.tradingFee = tradingFee;
    this.walletsMap = wallets;
    this.wallets = new ArrayList<Wallet>(wallets.values());
  }

  /**
   * @return The user name
   */
  public String getUsername() {

    return username;
  }

  /**
   * Returns the current trading fee
   *
   * @return The trading fee
   */
  public BigDecimal getTradingFee() {

    return tradingFee;
  }

  /**
   * @return The available wallets (balance and currency)
   */
  public List<Wallet> getWallets() {

    // FIXME: from XChange 4.0.0 change to return the walletsMap.values()
    return wallets;
  }

  /**
   * Returns the wallet of the specified currency.
   *
   * @param currency one of the {@link Currencies}.
   * @return the wallet of the specified currency, or a zero balance wallet if no wallet with such currency.
   */
  public Wallet getWallet(String currency) {
    Wallet wallet = this.walletsMap.get(currency);
    return wallet == null ? Wallet.zero(currency) : wallet;
  }

  /**
   * Utility method to locate an exchange balance in the given currency
   *
   * @param currency A valid currency unit (e.g. CurrencyUnit.USD or CurrencyUnit.of("BTC"))
   * @return The balance, or zero if not found
   * @deprecated Use {@link #getWallet(String)} instead.
   */
  @Deprecated
  public BigDecimal getBalance(String currency) {

    for (Wallet wallet : wallets) {
      if (wallet.getCurrency().equals(currency)) {
        return wallet.getBalance();
      }
    }

    // Not found so treat as zero
    return BigDecimal.ZERO;
  }

  @Override
  public String toString() {
    return "AccountInfo [username=" + username + ", tradingFee=" + tradingFee + ", wallets=" + wallets + "]";
  }

}
