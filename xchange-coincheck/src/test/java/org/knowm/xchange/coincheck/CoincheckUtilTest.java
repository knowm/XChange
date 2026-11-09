package org.knowm.xchange.coincheck;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class CoincheckUtilTest {
  @Test
  void getArgs() {
    Optional<String> found = CoincheckUtil.getArg(new Object[] {100, "var1", "var2"}, String.class);
    assertThat(found).isNotNull();
    assertThat(found.isPresent()).isTrue();
    assertThat(found.get()).isEqualTo("var1");
  }

  @Test
  void getArgsNotFound() {
    Optional<String> found = CoincheckUtil.getArg(new Object[] {100}, String.class);
    assertThat(found).isNotNull();
    assertThat(found.isPresent()).isFalse();
  }

  @Test
  void getArgsExtended() {
    Optional<CharSequence> found =
        CoincheckUtil.getArg(new Object[] {100, "var1", "var2"}, CharSequence.class);
    assertThat(found).isNotNull();
    assertThat(found.isPresent()).isTrue();
    assertThat(found.get()).isEqualTo("var1");
  }
}
