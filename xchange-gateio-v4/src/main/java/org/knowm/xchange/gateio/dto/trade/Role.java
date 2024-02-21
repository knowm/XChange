package org.knowm.xchange.gateio.dto.trade;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  TAKER("taker"),
  MAKER("maker");

  @JsonValue
  private final String value;


}
