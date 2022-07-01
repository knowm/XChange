package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeException extends RuntimeException {

  Integer credits;
  BitcoindeError[] errors;
  BitcoindeMaintenance maintenance;
  Long nonce;

  @JsonCreator
  public BitcoindeException(
      @JsonProperty("credits") Integer credits,
      @JsonProperty("errors") BitcoindeError[] errors,
      @JsonProperty("maintenance") BitcoindeMaintenance maintenance,
      @JsonProperty("nonce") Long nonce) {
    this.credits = credits;
    this.errors = errors;
    this.maintenance = maintenance;
    this.nonce = nonce;
  }

  @Override
  public String getMessage() {
    return Arrays.stream(this.errors)
        .map(BitcoindeError::toString)
        .collect(Collectors.joining(", "));
  }
}
