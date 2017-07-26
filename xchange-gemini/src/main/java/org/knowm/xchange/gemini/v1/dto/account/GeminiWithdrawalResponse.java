package org.knowm.xchange.gemini.v1.dto.account;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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