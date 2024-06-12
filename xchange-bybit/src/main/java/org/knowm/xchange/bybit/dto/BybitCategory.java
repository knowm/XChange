package org.knowm.xchange.bybit.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.knowm.xchange.service.marketdata.params.Params;

@Getter
@AllArgsConstructor
public enum BybitCategory implements Params {
  SPOT("spot"),
  LINEAR("linear"),
  INVERSE("inverse"),
  OPTION("option");

  @JsonValue private final String value;
}
