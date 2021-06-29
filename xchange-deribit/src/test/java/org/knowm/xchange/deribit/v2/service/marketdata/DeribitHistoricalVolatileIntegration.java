package org.knowm.xchange.deribit.v2.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.deribit.v2.DeribitExchange;
import org.knowm.xchange.deribit.v2.service.DeribitMarketDataService;

public class DeribitHistoricalVolatileIntegration {

  private static Exchange exchange;
  private static DeribitMarketDataService deribitMarketDataService;

  @BeforeClass
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    exchange.applySpecification(((DeribitExchange) exchange).getSandboxExchangeSpecification());
    deribitMarketDataService = (DeribitMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void getHistoricalVol() throws Exception {
    List<List<BigDecimal>> historyVolatile =
        deribitMarketDataService.getHistoricalVolatility("BTC");

    assertThat(historyVolatile).isNotNull();
    assertThat(historyVolatile).isNotEmpty();
    assertThat(historyVolatile.get(0)).isNotEmpty();
  }
}
