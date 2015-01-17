package com.xeiam.xchange.dto.marketdata;

import java.math.BigDecimal;

import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;

public interface TradeServiceHelper {

  /**
   * The smallest tradable amount accepted by the market.
   * The scale of this number is the number of decimal digits accepted by the exchange
   */
  BigDecimal getAmountMinimum();

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

  /**
   * Verify a Limit Order
   *
   * @param limitOrder
   */
  void verifyOrder(LimitOrder limitOrder);

  /**
   * Verify a Market Order
   *
   * @param limitOrder
   */
  void verifyOrder(MarketOrder marketOrder);

}
