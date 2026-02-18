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

public class DeribitExceptionIntegration {
  private static Exchange exchange;
  private static DeribitMarketDataService deribitMarketDataService;

  @BeforeAll
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    exchange.applySpecification(((DeribitExchange) exchange).getSandboxExchangeSpecification());
    deribitMarketDataService = (DeribitMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void getTickerThrowsExceptionTest() throws Exception {
    Instrument pair = new CurrencyPair("?", "?");
    assertThatExceptionOfType(CurrencyPairNotValidException.class)
        .isThrownBy(() -> deribitMarketDataService.getTicker(pair));
  }

  @Test
  public void getDeribitTickerThrowsExceptionTest() throws Exception {
    assertThatExceptionOfType(DeribitException.class)
        .isThrownBy(() -> deribitMarketDataService.getDeribitTicker("?"));
  }

  @Test
  public void getDeribitInstrumentsThrowsIllegalArgumentExceptionTest() throws Exception {
    assertThatExceptionOfType(DeribitException.class)
        .isThrownBy(
            () -> deribitMarketDataService.getDeribitInstruments("BTC-PERPETUAAAAL", null, null));
  }
}
