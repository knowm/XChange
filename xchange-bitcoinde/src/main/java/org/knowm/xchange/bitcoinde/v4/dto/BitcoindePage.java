package org.knowm.xchange.bitcoinde.v4.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindePage {

  Integer current;
  Integer last;

  @JsonCreator
  public BitcoindePage(
      @JsonProperty("current") Integer current, @JsonProperty("last") Integer last) {
    this.current = current;
    this.last = last;
  }
}
