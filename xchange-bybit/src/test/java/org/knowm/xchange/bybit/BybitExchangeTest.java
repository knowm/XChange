package org.knowm.xchange.bybit;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.service.BaseWiremockTest;

public class BybitExchangeTest extends BaseWiremockTest {

  @Test
  public void testSymbolLoading() throws IOException {
    Exchange bybitExchange = createExchange();

    initGetStub("/v5/market/instruments-info", "/getInstrumentLinear.json5");
    initGetStub("/v5/account/fee-rate", "/getFeeRates.json5");

    ExchangeSpecification specification = bybitExchange.getExchangeSpecification();
    specification.setShouldLoadRemoteMetaData(true);
    bybitExchange.applySpecification(specification);

    assertThat(bybitExchange.getExchangeMetaData().getInstruments()).hasSize(1);
  }
}
