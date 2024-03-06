package org.knowm.xchange.coinmate.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class UnconfirmedDeposits {
  private final Long id;

  private final BigDecimal amount;

  private final String address;

  private final Long confirmations;

  public UnconfirmedDeposits(
      @JsonProperty Long id,
      @JsonProperty BigDecimal amount,
      @JsonProperty String address,
      @JsonProperty Long confirmations) {
    this.id = id;
    this.amount = amount;
    this.address = address;
    this.confirmations = confirmations;
  }

  public Long getId() {
    return id;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public String getAddress() {
    return address;
  }

  public Long getConfirmations() {
    return confirmations;
  }
}
