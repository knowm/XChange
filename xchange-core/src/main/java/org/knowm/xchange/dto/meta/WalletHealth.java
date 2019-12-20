package org.knowm.xchange.dto.meta;

public enum WalletHealth {
  /** You can deposit and withdraw founds from the exchange */
  ONLINE,
  DEPOSITS_DISABLED,
  WITHDRAWALS_DISABLED,
  /** You cannot deposit nor withdraw founds from the exchange */
  OFFLINE,
  /** The exchange does not inform us about the health of this wallet */
  UNKNOWN;
}
