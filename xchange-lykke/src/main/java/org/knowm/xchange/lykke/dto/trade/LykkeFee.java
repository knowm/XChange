package org.knowm.xchange.lykke.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LykkeFee {

  private double amount;
  private LykkeFeeType type;

  public LykkeFee(@JsonProperty("Amount") double amount, @JsonProperty("Type") LykkeFeeType type) {
    this.amount = amount;
    this.type = type;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public LykkeFeeType getType() {
    return type;
  }

  public void setType(LykkeFeeType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "Fee{" + "amount=" + amount + ", type=" + type + '}';
  }
}
