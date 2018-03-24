package org.knowm.xchange.bitcoincore.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import si.mazi.rescu.ExceptionalReturnContentException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoinCoreBalanceResponse {

  private BigDecimal amount = BigDecimal.ZERO;

  public BigDecimal getAmount() {
    return amount;
  }

  public void setResult(BigDecimal value) {
    amount = value;
  }

  public void setError(String value) {
    if (value != null) {
      throw new ExceptionalReturnContentException(value);
    }
  }
}
