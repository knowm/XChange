package com.xeiam.xchange.examples.okcoin.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.okcoin.FuturesContract;
import com.xeiam.xchange.okcoin.OkCoinExchange;
import com.xeiam.xchange.okcoin.dto.marketdata.OkCoinTrade;
import com.xeiam.xchange.okcoin.service.polling.OkCoinMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class OkCoinTradesDemo {

  public static void main(String[] args) throws IOException {

    ExchangeSpecification exSpec = new ExchangeSpecification(OkCoinExchange.class);

    // flag to set Use_Intl (USD) or China (default)
    exSpec.setExchangeSpecificParametersItem("Use_Intl", false);
    Exchange okcoinExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);

    generic(okcoinExchange);
    // raw(okcoinExchange);
  }

  private static void generic(Exchange okcoinExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = okcoinExchange.getPollingMarketDataService();

    // Get the latest trade data for BTC_CNY
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD, FuturesContract.ThisWeek);
    System.out.println(trades);
    System.out.println("Trades(0): " + trades.getTrades().get(0).toString());
    System.out.println("Trades size: " + trades.getTrades().size());

    // Get the latest trades data for BTC_CNY for the past couple of trades
    trades = marketDataService.getTrades(CurrencyPair.BTC_CNY, trades.getlastID() - 10);
    System.out.println(trades);
    System.out.println("Trades size: " + trades.getTrades().size());
  }

  private static void raw(Exchange okcoinExchange) throws IOException {

    // Interested in the public polling market data feed (no authentication)
    OkCoinMarketDataServiceRaw okCoinMarketDataServiceRaw = (OkCoinMarketDataServiceRaw) okcoinExchange.getPollingMarketDataService();

    // Get the latest trade data for BTC_USD
    OkCoinTrade[] trades = okCoinMarketDataServiceRaw.getTrades(CurrencyPair.BTC_CNY);

    System.out.println("Trades size: " + trades.length);
    System.out.println("newest trade: " + trades[trades.length - 1].toString());

    // Poll for any new trades since last id
    trades = okCoinMarketDataServiceRaw.getTrades(CurrencyPair.BTC_CNY, trades[trades.length - 1].getTid() - 10);
    for (int i = 0; i < trades.length; i++) {
      OkCoinTrade okCoinTrade = trades[i];
      System.out.println(okCoinTrade.toString());
    }
    System.out.println("Trades size: " + trades.length);
  }
}
