package org.knowm.xchange.examples.bleutrade.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bleutrade.BleutradeExchange;
import org.knowm.xchange.bleutrade.service.BleutradeMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BleutradeMarketDataDemo {

  private static CurrencyPair currencyPair = CurrencyPair.LTC_BTC;

  public static void main(String[] args) throws IOException, InterruptedException {

    //    Exchange bleutrade = BleutradeDemoUtils.getExchange();
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BleutradeExchange.class.getName());
    Exchange bleutrade = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    MarketDataService dataService = bleutrade.getMarketDataService();

    generic(dataService);
    raw((BleutradeMarketDataServiceRaw) dataService);
  }

  private static void generic(MarketDataService dataService)
      throws IOException, InterruptedException {

    System.out.println(dataService.getTicker(currencyPair));
    Thread.sleep(1000);

    System.out.println(dataService.getOrderBook(currencyPair, 50));
    Thread.sleep(1000);

    System.out.println(dataService.getTrades(currencyPair, 100));
    Thread.sleep(1000);
  }

  private static void raw(BleutradeMarketDataServiceRaw dataService)
      throws IOException, InterruptedException {

    System.out.println(dataService.getBleutradeTicker(currencyPair));
    Thread.sleep(1000);

    System.out.println(dataService.getBleutradeOrderBook(currencyPair, 50));
    Thread.sleep(1000);

    System.out.println(dataService.getBleutradeMarketHistory(currencyPair, 100));
  }
}
