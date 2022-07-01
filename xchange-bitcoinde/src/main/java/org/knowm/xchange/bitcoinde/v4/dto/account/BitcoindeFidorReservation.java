package org.knowm.xchange.bitcoinde.v4.dto.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Map;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeFidorReservation {

  BigDecimal totalAmount;
  BigDecimal availableAmount;
  String reservedAt;
  String validUntil;
  Map<String, BitcoindeAllocation> allocation;

  @JsonCreator
  public BitcoindeFidorReservation(
      @JsonProperty("total_amount") BigDecimal totalAmount,
      @JsonProperty("available_amount") BigDecimal availableAmount,
      @JsonProperty("reserved_at") String reservedAt,
      @JsonProperty("valid_until") String validUntil,
      @JsonProperty("allocation") Map<String, BitcoindeAllocation> allocation) {
    this.totalAmount = totalAmount;
    this.availableAmount = availableAmount;
    this.reservedAt = reservedAt;
    this.validUntil = validUntil;
    this.allocation = allocation;
  }
}
