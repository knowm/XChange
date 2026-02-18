package org.knowm.xchange.coinmate.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CoinmateWithdrawInfo {

  private final boolean enabled;
  private final boolean requiresTag;
  private final List<CoinmateWithdrawFee> fee;
  private final BigDecimal minAmount;
  private final BigDecimal max24hLimit;

  @JsonCreator
  public CoinmateWithdrawInfo(
      @JsonProperty("enabled") boolean enabled,
      @JsonProperty("requiresTag") boolean requiresTag,
      @JsonProperty("fee") List<CoinmateWithdrawFee> fee,
      @JsonProperty("minAmount") BigDecimal minAmount,
      @JsonProperty("max24hLimit") BigDecimal max24hLimit) {
    this.enabled = enabled;
    this.requiresTag = requiresTag;
    this.fee = fee;
    this.minAmount = minAmount;
    this.max24hLimit = max24hLimit;
  }
}
