package org.knowm.xchange.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.meta.InstrumentMetaData;

/**
 * Helps you to validate and / or adjust order values like price and amount to the restrictions
 * dictated by {@link InstrumentMetaData}
 *
 * @author walec51
 */
public class OrderValuesHelper {

  private final InstrumentMetaData metaData;

  public OrderValuesHelper(InstrumentMetaData metaData) {
    this.metaData = metaData;
  }

  /**
   * @return true if the minimum amount is specified in the currency pair and if the amount is under
   *     it
   */
  public boolean amountUnderMinimum(BigDecimal amount) {
    BigDecimal minimalAmount = metaData.getMinimumAmount();
    if (minimalAmount == null) {
      return false;
    }
    return amount.compareTo(minimalAmount) < 0;
  }

  /**
   * Adjusts the given amount to the restrictions dictated by {@link InstrumentMetaData}.
   *
   * <p>This mainly does rounding based on {@link InstrumentMetaData#getVolumeScale()} and {@link
   * InstrumentMetaData#getAmountStepSize()} if they are present in the metadata. It will also
   * return the maximum allowed amount if {@link InstrumentMetaData#getMaximumAmount() ()} is set
   * and your amount is greater.
   *
   * @param amount the amount your derived from your users input or your calculations
   * @return amount adjusted to the restrictions dictated by {@link InstrumentMetaData}
   */
  public BigDecimal adjustAmount(BigDecimal amount) {
    BigDecimal maximumAmount = metaData.getMaximumAmount();
    if (maximumAmount != null && amount.compareTo(maximumAmount) > 0) {
      return maximumAmount;
    }
    BigDecimal result = amount;
    BigDecimal stepSize = metaData.getAmountStepSize();
    if (stepSize != null && stepSize.compareTo(BigDecimal.ZERO) != 0) {
      result = BigDecimalUtils.roundToStepSize(result, stepSize, RoundingMode.FLOOR);
    }
    Integer baseScale = metaData.getVolumeScale();
    if (baseScale != null) {
      result = result.setScale(baseScale, RoundingMode.FLOOR);
    }
    return result;
  }

  /**
   * Adjusts the given price to the restrictions dictated by {@link InstrumentMetaData}.
   *
   * <p>Convenience method that chooses the adequate rounding mode for you order type. See {@link
   * #adjustPrice(java.math.BigDecimal, java.math.RoundingMode)} for more information.
   *
   * @see #adjustPrice(java.math.BigDecimal, java.math.RoundingMode)
   */
  public BigDecimal adjustPrice(BigDecimal price, Order.OrderType orderType) {
    return adjustPrice(
        price,
        orderType == Order.OrderType.ASK || orderType == Order.OrderType.EXIT_ASK
            ? RoundingMode.CEILING
            : RoundingMode.FLOOR);
  }

  /**
   * Adjusts the given price to the restrictions dictated by {@link InstrumentMetaData}.
   *
   * <p>This mainly does rounding based on {@link InstrumentMetaData#getPriceScale()} if it is
   * present in the metadata.
   *
   * @param price the price your derived from your users input or your calculations
   * @return price adjusted to the restrictions dictated by {@link InstrumentMetaData}
   */
  public BigDecimal adjustPrice(BigDecimal price, RoundingMode roundingMode) {
    BigDecimal result = price;
    Integer scale = metaData.getPriceScale();
    if (scale != null) {
      result = result.setScale(scale, roundingMode);
    }

    BigDecimal stepSize = metaData.getPriceStepSize();
    if (stepSize != null && stepSize.signum() != 0) {
      result = BigDecimalUtils.roundToStepSize(result, stepSize, roundingMode);
    }
    return result;
  }
}
