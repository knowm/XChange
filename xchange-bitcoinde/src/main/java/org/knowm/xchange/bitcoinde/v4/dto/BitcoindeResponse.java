package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeResponse {

  private final Integer credits;
  private final BitcoindeError[] errors;
  private final BitcoindeMaintenance maintenance;
  private final Long nonce;

  @JsonCreator
  public BitcoindeResponse(
      @JsonProperty("credits") Integer credits,
      @JsonProperty("errors") BitcoindeError[] errors,
      @JsonProperty("maintenance") BitcoindeMaintenance maintenance,
      @JsonProperty("nonce") Long nonce) {
    this.credits = credits;
    this.errors = errors;
    this.maintenance = maintenance;
    this.nonce = nonce;
  }
}
