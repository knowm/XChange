package com.xeiam.xchange.dto.account;

import java.math.BigDecimal;
import java.util.*;

import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.trade.Balance;

/**
 * <p>
 * DTO representing account information
 * </p>
 * <p>
 * Account information is associated with the current balances in various currencies held on the exchange.
 * </p>
 */
public final class AccountInfo {

  // TODO separate account details (only occasionally provided, 1 per account) from wallet balances (always provided, 1 or more per account)
  private final String username;
  private final BigDecimal tradingFee;

  /**
   * @deprecated Use {@link #balances} instead, this field will be deleted in XChange 4.0.0.
   */
  @Deprecated
  private final List<Balance> balancesList;

  /**
   * The keys represent the currency of the wallet.
   */
  private final Map<String, Balance> balances;

  /**
   * Constructor
   *
   * @param username The user name
   * @param balancesList The available balances
   * @deprecated Use {@link #AccountInfo(String, Map)} instead, this constructor will be deleted in XChange 4.0.0.
   */
  @Deprecated
  public AccountInfo(String username, List<Balance> balancesList) {

    this(username, null, balancesList);
  }

  /**
   * @see #AccountInfo(String, BigDecimal, Iterable).
   */
  public AccountInfo(String username, Iterable<Balance> balances) {

    this(username, null, balances);
  }

  /**
   * @see #AccountInfo(String, BigDecimal, Map).
   */
  public AccountInfo(String username, Map<String, Balance> balances) {

    this(username, null, balances);
  }

  /**
   * Constructor
   *
   * @param username The user name
   * @param tradingFee the trading fee
   * @param balancesList The available balances
   * @deprecated Use {@link #AccountInfo(String, BigDecimal, Map)} instead, this constructor will be deleted in XChange 4.0.0.
   */
  @Deprecated
  public AccountInfo(String username, BigDecimal tradingFee, List<Balance> balancesList) {

    this.username = username;
    this.tradingFee = tradingFee;
    this.balancesList = balancesList;
    this.balances = new HashMap<String, Balance>();
    for (Balance balance : balancesList) {
      this.balances.put(balance.getCurrency(), balance);
    }
  }

  /**
   * Constructs an {@link AccountInfo}.
   *
   * @param username the user name.
   * @param tradingFee the trading fee.
   * @param balances the balances, the currencies of the balances should not be duplicated.
   */
  public AccountInfo(String username, BigDecimal tradingFee, Iterable<Balance> balances) {
    this.username = username;
    this.tradingFee = tradingFee;
    this.balancesList = new ArrayList<Balance>();
    this.balances = new HashMap<String, Balance>();
    for (Balance balance : balances) {
      this.balancesList.add(balance);
      this.balances.put(balance.getCurrency(), balance);
    }
  }

  /**
   * Constructs an {@link AccountInfo}.
   *
   * @param username the user name.
   * @param tradingFee the trading fee.
   * @param balances the balances, key is {@link Currencies}.
   */
  public AccountInfo(String username, BigDecimal tradingFee, Map<String, Balance> balances) {
    this.username = username;
    this.tradingFee = tradingFee;
    this.balances = balances;
    this.balancesList = new ArrayList<Balance>(balances.values());
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
   * @return The available balances (amount and currency)
   * @deprecated use {@link #getBalances()}
   */
  @Deprecated
  public List<Balance> getBalancesList() {

    return balancesList;
  }

  /**
   * @return The available balances (amount and currency)
   */
  public Set<Balance> getBalances() {

    return new TreeSet<Balance>(balances.values());
  }

  /**
   * Returns the balance for the specified currency.
   *
   * @param currency one of the {@link Currencies}.
   * @return the balance of the specified currency, or a zero balance if currency not present
   */
  public Balance getBalance(String currency) {

    Balance balance = this.balances.get(currency);
    return balance == null ? Balance.zero(currency) : balance;
  }

  /**
   * Utility method to locate an exchange balance in the given currency
   *
   * @param currency A valid currency unit (e.g. CurrencyUnit.USD or CurrencyUnit.of("BTC"))
   * @return The balance, or zero if not found
   * @deprecated Use {@link #getBalance(String)} instead.
   */
  @Deprecated
  public BigDecimal getBalanceTotal(String currency) {

    return getBalance(currency).getTotal();
  }

  @Override
  public String toString() {

    return "AccountInfo [username=" + username + ", tradingFee=" + tradingFee + ", balances=" + balances.values() + "]";
  }

}
