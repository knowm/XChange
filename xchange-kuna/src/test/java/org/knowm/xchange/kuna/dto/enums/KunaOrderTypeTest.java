package org.knowm.xchange.kuna.dto.enums;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.knowm.xchange.kuna.dto.enums.KunaOrderType.LIMIT;
import static org.knowm.xchange.kuna.dto.enums.KunaOrderType.MARKET;

import org.junit.Test;

public class KunaOrderTypeTest {
  @Test
  public void valueOf_withString() {
    assertThatThrownBy(() -> KunaOrderType.valueOf(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("Name is null");
    assertThatThrownBy(() -> KunaOrderType.valueOf(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant org.knowm.xchange.kuna.dto.enums.KunaOrderType");
  }

  @Test
  public void valueOfIgnoreCase_withString() {
    assertThat(KunaOrderType.valueOfIgnoreCase(null)).isNull();
    assertThatThrownBy(() -> KunaOrderType.valueOfIgnoreCase(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant org.knowm.xchange.kuna.dto.enums.KunaOrderType");

    assertThat(KunaOrderType.valueOfIgnoreCase(LIMIT.name().toLowerCase()))
        .isEqualByComparingTo(LIMIT);
    assertThat(KunaOrderType.valueOfIgnoreCase(LIMIT.name())).isEqualByComparingTo(LIMIT);

    assertThat(KunaOrderType.valueOfIgnoreCase(MARKET.name().toLowerCase()))
        .isEqualByComparingTo(MARKET);
    assertThat(KunaOrderType.valueOfIgnoreCase(MARKET.name())).isEqualByComparingTo(MARKET);
  }
}
