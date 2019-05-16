package org.knowm.xchange.examples.bitcoinium;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinium.BitcoiniumExchange;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumOrderbook;
import org.knowm.xchange.bitcoinium.dto.marketdata.BitcoiniumTicker;
import org.knowm.xchange.bitcoinium.service.BitcoiniumMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting Market Data from CampBX */
public class BitcoiniumMarketDataDemo {

  public static void main(String[] args) throws Exception {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(BitcoiniumExchange.class.getName());
    exchangeSpecification.setApiKey("42djci5kmbtyzrvglfdw3e2dgmh5mr37");
    System.out.println(exchangeSpecification.toString());
    Exchange bitcoiniumExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    generic(bitcoiniumExchange);
    raw(bitcoiniumExchange);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService bitcoiniumGenericMarketDataService = exchange.getMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker =
        bitcoiniumGenericMarketDataService.getTicker(new CurrencyPair("BTC", "BITSTAMP_USD"));

    System.out.println("Last: " + ticker.getLast());
    System.out.println("Bid: " + ticker.getBid());
    System.out.println("Ask: " + ticker.getAsk());
    System.out.println("Volume: " + ticker.getVolume());

    // Get the latest order book data for BTC/USD
    OrderBook orderBook =
        bitcoiniumGenericMarketDataService.getOrderBook(
            new CurrencyPair("BTC", "BITSTAMP_USD"), "TEN_PERCENT");

    System.out.println("Order book: " + orderBook);
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    BitcoiniumMarketDataServiceRaw bitcoiniumSpecificMarketDataService =
        (BitcoiniumMarketDataServiceRaw) exchange.getMarketDataService();

    // Get the latest ticker data showing BTC to USD
    BitcoiniumTicker bitcoiniumTicker =
        bitcoiniumSpecificMarketDataService.getBitcoiniumTicker("BTC", "BITSTAMP_USD");

    System.out.println("Last: " + bitcoiniumTicker.getLast());
    System.out.println("Bid: " + bitcoiniumTicker.getBid());
    System.out.println("Ask: " + bitcoiniumTicker.getAsk());
    System.out.println("Volume: " + bitcoiniumTicker.getVolume());

    // Get the latest order book data for BTC/USD - MtGox
    BitcoiniumOrderbook bitcoiniumOrderbook =
        bitcoiniumSpecificMarketDataService.getBitcoiniumOrderbook(
            "BTC", "BITSTAMP_USD", "TEN_PERCENT");

    System.out.println("Order book: " + bitcoiniumOrderbook);
  }
}
