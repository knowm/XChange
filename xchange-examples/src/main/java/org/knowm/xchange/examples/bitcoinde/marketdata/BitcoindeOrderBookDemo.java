package org.knowm.xchange.examples.bitcoinde.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.dto.marketdata.BitcoindeOrderbookWrapper;
import org.knowm.xchange.bitcoinde.service.BitcoindeMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.examples.bitcoinde.ExchangeUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitcoindeOrderBookDemo {

  public static void main(String[] args) throws IOException {

    Exchange bitcoindeExchange = ExchangeUtils.createExchangeFromJsonConfiguration();

    /* create a data service from the exchange */
    MarketDataService marketDataService = bitcoindeExchange.getMarketDataService();

    for (int i = 0; i < 10; i++) {

      generic(marketDataService);
    }
    //    raw((BitcoindeMarketDataServiceRaw) marketDataService);

  }

  public static void generic(MarketDataService marketDataService) throws IOException {

    /* get OrderBook data */
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_EUR);
    //    System.out.println(orderBook.toString());

    System.out.println(
        "Current Order Book size: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
    System.out.println(
        "Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
    System.out.println(
        "Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());
  }

  public static void raw(BitcoindeMarketDataServiceRaw marketDataService) throws IOException {

    /* get BitcoindeOrderBook data */
    BitcoindeOrderbookWrapper bitcoindeOrderBook =
        marketDataService.getBitcoindeOrderBook(CurrencyPair.BTC_EUR);
    System.out.println(bitcoindeOrderBook.toString());
  }
}
