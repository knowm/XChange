package org.knowm.xchange.bybit;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.matching.ContainsPattern;
import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.service.BaseWiremockTest;

public class BybitExchangeTest extends BaseWiremockTest {

  @Test
  public void testSymbolLoading() throws IOException {
    Exchange bybitExchange = createExchange();

    initGetStub(
        "/v5/market/instruments-info",
        "/getInstrumentSpot.json5",
        "category",
        new ContainsPattern("spot"));
    initGetStub(
        "/v5/market/instruments-info",
        "/getInstrumentLinear.json5",
        "category",
        new ContainsPattern("linear"));
    initGetStub(
        "/v5/market/instruments-info",
        "/getInstrumentInverse.json5",
        "category",
        new ContainsPattern("inverse"));
    initGetStub(
        "/v5/market/instruments-info",
        "/getInstrumentOption.json5",
        "category",
        new ContainsPattern("option"));
    initGetStub("/v5/account/fee-rate", "/getFeeRates.json5");

    ExchangeSpecification specification = bybitExchange.getExchangeSpecification();
    specification.setShouldLoadRemoteMetaData(true);
    bybitExchange.applySpecification(specification);

    assertThat(bybitExchange.getExchangeMetaData().getInstruments()).hasSize(4);
  }
}
