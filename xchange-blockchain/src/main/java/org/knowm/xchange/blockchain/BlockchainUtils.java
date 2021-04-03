package org.knowm.xchange.blockchain;

import java.math.BigDecimal;

/** A central place for shared Blockchain Utils */
public final class BlockchainUtils {

  private static final int AMOUNT_INT_2_DECIMAL_FACTOR = 100000000;

  /** private Constructor */
  private BlockchainUtils() {}

  /**
   * Converts long amount into a BigMoney amount
   *
   * @param price
   * @return
   */
  public static BigDecimal getAmount(long price) {

    return BigDecimal.valueOf(price)
        .divide(new BigDecimal(Integer.toString(BlockchainUtils.AMOUNT_INT_2_DECIMAL_FACTOR)));
  }
}
