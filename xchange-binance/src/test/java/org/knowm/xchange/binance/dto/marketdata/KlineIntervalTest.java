package org.knowm.xchange.binance.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class KlineIntervalTest {

  @Test
  public void testSubSecondIntervals() {
    assertThat(KlineInterval.s1.code()).isEqualTo("1s");
    assertThat(KlineInterval.s1.getMillis()).isEqualTo(1000L);

    assertThat(KlineInterval.s3.code()).isEqualTo("3s");
    assertThat(KlineInterval.s3.getMillis()).isEqualTo(3000L);
  }

  @Test
  public void testGetPeriodTypeFromSecs() {
    assertThat(KlineInterval.getPeriodTypeFromSecs(1)).isEqualTo(KlineInterval.s1);
    assertThat(KlineInterval.getPeriodTypeFromSecs(3)).isEqualTo(KlineInterval.s3);
    assertThat(KlineInterval.getPeriodTypeFromSecs(60)).isEqualTo(KlineInterval.m1);
  }

  @Test
  public void testAllIntervalsHaveNonNullCode() {
    for (KlineInterval interval : KlineInterval.values()) {
      assertThat(interval.code()).as(interval.name()).isNotNull().isNotEmpty();
      assertThat(interval.getMillis()).as(interval.name()).isGreaterThan(0);
    }
  }
}
