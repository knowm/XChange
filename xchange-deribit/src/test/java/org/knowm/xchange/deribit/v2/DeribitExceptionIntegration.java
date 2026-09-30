package org.knowm.xchange.deribit.v2;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.service.DeribitMarketDataService;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.instrument.Instrument;

class DeribitExceptionIntegration {
  private static Exchange exchange;
  private static DeribitMarketDataService deribitMarketDataService;

  @BeforeAll
  static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    exchange.applySpecification(((DeribitExchange) exchange).getSandboxExchangeSpecification());
    deribitMarketDataService = (DeribitMarketDataService) exchange.getMarketDataService();
  }

  @Test
  void getTickerThrowsExceptionTest() throws Exception {
    Instrument pair = new CurrencyPair("?", "?");
    assertThatExceptionOfType(CurrencyPairNotValidException.class)
        .isThrownBy(() -> deribitMarketDataService.getTicker(pair));
  }

  @Test
  void getDeribitTickerThrowsExceptionTest() throws Exception {
    assertThatExceptionOfType(DeribitException.class)
        .isThrownBy(() -> deribitMarketDataService.getDeribitTicker("?"));
  }

  @Test
  void getDeribitInstrumentsThrowsIllegalArgumentExceptionTest() throws Exception {
    assertThatExceptionOfType(DeribitException.class)
        .isThrownBy(
            () -> deribitMarketDataService.getDeribitInstruments("BTC-PERPETUAAAAL", null, null));
  }
}
