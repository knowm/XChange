package org.knowm.xchange.examples.cryptocom.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptocom.service.CryptoComMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.cryptocom.CryptoComDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CryptoComMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = CryptoComDemoUtils.createExchange();

    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(marketDataService);
    raw((CryptoComMarketDataServiceRaw) marketDataService);
  }

  public static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USDT);
    System.out.println("Ticker: " + ticker);

    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USDT);
    System.out.println(
        "Order book: "
            + orderBook.getAsks().size()
            + " asks, "
            + orderBook.getBids().size()
            + " bids");

    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USDT);
    System.out.println("Trades: " + trades.getTrades().size());
  }

  public static void raw(CryptoComMarketDataServiceRaw marketDataService) throws IOException {

    System.out.println("Instruments: " + marketDataService.getCryptoComInstruments().size());
  }
}
