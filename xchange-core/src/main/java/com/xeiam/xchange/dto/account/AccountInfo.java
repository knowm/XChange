package com.xeiam.xchange.dto.account;

import java.math.BigDecimal;
import java.util.List;

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

  private final String username;
  private final BigDecimal tradingFee;
  private final List<Wallet> wallets;

  /**
   * Constructor
   * 
   * @param username The user name
   * @param wallets The available wallets
   */
  public AccountInfo(String username, List<Wallet> wallets) {

    this(username, null, wallets);
  }

  /**
   * Constructor
   * 
   * @param username The user name
   * @param tradingFee the trading fee
   * @param wallets The available wallets
   */
  public AccountInfo(String username, BigDecimal tradingFee, List<Wallet> wallets) {

    this.username = username;
    this.tradingFee = tradingFee;
    this.wallets = wallets;
  }

  /**
   * @return The user name
   */
  public String getUsername() {

    return username;
  }

  /**
   * @return The available wallets (balance and currency)
   */
  public List<Wallet> getWallets() {

    return wallets;
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
   * Utility method to locate an exchange balance in the given currency
   * 
   * @param currencyUnit A valid currency unit (e.g. CurrencyUnit.USD or CurrencyUnit.of("BTC"))
   * @return The balance, or zero if not found
   */
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

    return "AccountInfo [username=" + username + ", wallets=" + wallets + "]";
  }

}
