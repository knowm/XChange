package com.xeiam.xchange.service.marketdata;

import net.jcip.annotations.Immutable;

/**
 * Represents a money value in both integer and decimal form and the conversion factor for going from one to the other. The integer (long) value is used for calculations, the decimal for display purposes. The factor for going from one to the other.
 */
@Immutable
public final class Money {

  private final long value_int;
  private final double value_decimal;
  private final int factor;

  /**
   * Constructor
   * 
   * @param value_int
   * @param value_decimal
   * @param factor
   */
  public Money(long value_int, double value_decimal, int factor) {

    this.value_int = value_int;
    this.value_decimal = value_decimal;
    this.factor = factor;
  }

  public long getValue_int() {
    return value_int;
  }

  public double getValue_decimal() {
    return value_decimal;
  }

  public int getFactor() {
    return factor;
  }

}
