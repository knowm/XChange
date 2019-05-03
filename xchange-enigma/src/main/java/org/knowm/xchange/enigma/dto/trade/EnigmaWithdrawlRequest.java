package org.knowm.xchange.enigma.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class EnigmaWithdrawlRequest {

  @JsonProperty("withdrawal_type_id")
  private int withdrawlTypeId;

  @JsonProperty private BigDecimal amount;

  @JsonProperty private String currency;

  public EnigmaWithdrawlRequest(int withdrawlTypeId, BigDecimal amount, String currency) {
    this.withdrawlTypeId = withdrawlTypeId;
    this.amount = amount;
    this.currency = currency;
  }

  public int getWithdrawlTypeId() {
    return this.withdrawlTypeId;
  }

  public BigDecimal getAmount() {
    return this.amount;
  }

  public String getCurrency() {
    return this.currency;
  }
}
