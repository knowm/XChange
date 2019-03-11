package org.knowm.xchange.kuna.dto.enums;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.knowm.xchange.kuna.dto.enums.KunaSide.BUY;
import static org.knowm.xchange.kuna.dto.enums.KunaSide.SELL;

import org.junit.Test;

public class KunaSideTest {

  @Test
  public void valueOf_withString() {
    assertThatThrownBy(() -> KunaSide.valueOf(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("Name is null");
    assertThatThrownBy(() -> KunaSide.valueOf(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant org.knowm.xchange.kuna.dto.enums.KunaSide");
  }

  @Test
  public void valueOfIgnoreCase_withString() {
    assertThat(KunaSide.valueOfIgnoreCase(null)).isNull();
    assertThatThrownBy(() -> KunaSide.valueOfIgnoreCase(""))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("No enum constant org.knowm.xchange.kuna.dto.enums.KunaSide");

    assertThat(KunaSide.valueOfIgnoreCase(BUY.name().toLowerCase())).isEqualByComparingTo(BUY);
    assertThat(KunaSide.valueOfIgnoreCase(BUY.name())).isEqualByComparingTo(BUY);

    assertThat(KunaSide.valueOfIgnoreCase(SELL.name().toLowerCase())).isEqualByComparingTo(SELL);
    assertThat(KunaSide.valueOfIgnoreCase(SELL.name())).isEqualByComparingTo(SELL);
  }
}
