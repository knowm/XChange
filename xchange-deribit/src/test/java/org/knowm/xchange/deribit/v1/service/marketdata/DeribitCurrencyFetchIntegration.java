package org.knowm.xchange.deribit.v1.service.marketdata;

import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.deribit.v1.DeribitExchange;
import org.knowm.xchange.deribit.v1.dto.marketdata.DeribitCurrency;
import org.knowm.xchange.deribit.v1.service.DeribitMarketDataService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeribitCurrencyFetchIntegration {

  private static Exchange exchange;
  private static DeribitMarketDataService deribitMarketDataService;

  @BeforeClass
  public static void setUp() {
    exchange = ExchangeFactory.INSTANCE.createExchange(DeribitExchange.class);
    exchange.applySpecification(((DeribitExchange) exchange).getSandboxExchangeSpecification());
    deribitMarketDataService = (DeribitMarketDataService) exchange.getMarketDataService();
  }

  @Test
  public void getInstrumentsTest() throws Exception {
    List<DeribitCurrency> currencies = deribitMarketDataService.getCurrencies();

    assertThat(currencies).isNotEmpty();
  }
}
