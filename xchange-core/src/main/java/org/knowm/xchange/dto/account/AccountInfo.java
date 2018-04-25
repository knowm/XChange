package org.knowm.xchange.dto.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public interface AccountInfo extends Serializable {
  // There were concurrently 6 constructors in use !!!!
  // what's easier ios to define one ctor that makes memory tracking easier for profilers.
  static AccountInfo build(Wallet... wallets) {
    return build(null, null, wallets);
  }

  static AccountInfo build(Collection<Wallet> wallets) {
    return build(null, null, wallets);
  }

  static AccountInfo build(String username, Wallet... wallets) {
    return build(username, null, wallets);
  }

  static AccountInfo build(String username, Collection<Wallet> wallets) {
    return build(username, null, wallets);
  }

  static AccountInfo build(String username, BigDecimal tradingFee, Collection<Wallet> wallets) {
    return new org.knowm.xchange.dto.account.impl.AccountInfo(username, tradingFee, wallets);
  }

  static AccountInfo build(String username, BigDecimal tradingFee, Wallet... wallets) {
    return build(username, tradingFee, Arrays.asList(wallets));
  }

  /** Gets all wallets in this account */
  Map<String, Wallet> getWallets();

  /** Gets wallet for accounts which don't use multiple wallets with ids */
  Wallet getWallet();

  /** Gets the wallet with a specific id */
  Wallet getWallet(String id);

  /** @return The user name */
  String getUsername();

  /**
   * Returns the current trading fee
   *
   * @return The trading fee
   */
  BigDecimal getTradingFee();
}
