package org.knowm.xchange.deribit.v1.service.marketdata;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.deribit.v1.DeribitExchange;
import org.knowm.xchange.deribit.v1.dto.marketdata.DeribitCurrency;
import org.knowm.xchange.deribit.v1.dto.marketdata.DeribitSummary;
import org.knowm.xchange.deribit.v1.service.DeribitMarketDataService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    DeribitSummary summary = deribitMarketDataService.getSummary("BTC-PERPETUAL");

    assertThat(summary).isNotNull();
    assertThat(summary.getInstrumentName()).isEqualTo("BTC-PERPETUAL");
  }

  @Test
  public void getDeribitAllSummariesTest() throws Exception {
    List<DeribitSummary> summaries = deribitMarketDataService.getAllSummaries();

    assertThat(summaries).isNotEmpty();
  }
}
