package org.xchange.bitz.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.xchange.bitz.BitZExchange;
import org.xchange.bitz.dto.marketdata.BitZKline;
import org.xchange.bitz.service.BitZMarketDataService;

public class BitzKlineFetchIntegration {

  @Test
  public void ordersFetchTest() throws Exception {
    // Get Specific Exchange
    BitZExchange exchange =
        (BitZExchange) ExchangeFactory.INSTANCE.createExchange(BitZExchange.class.getName());
    BitZMarketDataService marketDataService =
        (BitZMarketDataService) exchange.getMarketDataService();

    BitZKline kline = marketDataService.getKline(CurrencyPair.LTC_BTC, "5m");

    // Verify Not Null Values
    assertThat(kline).isNotNull();

    // TODO: Logical Verification Of Values
  }
}
