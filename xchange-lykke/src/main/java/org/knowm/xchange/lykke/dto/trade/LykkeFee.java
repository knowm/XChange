package org.knowm.xchange.lykke.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeFee {

  @JsonProperty("Amount")
  private final double amount;

  @JsonProperty("Type")
  private final LykkeFeeType type;

  public LykkeFee(@JsonProperty("Amount") double amount, @JsonProperty("Type") LykkeFeeType type) {
    this.amount = amount;
    this.type = type;
  }

  public double getAmount() {
    return amount;
  }

  public LykkeFeeType getType() {
    return type;
  }

  @Override
  public String toString() {
    return "Fee{" + "amount=" + amount + ", type=" + type + '}';
  }
}
