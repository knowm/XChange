package org.knowm.xchange.examples.gdax;

import java.io.IOException;
import java.util.Arrays;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.gdax.dto.marketdata.GDAXCandle;
import org.knowm.xchange.gdax.service.GDAXMarketDataService;

public class GDAXHistoricalCandlesDemo {

  public static void main(String[] args) throws IOException {
    Exchange gdax = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getName());
    GDAXMarketDataService mds = (GDAXMarketDataService) gdax.getMarketDataService();
    GDAXCandle[] candles =
        mds.getGDAXHistoricalCandles(
            CurrencyPair.BTC_USD, "2018-02-01T00:00:00Z", "2018-02-01T00:10:00Z", "60");
    System.out.println(Arrays.toString(candles));
  }
}
