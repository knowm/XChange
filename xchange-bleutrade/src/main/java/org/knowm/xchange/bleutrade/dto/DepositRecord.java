package org.knowm.xchange.bleutrade.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepositRecord {
  public final String id;
  public final String timestamp;
  public final BigDecimal amount;
  public final String label;
  public final String coin;

  public DepositRecord(@JsonProperty("Id") String id, @JsonProperty("TimeStamp") String timestamp, @JsonProperty("Amount") BigDecimal amount, @JsonProperty("Label") String label, @JsonProperty("Coin") String coin) {
    this.id = id;
    this.timestamp = timestamp;
    this.amount = amount;
    this.label = label;
    this.coin = coin;
  }

  @Override
  public String toString() {
    return "DepositRecord{" +
        "id='" + id + '\'' +
        ", timestamp='" + timestamp + '\'' +
        ", amount=" + amount +
        ", label='" + label + '\'' +
        ", coin='" + coin + '\'' +
        '}';
  }
}
