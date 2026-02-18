package org.knowm.xchange.coinmate.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CoinmateWithdrawFee {

  private final BigDecimal fixFee;
  private final BigDecimal percentageFee;
  private final String variant;

  @JsonCreator
  public CoinmateWithdrawFee(
      @JsonProperty("fixFee") BigDecimal fixFee,
      @JsonProperty("percentageFee") BigDecimal percentageFee,
      @JsonProperty("variant") String variant) {
    this.fixFee = fixFee;
    this.percentageFee = percentageFee;
    this.variant = variant;
  }
}
