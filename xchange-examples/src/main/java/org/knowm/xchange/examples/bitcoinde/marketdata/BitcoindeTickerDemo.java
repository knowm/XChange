package org.knowm.xchange.examples.bitcoinde.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinde.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeRate;
import org.knowm.xchange.bitcoinde.service.BitcoindeMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitcoindeTickerDemo {

  public static void main(String[] args) throws IOException {

    /* get the api key from args */
    if (args.length != 1) {
      System.err.println("Usage: java BitcoindeTickerDemo <api_key>");
      System.exit(1);
    }
    final String API_KEY = args[0];

    /* configure the exchange to use our api key */
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoindeExchange.class.getName());
    exchangeSpecification.setApiKey(API_KEY);

    /* create the exchange object */
    Exchange bitcoindeExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    /* create a data service from the exchange */
    MarketDataService marketDataService = bitcoindeExchange.getMarketDataService();

    /*
     * We can't get a real ticker since Bitcoin.de doesn't support it, but we can get an exchange rate. Use a BitcoindeRate object for this.
     */
    raw((BitcoindeMarketDataServiceRaw) marketDataService);

  }

  public static void raw(BitcoindeMarketDataServiceRaw marketDataService) throws IOException {

    /* get BitcoindeRate data */
    BitcoindeRate bitcoindeRate = marketDataService.getBitcoindeRate();
    System.out.println(bitcoindeRate.toString());
  }

}
