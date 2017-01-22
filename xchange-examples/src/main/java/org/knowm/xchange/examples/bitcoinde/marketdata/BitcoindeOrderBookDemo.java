package org.knowm.xchange.examples.bitcoinde.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinde.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderBook;
import org.knowm.xchange.bitcoinde.service.BitcoindeMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitcoindeOrderBookDemo {

  public static void main(String[] args) throws IOException {

    /* get the api key from args */
    if (args.length != 1) {
      System.err.println("Usage: java BitcoindeOrderBookDemo <api_key>");
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

    generic(marketDataService);
    raw((BitcoindeMarketDataServiceRaw) marketDataService);

  }

  public static void generic(MarketDataService marketDataService) throws IOException {

    /* get OrderBook data */
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_EUR);
    System.out.println(orderBook.toString());
  }

  public static void raw(BitcoindeMarketDataServiceRaw marketDataService) throws IOException {

    /* get BitcoindeOrderBook data */
    BitcoindeOrderBook bitcoindeOrderBook = marketDataService.getBitcoindeOrderBook();
    System.out.println(bitcoindeOrderBook.toString());
  }
}
