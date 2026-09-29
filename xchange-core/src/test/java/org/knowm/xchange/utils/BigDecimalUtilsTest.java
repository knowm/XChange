package org.knowm.xchange.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class BigDecimalUtilsTest {

  @Test
  void roundToStepSize() {
    BigDecimal result =
        BigDecimalUtils.roundToStepSize(new BigDecimal("3502.31111111"), new BigDecimal("0.25"));
    assertThat(result).isEqualByComparingTo("3502.25");
  }
}
