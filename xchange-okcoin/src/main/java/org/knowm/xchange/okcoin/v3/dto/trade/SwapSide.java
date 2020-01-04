package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum SwapSide {
  short_side("short"),
  long_side("long");

  private final String value;

  @JsonCreator
  public static SwapSide forValue(String value) {
    if (value == null) {
      return null;
    }
    return Stream.of(SwapSide.values())
        .filter(s -> value.equals(s.value))
        .findAny()
        .orElseThrow(() -> new RuntimeException("Invalid swap side: " + value));
  }
}
