package org.knowm.xchange.examples.cobinhood.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodOrderBook;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodTicker;
import org.knowm.xchange.cobinhood.dto.marketdata.CobinhoodTickers;
import org.knowm.xchange.cobinhood.service.CobinhoodMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.cobinhood.CobinhoodDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CobinhoodMarketDataDemo {

  public static void main(String[] args) throws Exception {

    Exchange CobinhoodExchange = CobinhoodDemoUtils.createExchange();

    CobinhoodExchange.remoteInit();
    System.out.println(
        "Market metadata: "
            + CobinhoodExchange.getExchangeMetaData().getCurrencyPairs().toString());

    MarketDataService marketDataService = CobinhoodExchange.getMarketDataService();

    generic(marketDataService);
    raw((CobinhoodMarketDataServiceRaw) marketDataService);
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

  private static void raw(CobinhoodMarketDataServiceRaw marketDataService) throws IOException {

    CobinhoodTicker ticker =
        marketDataService.getCobinhoodTicker(CurrencyPair.BTC_USDT).getResult().getTicker();
    System.out.println("BTC/USD Ticker: " + ticker.toString());

    CobinhoodTickers tickers = marketDataService.getCobinhoodTickers().getResult();
    System.out.println("All Tickers: " + tickers.toString());

    // Get the latest order book data for BTC/USD
    CobinhoodOrderBook orderBook =
        marketDataService.getCobinhoodOrderBook(CurrencyPair.BTC_USDT).getResult().getOrderBook();

    System.out.println(orderBook);
  }
}
