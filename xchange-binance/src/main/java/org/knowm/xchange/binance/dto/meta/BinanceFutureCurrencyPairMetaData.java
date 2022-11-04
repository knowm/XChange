package org.knowm.xchange.binance.dto.meta;

import java.math.BigDecimal;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.FeeTier;

public class BinanceFutureCurrencyPairMetaData extends CurrencyPairMetaData {
  private final BigDecimal notional;
  /**
   * Constructor
   *
   * @param tradingFee Trading fee (fraction)
   * @param minimumAmount Minimum trade amount
   * @param maximumAmount Maximum trade amount
   * @param priceScale Price scale
   */
  public BinanceFutureCurrencyPairMetaData(
      BigDecimal tradingFee,
      BigDecimal minimumAmount,
      BigDecimal maximumAmount,
      Integer priceScale,
      BigDecimal notional,
      FeeTier[] feeTiers) {
    super(tradingFee, minimumAmount, maximumAmount, priceScale, feeTiers);
    this.notional = notional;
  }

  public BigDecimal getNotional() {
    return notional;
  }

  @Override
  public String toString() {
    return "BinanceFutureCurrencyPairMetaData{" + "notional=" + notional + "} " + super.toString();
  }
}
