package org.knowm.xchange.deribit.v2.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitTicker;
import org.knowm.xchange.deribit.v2.service.DeribitMarketDataService;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.instrument.Instrument;

public class DeribitTickerFetchIntegration {

  private static Exchange exchange;
  private static DeribitMarketDataService deribitMarketDataService;

  @BeforeClass
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    exchange.applySpecification(((DeribitExchange) exchange).getSandboxExchangeSpecification());
    deribitMarketDataService = (DeribitMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void getDeribitTickerTest() throws Exception {
    DeribitTicker ticker = deribitMarketDataService.getDeribitTicker("BTC-PERPETUAL");

    assertThat(ticker).isNotNull();
    assertThat(ticker.getInstrumentName()).isEqualTo("BTC-PERPETUAL");
    assertThat(ticker.getLastPrice()).isGreaterThan(new BigDecimal("0"));
  }

  @Test
  public void getTickerTest() throws Exception {
    Instrument pair = new FuturesContract(CurrencyPair.BTC_USD, "PERPETUAL");
    Ticker ticker = deribitMarketDataService.getTicker(pair);

    assertThat(ticker).isNotNull();
    assertThat(ticker.getInstrument()).isEqualTo(pair);
  }
}
