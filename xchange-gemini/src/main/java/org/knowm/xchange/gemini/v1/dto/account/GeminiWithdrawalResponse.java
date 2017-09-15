package org.knowm.xchange.gemini.v1.dto.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeminiWithdrawalResponse {

  public final String destination;
  public final BigDecimal amount;
  public final String txHash;

  public GeminiWithdrawalResponse(@JsonProperty("destination") String destination, @JsonProperty("amount") BigDecimal amount, @JsonProperty("txHash") String txHash) {
    this.destination = destination;
    this.amount = amount;
    this.txHash = txHash;
  }
}