package org.knowm.xchange.bleutrade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class WithdrawRecord extends DepositRecord {
  public final String transactionId;

  public WithdrawRecord(
      @JsonProperty("Id") String id,
      @JsonProperty("TimeStamp") String timestamp,
      @JsonProperty("Amount") BigDecimal amount,
      @JsonProperty("Label") String label,
      @JsonProperty("Coin") String coin,
      @JsonProperty("TransactionId") String transactionId) {
    super(id, timestamp, amount, label, coin);
    this.transactionId = transactionId;
  }

  @Override
  public String toString() {
    return "WithdrawRecord{"
        + "id='"
        + id
        + '\''
        + ", transactionId='"
        + transactionId
        + '\''
        + ", timestamp='"
        + timestamp
        + '\''
        + ", amount="
        + amount
        + ", label='"
        + label
        + '\''
        + ", coin='"
        + coin
        + '\''
        + '}';
  }
}
