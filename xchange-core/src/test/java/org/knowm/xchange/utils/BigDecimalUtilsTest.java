package org.knowm.xchange.utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.Test;

public class BigDecimalUtilsTest {

  @Test
  public void testRoundToStepSize() {
    BigDecimal result =
        BigDecimalUtils.roundToStepSize(new BigDecimal("3502.31111111"), new BigDecimal("0.25"));
    assertThat(result).isEqualByComparingTo("3502.25");
  }
}
