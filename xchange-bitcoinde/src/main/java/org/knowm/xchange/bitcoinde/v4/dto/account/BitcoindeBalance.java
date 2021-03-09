package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeBalance {

  BigDecimal availableAmount;
  BigDecimal reservedAmount;
  BigDecimal totalAmount;

  @JsonCreator
  public BitcoindeBalance(
      @JsonProperty("available_amount") BigDecimal availableAmount,
      @JsonProperty("reserved_amount") BigDecimal reservedAmount,
      @JsonProperty("total_amount") BigDecimal totalAmount) {
    this.availableAmount = availableAmount;
    this.reservedAmount = reservedAmount;
    this.totalAmount = totalAmount;
  }
}
