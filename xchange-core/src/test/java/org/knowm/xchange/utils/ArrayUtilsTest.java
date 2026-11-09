package org.knowm.xchange.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.exceptions.ExchangeException;

class ArrayUtilsTest {
  private final Object[] array = {1, "Test", '4'};

  @Test
  void test() {
    assertThat(ArrayUtils.getElement(-1, null, Integer.class)).isNull();
    assertThat(ArrayUtils.getElement(3, array, Integer.class)).isNull();
    assertThat(ArrayUtils.getElement(0, array, Integer.class)).isEqualTo((Integer) 1);
    assertThat(ArrayUtils.getElement(1, array, String.class)).isEqualTo("Test");
    assertThat(ArrayUtils.getElement(3, array, String.class, "default")).isEqualTo("default");
    assertThat(ArrayUtils.getElement(2, array, Character.class)).isEqualTo((Character) '4');
  }

  @Test
  void failedType() {
    assertThatExceptionOfType(ExchangeException.class)
        .isThrownBy(() -> ArrayUtils.getElement(0, array, String.class));
  }

  @Test
  void failedMandatory() {
    assertThatExceptionOfType(ExchangeException.class)
        .isThrownBy(() -> ArrayUtils.getElement(3, array, String.class, true));
  }
}
