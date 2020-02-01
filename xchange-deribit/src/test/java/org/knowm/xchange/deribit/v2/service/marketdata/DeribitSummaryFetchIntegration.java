package org.knowm.xchange.deribit.v2.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitSummary;
import org.knowm.xchange.deribit.v2.service.DeribitMarketDataService;

public class DeribitSummaryFetchIntegration {

  private static Exchange exchange;
  private static DeribitMarketDataService deribitMarketDataService;

  @BeforeClass
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    exchange.applySpecification(((DeribitExchange) exchange).getSandboxExchangeSpecification());
    deribitMarketDataService = (DeribitMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void getDeribitSummaryTest() throws Exception {
    List<DeribitSummary> summary = deribitMarketDataService.getSummaryByInstrument("BTC-PERPETUAL");

    assertThat(summary).isNotEmpty();
    assertThat(summary.get(0).getInstrumentName()).isEqualTo("BTC-PERPETUAL");
  }
}
