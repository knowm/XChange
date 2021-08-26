package org.knowm.xchange.ftx;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;

public class ExchangeInitIntegration {

  @Test
  public void ftxInitializationTest() {
    Exchange ftx = ExchangeFactory.INSTANCE.createExchange(FtxExchange.class);

    assertThat(ftx.getExchangeSymbols().isEmpty()).isFalse();
    assertThat(ftx.getExchangeInstruments().isEmpty()).isFalse();
  }
}
