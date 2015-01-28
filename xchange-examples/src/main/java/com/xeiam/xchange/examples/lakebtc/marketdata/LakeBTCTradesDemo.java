package com.xeiam.xchange.examples.lakebtc.marketdata;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.examples.lakebtc.LakeBTCExamplesUtils;
import com.xeiam.xchange.lakebtc.service.polling.LakeBTCMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Created by Cristi on 12/22/2014.
 */
public class LakeBTCTradesDemo {

  public static void main(String[] args) throws IOException {
    Exchange lakebtcExchange = LakeBTCExamplesUtils.createTestExchange();
    generic(lakebtcExchange);
    raw(lakebtcExchange);
  }

  private static void generic(Exchange lakebtcExchange) throws IOException {

    PollingMarketDataService marketDataService = lakebtcExchange.getPollingMarketDataService();
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_CNY, 0L);
    System.out.println(trades);
    System.out.println("Trades(0): " + trades.getTrades().get(0).toString());
    System.out.println("Trades size: " + trades.getTrades().size());

    // Get the latest trade data for BTC_USD for the past 12 hours (note: doesn't account for time zone differences, should use UTC instead)
    trades = marketDataService.getTrades(CurrencyPair.BTC_USD, (long) (System.nanoTime() - (12 * 60 * 60 * Math.pow(10, 9))));
    System.out.println(trades);
    System.out.println("Trades size: " + trades.getTrades().size());
  }

  private static void raw(Exchange lakeBtcExchange) throws IOException {
    LakeBTCMarketDataServiceRaw marketDataService = (LakeBTCMarketDataServiceRaw) lakeBtcExchange.getPollingMarketDataService();
    BigDecimal[][] trades = marketDataService.getLakeBTCOrderBookCNY().getAsks();

    System.out.println("Ask size: " + trades.length);
    System.out.println("Ask(0): " + trades[0].toString());
    System.out.println("Last: " + trades[trades.length - 1]);

  }
}
