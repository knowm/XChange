package org.knowm.xchange.examples.coinbene.marketdata;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneOrderBook;
import org.knowm.xchange.coinbene.dto.marketdata.CoinbeneTicker;
import org.knowm.xchange.coinbene.service.CoinbeneMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.coinbene.CoinbeneDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;

public class CoinbeneMarketDataDemo {

  public static void main(String[] args) throws Exception {

    Exchange CoinbeneExchange = CoinbeneDemoUtils.createExchange();

    CoinbeneExchange.remoteInit();
    System.out.println(
        "Market metadata: "
            + CoinbeneExchange.getExchangeMetaData().getCurrencyPairs().toString());

    MarketDataService marketDataService = CoinbeneExchange.getMarketDataService();

    generic(marketDataService);
    raw((CoinbeneMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USDT);
    System.out.println("BTC / USDT Ticker: " + ticker.toString());

    // Get the latest order book data for BTC/USD
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USDT);

    System.out.println(
        "Current Order Book size for BTC/USD: "
            + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());

    // Get the latest trade data for BTC/USD
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USDT);
    System.out.println("Trades, default. Size=" + trades.getTrades().size());
  }

  private static void raw(CoinbeneMarketDataServiceRaw marketDataService) throws IOException {

    CoinbeneTicker ticker =
        marketDataService.getCoinbeneTicker(CurrencyPair.BTC_USDT).getTicker();
    System.out.println("BTC/USD Ticker: " + ticker.toString());

    // Get the latest order book data for BTC/USD
    CoinbeneOrderBook orderBook =
        marketDataService.getCoinbeneOrderBook(CurrencyPair.BTC_USDT).getOrderBook();

    System.out.println(orderBook);
  }
}
