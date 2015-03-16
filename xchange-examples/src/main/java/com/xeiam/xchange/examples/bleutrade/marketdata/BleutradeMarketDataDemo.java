package com.xeiam.xchange.examples.bleutrade.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bleutrade.BleutradeExchange;
import com.xeiam.xchange.bleutrade.service.polling.BleutradeMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BleutradeMarketDataDemo {

  private static CurrencyPair currencyPair = CurrencyPair.LTC_BTC;

  public static void main(String[] args) throws IOException, InterruptedException {

    //    Exchange bleutrade = BleutradeDemoUtils.getExchange();
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BleutradeExchange.class.getName());
    Exchange bleutrade = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    PollingMarketDataService dataService = bleutrade.getPollingMarketDataService();

    generic(dataService);
    raw((BleutradeMarketDataServiceRaw) dataService);
  }

  private static void generic(PollingMarketDataService dataService) throws IOException, InterruptedException {

    System.out.println(dataService.getExchangeSymbols());
    Thread.sleep(1000);

    System.out.println(dataService.getTicker(currencyPair));
    Thread.sleep(1000);

    System.out.println(dataService.getOrderBook(currencyPair, 50));
    Thread.sleep(1000);

    System.out.println(dataService.getTrades(currencyPair, 100));
    Thread.sleep(1000);

  }

  private static void raw(BleutradeMarketDataServiceRaw dataService) throws IOException, InterruptedException {

    System.out.println(dataService.getBleutradeTicker(currencyPair));
    Thread.sleep(1000);

    System.out.println(dataService.getBleutradeOrderBook(currencyPair, 50));
    Thread.sleep(1000);

    System.out.println(dataService.getBleutradeMarketHistory(currencyPair, 100));
  }

}
