package org.knowm.xchange.examples.coinbasepro;

import java.io.IOException;
import java.util.Arrays;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCandle;
import org.knowm.xchange.coinbasepro.service.CoinbaseProMarketDataService;
import org.knowm.xchange.currency.CurrencyPair;

public class CoinbaseProHistoricalCandlesDemo {

  public static void main(String[] args) throws IOException {
    Exchange coinbasePro =
        ExchangeFactory.INSTANCE.createExchange(CoinbaseProExchange.class.getName());
    CoinbaseProMarketDataService mds =
        (CoinbaseProMarketDataService) coinbasePro.getMarketDataService();
    CoinbaseProCandle[] candles =
        mds.getCoinbaseProHistoricalCandles(
            CurrencyPair.BTC_USD, "2018-02-01T00:00:00Z", "2018-02-01T00:10:00Z", "60");
    System.out.println(Arrays.toString(candles));
  }
}
