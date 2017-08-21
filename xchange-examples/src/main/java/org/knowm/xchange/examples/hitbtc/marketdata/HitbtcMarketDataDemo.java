package org.knowm.xchange.examples.hitbtc.marketdata;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.hitbtc.HitbtcExampleUtils;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcOrderBook;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcSymbols;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTicker;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTime;
import org.knowm.xchange.hitbtc.dto.marketdata.HitbtcTrades;
import org.knowm.xchange.hitbtc.service.HitbtcMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HitbtcMarketDataDemo {

  public static void main(String[] args) throws Exception {

    Exchange hitbtcExchange = HitbtcExampleUtils.createExchange();

    hitbtcExchange.remoteInit();
    System.out.println("Market metadata: " + hitbtcExchange.getExchangeMetaData().getCurrencyPairs().toString());

    MarketDataService marketDataService = hitbtcExchange.getMarketDataService();

    generic(marketDataService);
    raw((HitbtcMarketDataServiceRaw) marketDataService);

  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
    System.out.println("BTC / USD Ticker: " + ticker.toString());

    // Get the latest order book data for BTC/USD
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_USD);

    System.out.println("Current Order Book size for BTC/USD: " + (orderBook.getAsks().size() + orderBook.getBids().size()));

    System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());

    System.out.println("First Bid: " + orderBook.getBids().get(0).toString());

    System.out.println(orderBook.toString());

    // Get the latest trade data for BTC/USD
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println("Trades, default. Size=" + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.BTC_USD, 0L, HitbtcTrades.HitbtcTradesSortField.SORT_BY_TRADE_ID,
        HitbtcTrades.HitbtcTradesSortDirection.SORT_ASCENDING, 0L, 10L);
    System.out.println("Trades, first 10, Size= " + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.BTC_USD, System.currentTimeMillis() - 1000 * 60,
        HitbtcTrades.HitbtcTradesSortField.SORT_BY_TIMESTAMP, HitbtcTrades.HitbtcTradesSortDirection.SORT_DESCENDING, 0L, 1000L);
    System.out.println("Trades, last minute, Size= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

  private static void raw(HitbtcMarketDataServiceRaw marketDataService) throws IOException {

    HitbtcSymbols symbols = marketDataService.getHitbtcSymbols();
    System.out.println("Market metadata: " + symbols.toString());

    HitbtcTime time = marketDataService.getHitbtcTime();
    System.out.println("Server time: " + time.toString());

    HitbtcTicker ticker = marketDataService.getHitbtcTicker(CurrencyPair.BTC_USD);
    System.out.println("BTC/USD Ticker: " + ticker.toString());

    Map<String, HitbtcTicker> tickers = marketDataService.getHitbtcTickers();
    System.out.println("All Tickers: " + tickers.toString());

    // Get the latest order book data for BTC/USD
    HitbtcOrderBook orderBook = marketDataService.getHitbtcOrderBook(CurrencyPair.BTC_USD);

    System.out.println("Current Order Book size for BTC/USD: " + (orderBook.getAsks().length + orderBook.getBids().length));

    System.out.println("First Ask: " + Arrays.toString(orderBook.getAsks()[0]));

    System.out.println("First Bid: " + Arrays.toString(orderBook.getBids()[0]));

    System.out.println(orderBook);

    // Get the latest trade data for BTC/USD
    HitbtcTrades trades = marketDataService.getHitbtcTradesRecent(CurrencyPair.BTC_USD, 1000);
    System.out.println("Trades, recent, Size= " + trades.getHitbtcTrades().size());

    trades = marketDataService.getHitbtcTrades(CurrencyPair.BTC_USD, 0, HitbtcTrades.HitbtcTradesSortField.SORT_BY_TRADE_ID,
        HitbtcTrades.HitbtcTradesSortDirection.SORT_ASCENDING, 0, 10);
    System.out.println("Trades, first 10, Size= " + trades.getHitbtcTrades().size());

    trades = marketDataService.getHitbtcTrades(CurrencyPair.BTC_USD, System.currentTimeMillis() - 1000 * 60,
        HitbtcTrades.HitbtcTradesSortField.SORT_BY_TIMESTAMP, HitbtcTrades.HitbtcTradesSortDirection.SORT_ASCENDING, 0, 1000);
    System.out.println("Trades, last minute, Size= " + trades.getHitbtcTrades().size());
    System.out.println(trades.toString());
  }

}
