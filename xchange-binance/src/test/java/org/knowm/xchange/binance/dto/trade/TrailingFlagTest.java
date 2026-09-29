package org.knowm.xchange.binance.dto.trade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchIllegalArgumentException;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * @author mrmx
 */
class TrailingFlagTest {

  /** Test of method, of class BinanceOrderTrailingFlag. */
  @Test
  void ofValue() {
    System.out.println("testOfValue");
    List<Number> values = Arrays.asList(0.01, 0.1, 1, 10);
    for (Number value : values) {
      assertThat(TrailingFlag.of(value).getTrailingBip()).isBetween(1L, 1000L);
    }
  }

  /** Test of method, of class BinanceOrderTrailingFlag. */
  @Test
  void ofValueWithInvalidNumbers() {
    System.out.println("testOfValueWithInvalidNumbers");
    List<Number> values = Arrays.asList(0.011, 0.11, 2, 11);
    for (Number value : values) {
      assertThat(catchIllegalArgumentException(() -> TrailingFlag.of(value)))
          .as("Invalid value " + value)
          .isNotNull();
    }
  }
}
