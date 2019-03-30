package org.knowm.xchange.deribit.v1.service.marketdata;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.deribit.v1.DeribitExchange;
import org.knowm.xchange.deribit.v1.dto.marketdata.DeribitInstrument;
import org.knowm.xchange.deribit.v1.dto.marketdata.DeribitOrderbook;
import org.knowm.xchange.deribit.v1.service.DeribitMarketDataService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeribitOrderbookFetchIntegration {

  private static Exchange exchange;
  private static DeribitMarketDataService deribitMarketDataService;

  @BeforeClass
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    exchange.applySpecification(((DeribitExchange) exchange).getSandboxExchangeSpecification());
    deribitMarketDataService = (DeribitMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void getDeribitOrderbookTest() throws Exception {
    DeribitOrderbook orderbook = deribitMarketDataService.getDeribitOrderbook("BTC-PERPETUAL");

    assertThat(orderbook).isNotNull();
  }
}
