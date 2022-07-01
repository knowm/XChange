package org.knowm.xchange.okcoin.v3.dto.trade;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;

/**
 * optional Fill in number for parameter， 0: Normal limit order (Unfilled and 0 represent normal
 * limit order) 1: Post only 2: Fill Or Kill 3: Immediatel Or Cancel
 */
@AllArgsConstructor
public enum OrderPlacementType {
  normal("0"),
  post_only("1"),
  fill_or_kill("2"),
  immediate_or_cancel("3");

  private final String value;

  @JsonValue
  public String getValue() {
    return value;
  }

  @JsonCreator
  public static OrderPlacementType forValue(String value) {
    if (value == null) {
      return null;
    }
    return Stream.of(OrderPlacementType.values())
        .filter(s -> value.equals(s.value))
        .findAny()
        .orElseThrow(() -> new RuntimeException("Invalid order placement type: " + value));
  }
}
