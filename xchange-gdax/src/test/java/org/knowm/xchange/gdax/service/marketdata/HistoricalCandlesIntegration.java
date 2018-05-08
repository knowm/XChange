package org.knowm.xchange.gdax.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.gdax.dto.marketdata.GDAXCandle;
import org.knowm.xchange.gdax.service.GDAXMarketDataService;

public class HistoricalCandlesIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getName());
    GDAXMarketDataService mds = (GDAXMarketDataService) exchange.getMarketDataService();
    GDAXCandle[] candles =
        mds.getGDAXHistoricalCandles(
            CurrencyPair.BTC_USD, "2018-02-01T00:00:00Z", "2018-02-01T00:10:00Z", "60");
    System.out.println(Arrays.toString(candles));
    assertThat(candles).hasSize(10);
  }
}
