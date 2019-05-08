package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class EnigmaWithdrawalRequest {

  @JsonProperty("withdrawal_type_id")
  private int withdrawalTypeId;

  @JsonProperty private BigDecimal amount;

  @JsonProperty private String currency;

  public EnigmaWithdrawalRequest(int withdrawalTypeId, BigDecimal amount, String currency) {
    this.withdrawalTypeId = withdrawalTypeId;
    this.amount = amount;
    this.currency = currency;
  }

  public int getWithdrawalTypeId() {
    return this.withdrawalTypeId;
  }

  public BigDecimal getAmount() {
    return this.amount;
  }

  public String getCurrency() {
    return this.currency;
  }
}
