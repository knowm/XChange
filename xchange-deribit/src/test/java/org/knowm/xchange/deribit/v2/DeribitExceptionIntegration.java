package org.knowm.xchange.deribit.v2;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.dto.DeribitException;
import org.knowm.xchange.deribit.v2.service.DeribitMarketDataService;
import org.knowm.xchange.instrument.Instrument;

public class DeribitExceptionIntegration {
  private static Exchange exchange;
  private static DeribitMarketDataService deribitMarketDataService;

  @BeforeClass
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    exchange.applySpecification(((DeribitExchange) exchange).getSandboxExchangeSpecification());
    deribitMarketDataService = (DeribitMarketDataService) exchange.getMarketDataService();
  }

  @Test(expected = IllegalArgumentException.class)
  public void getTickerThrowsExceptionTest() throws Exception {
    Instrument pair = new CurrencyPair("?", "?");
    deribitMarketDataService.getTicker(pair);
  }

  @Test(expected = DeribitException.class)
  public void getDeribitTickerThrowsExceptionTest() throws Exception {
    deribitMarketDataService.getDeribitTicker("?");
  }

  @Test(expected = DeribitException.class)
  public void getDeribitInstrumentsThrowsIllegalArgumentExceptionTest() throws Exception {
    deribitMarketDataService.getDeribitInstruments("BTC-PERPETUAAAAL", null, null);
  }
}
