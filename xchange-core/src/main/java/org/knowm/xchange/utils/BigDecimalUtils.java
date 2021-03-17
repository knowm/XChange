package org.knowm.xchange.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimalUtils {

  private BigDecimalUtils() {}

  // Need this to avoid errors
  // With ftx for lend it takes maximum 4 digit after the comma
  public static BigDecimal ftxRoundingLending(BigDecimal value) {
    return value.setScale(4, RoundingMode.DOWN);
  }

  public static BigDecimal roundToStepSize(BigDecimal value, BigDecimal stepSize) {
    return roundToStepSize(value, stepSize, RoundingMode.HALF_DOWN);
  }

  public static BigDecimal roundToStepSize(
      BigDecimal value, BigDecimal stepSize, RoundingMode roundingMode) {
    BigDecimal divided = value.divide(stepSize, MathContext.DECIMAL32).setScale(0, roundingMode);
    return divided.multiply(stepSize, MathContext.DECIMAL32).stripTrailingZeros();
  }
}
