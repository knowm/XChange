package org.knowm.xchange.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.dto.meta.InstrumentMetaData;

public class OrderValuesHelperTest {

  private InstrumentMetaData pairMetaData;
  private OrderValuesHelper adjuster;

  @Before
  public void setup() {
    pairMetaData = mock(InstrumentMetaData.class);
    adjuster = new OrderValuesHelper(pairMetaData);
  }

  @Test
  public void shouldAdjustAmountToStepSize() {
    // given
    given(pairMetaData.getAmountStepSize()).willReturn(new BigDecimal("0.001"));
    given(pairMetaData.getVolumeScale()).willReturn(null);

    // when
    BigDecimal result = adjuster.adjustAmount(new BigDecimal("0.93851732"));

    // then
    assertThat(result).isEqualByComparingTo("0.938");
  }

  @Test
  public void shouldAdjustAmountToScale() {
    // given
    given(pairMetaData.getAmountStepSize()).willReturn(null);
    given(pairMetaData.getVolumeScale()).willReturn(5);

    // when
    BigDecimal result = adjuster.adjustAmount(new BigDecimal("10.123456789"));

    // then
    assertThat(result).isEqualByComparingTo("10.12345");
  }

  @Test
  public void shouldAdjustAmountToMaximal() {
    // given
    BigDecimal minimal = new BigDecimal("100");
    given(pairMetaData.getMaximumAmount()).willReturn(minimal);
    given(pairMetaData.getVolumeScale()).willReturn(null);

    // when
    BigDecimal result = adjuster.adjustAmount(new BigDecimal("128.32432"));

    // then
    assertThat(result).isEqualByComparingTo(minimal);
  }

  @Test
  public void shouldAdjustPriceToScale() {
    // given
    given(pairMetaData.getPriceScale()).willReturn(2);

    // when
    BigDecimal result = adjuster.adjustPrice(new BigDecimal("36010.123456789"), RoundingMode.FLOOR);

    // then
    assertThat(result).isEqualByComparingTo("36010.12");
  }
}
