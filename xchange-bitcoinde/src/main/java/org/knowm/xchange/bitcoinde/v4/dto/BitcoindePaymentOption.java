package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BitcoindePaymentOption {
  EXPRESS_ONLY(1),
  SEPA_ONLY(2),
  EXPRESS_SEPA(3);

  private final int value;

  BitcoindePaymentOption(final int value) {
    this.value = value;
  }

  @JsonValue
  public int getValue() {
    return value;
  }

  @JsonCreator
  public static BitcoindePaymentOption fromValue(final int value) {
    for (BitcoindePaymentOption paymentOption : BitcoindePaymentOption.values()) {
      if (value == paymentOption.getValue()) {
        return paymentOption;
      }
    }

    throw new IllegalArgumentException("Unknown PaymentOption value: " + value);
  }
}
