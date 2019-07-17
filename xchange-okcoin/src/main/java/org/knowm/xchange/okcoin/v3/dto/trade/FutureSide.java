package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum FutureSide {
  short_side("short"),
  long_side("long");

  private final String value;

  @JsonValue
  @Override
  public String toString() {
    return value;
  }

  @JsonCreator
  public static FutureSide forValue(String value) {
    if (value == null) {
      return null;
    }
    return Stream.of(FutureSide.values())
        .filter(s -> value.equals(s.value))
        .findAny()
        .orElseThrow(() -> new RuntimeException("Invalid future side: " + value));
  }
}
