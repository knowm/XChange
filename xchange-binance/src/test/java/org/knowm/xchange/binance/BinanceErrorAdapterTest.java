package org.knowm.xchange.binance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.OrderAmountUnderMinimumException;
import org.knowm.xchange.exceptions.OrderNotValidException;

class BinanceErrorAdapterTest {

  @Test
  void binanceExceptionWithCode1013MsgLotSize() {
    final ExchangeException adaptedException =
        BinanceErrorAdapter.adapt(new BinanceException(-1013, "LOT_SIZE"));

    assertThat(adaptedException).isInstanceOf(OrderNotValidException.class);
  }

  @Test
  void binanceExceptionWithCode1013MsgMinNotional() {
    final ExchangeException adaptedException =
        BinanceErrorAdapter.adapt(new BinanceException(-1013, "MIN_NOTIONAL"));

    assertThat(adaptedException).isInstanceOf(OrderAmountUnderMinimumException.class);
  }
}
