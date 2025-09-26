package org.knowm.xchange.coinsph.dto.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CoinsphTime {

  private final long serverTime;

  public CoinsphTime(@JsonProperty("serverTime") long serverTime) {
    this.serverTime = serverTime;
  }
}
