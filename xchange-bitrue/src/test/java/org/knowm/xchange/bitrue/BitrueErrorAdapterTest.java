package org.knowm.xchange.bitrue;

import org.junit.Test;
import org.knowm.xchange.bitrue.dto.BitrueException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.OrderAmountUnderMinimumException;
import org.knowm.xchange.exceptions.OrderNotValidException;

import static org.assertj.core.api.Assertions.assertThat;

public class BitrueErrorAdapterTest {

  @Test
  public void testBitrueExceptionWithCode1013MsgLotSize() {
    final ExchangeException adaptedException =
        BitrueErrorAdapter.adapt(new BitrueException(-1013, "LOT_SIZE"));

    assertThat(adaptedException).isInstanceOf(OrderNotValidException.class);
  }

  @Test
  public void testBitrueExceptionWithCode1013MsgMinNotional() {
    final ExchangeException adaptedException =
        BitrueErrorAdapter.adapt(new BitrueException(-1013, "MIN_NOTIONAL"));

    assertThat(adaptedException).isInstanceOf(OrderAmountUnderMinimumException.class);
  }
}
