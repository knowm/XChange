package org.knowm.xchange.examples.lakebtc.marketdata;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import org.knowm.xchange.lakebtc.service.LakeBTCMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Created by Cristi on 12/22/2014. */
public class LakeBTCTradesDemo {

  public static void main(String[] args) throws IOException {
    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();
    generic(lakebtcExchange);
    raw(lakebtcExchange);
  }

  private static void generic(Exchange lakebtcExchange) throws IOException {

    MarketDataService marketDataService = lakebtcExchange.getMarketDataService();
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_CNY, 0L);
    System.out.println(trades);
    System.out.println("Trades(0): " + trades.getTrades().get(0).toString());
    System.out.println("Trades size: " + trades.getTrades().size());

    // Get the latest trade data for BTC_USD for the past 12 hours (note: doesn't account for time
    // zone differences, should use UTC instead)
    trades =
        marketDataService.getTrades(
            CurrencyPair.BTC_USD, (long) (System.nanoTime() - (12 * 60 * 60 * Math.pow(10, 9))));
    System.out.println(trades);
    System.out.println("Trades size: " + trades.getTrades().size());
  }

  private static void raw(Exchange lakeBtcExchange) throws IOException {
    LakeBTCMarketDataServiceRaw marketDataService =
        (LakeBTCMarketDataServiceRaw) lakeBtcExchange.getMarketDataService();
    BigDecimal[][] trades = marketDataService.getLakeOrderBook(CurrencyPair.BTC_CNY).getAsks();

    System.out.println("Ask size: " + trades.length);
    System.out.println("Ask(0): " + Arrays.toString(trades[0]));
    System.out.println("Last: " + trades[trades.length - 1]);
  }
}
