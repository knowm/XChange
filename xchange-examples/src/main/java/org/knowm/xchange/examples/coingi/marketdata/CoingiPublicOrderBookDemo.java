package org.knowm.xchange.examples.coingi.marketdata;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.examples.coingi.CoingiDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoingiPublicOrderBookDemo {
  public static void main(String[] args) throws IOException {
    Exchange coingi = CoingiDemoUtils.createExchange();

    MarketDataService marketDataService = coingi.getMarketDataService();
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_EUR);

    // The following example limits max asks to 10, max bids to 10, and market depth to 32
    // OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_EUR, 10, 10, 32);

    List<LimitOrder> asks = orderBook.getAsks();
    List<LimitOrder> bids = orderBook.getBids();

    asks.forEach(System.out::println);
    bids.forEach(System.out::println);

    System.out.printf(
        "Received an order book with the latest %d asks and %d bids.\n", asks.size(), bids.size());
  }
}
