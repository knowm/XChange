package org.knowm.xchange.examples.hitbtc.marketdata;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.hitbtc.HitbtcExampleUtils;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;
import org.knowm.xchange.hitbtc.v2.service.HitbtcMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HitbtcMarketDataDemo {

  public static void main(String[] args) throws Exception {

    Exchange hitbtcExchange = HitbtcExampleUtils.createExchange();

    hitbtcExchange.remoteInit();
    System.out.println(
        "Market metadata: " + hitbtcExchange.getExchangeMetaData().getCurrencyPairs().toString());

    MarketDataService marketDataService = hitbtcExchange.getMarketDataService();

    generic(marketDataService);
    raw((HitbtcMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println("BTC / USD Ticker: " + ticker.toString());

    // Get the latest order book data for BTC/USD
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

    System.out.println(
        "Current Order Book size for BTC/USD: "
            + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());

    // Get the latest trade data for BTC/USD
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println("Trades, default. Size=" + trades.getTrades().size());
  }

  private static void raw(HitbtcMarketDataServiceRaw marketDataService) throws IOException {

    HitbtcTicker ticker = marketDataService.getHitbtcTicker(CurrencyPair.BTC_USD);
    System.out.println("BTC/USD Ticker: " + ticker.toString());

    Map<String, HitbtcTicker> tickers = marketDataService.getHitbtcTickers();
    System.out.println("All Tickers: " + tickers.toString());

    // Get the latest order book data for BTC/USD
    HitbtcOrderBook orderBook = marketDataService.getHitbtcOrderBook(CurrencyPair.BTC_USD);

    System.out.println(
        "Current Order Book size for BTC/USD: "
            + (orderBook.getAsks().length + orderBook.getBids().length));

    System.out.println(orderBook);
  }
}
