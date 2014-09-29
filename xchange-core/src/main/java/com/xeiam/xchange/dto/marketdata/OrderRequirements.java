package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;

public interface OrderRequirements {
  /**
   * @return The smallest tradable amount accepted by the market
   */
  BigDecimal getAmountMinimum();

  /**
   * @return Number of digits after the decimal point in the amount accepted by the market.
   */
  int getAmountScale();

  /**
   * The smallest number that can be added to or removed from amount and not be discarded by the exchange.
   * <p/>
   * Usually 10^(-amountScale)
   */
  BigDecimal getAmountStep();

  /**
   * @return Number of digits after the decimal point in the price accepted by the market.
   */
  int getPriceScale();


  /**
   * The smallest number that can be added to or removed from price and not be discarded by the exchange.
   * <p/>
   * Usually 10^(-priceScale)
   */
  BigDecimal getPriceStep();
}
