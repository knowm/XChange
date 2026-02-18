package org.knowm.xchange.coinmate.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CoinmateDepositInfo {

  private final boolean enabled;
  private final BigDecimal fixFee;
  private final BigDecimal percentageFee;
  private final BigDecimal minAmount;
  private final BigDecimal maxAmount;
  private final int minConfirmations;

  @JsonCreator
  public CoinmateDepositInfo(
      @JsonProperty("enabled") boolean enabled,
      @JsonProperty("fixFee") BigDecimal fixFee,
      @JsonProperty("percentageFee") BigDecimal percentageFee,
      @JsonProperty("minAmount") BigDecimal minAmount,
      @JsonProperty("maxAmount") BigDecimal maxAmount,
      @JsonProperty("minConfirmations") int minConfirmations) {
    this.enabled = enabled;
    this.fixFee = fixFee;
    this.percentageFee = percentageFee;
    this.minAmount = minAmount;
    this.maxAmount = maxAmount;
    this.minConfirmations = minConfirmations;
  }
}
