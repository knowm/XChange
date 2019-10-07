package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnigmaWithdrawalRequest {

  @JsonProperty("withdrawal_type_id")
  private int withdrawalTypeId;

  @JsonProperty("amount")
  private BigDecimal amount;

  @JsonProperty("currency")
  private String currency;

  @JsonProperty("infra")
  private String infra;
}
