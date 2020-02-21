package org.knowm.xchange.gatehub.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Balance {
  private BigDecimal available;

  private BigDecimal pending;

  private BigDecimal total;

  private Vault vault;

  public BigDecimal getAvailable() {
    return available;
  }

  public BigDecimal getPending() {
    return pending;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public Vault getVault() {
    return vault;
  }
}
