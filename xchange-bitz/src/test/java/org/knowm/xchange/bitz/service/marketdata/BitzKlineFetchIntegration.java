package org.knowm.xchange.bitz.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitz.BitZExchange;
import org.knowm.xchange.bitz.dto.marketdata.BitZKline;
import org.knowm.xchange.bitz.service.BitZMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;

public class BitzKlineFetchIntegration {

  @Test
  public void ordersFetchTest() throws Exception {
    // Get Specific Exchange
    BitZExchange exchange =
        (BitZExchange) ExchangeFactory.INSTANCE.createExchange(BitZExchange.class);
    BitZMarketDataService marketDataService =
        (BitZMarketDataService) exchange.getMarketDataService();

    BitZKline kline = marketDataService.getKline(CurrencyPair.LTC_BTC, "5m");

    // Verify Not Null Values
    assertThat(kline).isNotNull();

    // TODO: Logical Verification Of Values
  }
}
