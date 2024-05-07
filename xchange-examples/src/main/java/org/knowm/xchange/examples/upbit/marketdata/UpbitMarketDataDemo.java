package org.knowm.xchange.examples.upbit.marketdata;

import java.io.IOException;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParam;
import org.knowm.xchange.service.trade.params.DefaultCandleStickParamWithLimit;
import org.knowm.xchange.upbit.UpbitExchange;

/** Demonstrate requesting Ticker at Upbit */
public class UpbitMarketDataDemo {

  public static void main(String[] args) throws IOException {

    // Create Default Upbit Instance
    Exchange upbit = ExchangeFactory.INSTANCE.createExchange(UpbitExchange.class);

    // Get The Public Market Data Service
    MarketDataService marketDataService = upbit.getMarketDataService();

    // Currency Pair To Get Ticker Of
    CurrencyPair pair = new CurrencyPair(Currency.ETH, Currency.KRW);

    // Print The Generic and Raw Ticker
    System.out.println(marketDataService.getTicker(pair));

    System.out.println(marketDataService.getTickers(null));

    System.out.println(marketDataService.getTrades(pair));

    System.out.println(
        marketDataService.getCandleStickData(
            pair, new DefaultCandleStickParam(new Date(), new Date(), 600)));
    System.out.println(
        marketDataService.getCandleStickData(
            pair, new DefaultCandleStickParamWithLimit(new Date(), new Date(), 60, 5)));
  }
}
