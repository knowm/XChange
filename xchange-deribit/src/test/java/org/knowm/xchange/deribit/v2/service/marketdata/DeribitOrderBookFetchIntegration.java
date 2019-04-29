package org.knowm.xchange.deribit.v2.service.marketdata;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.dto.marketdata.DeribitOrderBook;
import org.knowm.xchange.deribit.v2.service.DeribitMarketDataService;

import static org.assertj.core.api.Assertions.assertThat;

public class DeribitOrderBookFetchIntegration {

  private static Exchange exchange;
  private static DeribitMarketDataService deribitMarketDataService;

  @BeforeClass
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    exchange.applySpecification(((DeribitExchange) exchange).getSandboxExchangeSpecification());
    deribitMarketDataService = (DeribitMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void getDeribitOrderBookTest() throws Exception {
    DeribitOrderBook orderBook = deribitMarketDataService.getDeribitOrderbook("BTC-PERPETUAL");

    assertThat(orderBook).isNotNull();
  }
}
