package org.knowm.xchange.coinbasepro.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCandle;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;

public class HistoricalCandlesIntegration {

  @Test
  public void tickerFetchTest() throws Exception {

    Exchange exchange =
        ExchangeFactory.INSTANCE.createExchange(CoinbaseProExchange.class.getName());
    CoinbaseProMarketDataService mds =
        (CoinbaseProMarketDataService) exchange.getMarketDataService();
    CoinbaseProCandle[] candles =
        mds.getCoinbaseProHistoricalCandles(
            CurrencyPair.BTC_USD, "2018-02-01T00:00:00Z", "2018-02-01T00:10:00Z", "60");
    System.out.println(Arrays.toString(candles));
    assertThat(candles).hasSize(11);
  }
}
