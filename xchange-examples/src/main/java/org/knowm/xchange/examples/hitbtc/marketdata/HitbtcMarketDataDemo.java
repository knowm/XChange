package org.knowm.xchange.examples.hitbtc.marketdata;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.hitbtc.HitbtcExampleUtils;
import org.knowm.xchange.hitbtc.dto.general.HitbtcSort;
import org.knowm.xchange.hitbtc.dto.marketdata.*;
import org.knowm.xchange.hitbtc.service.HitbtcMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class HitbtcMarketDataDemo {

  public static void main(String[] args) throws Exception {

    Exchange hitbtcExchange = HitbtcExampleUtils.createExchange();

    hitbtcExchange.remoteInit();
    System.out.println("Market metadata: " + hitbtcExchange.getExchangeMetaData().getCurrencyPairs().toString());

    MarketDataService marketDataService = hitbtcExchange.getMarketDataService();

    System.out.println("--------------------- Generic Data ------------------");
    generic(marketDataService);
    System.out.println("--------------------- RAW Data ----------------------");
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

    trades = marketDataService.getTrades(CurrencyPair.BTC_USD, 0L, HitbtcTrade.HitbtcTradesSortField.SORT_BY_TRADE_ID,
        HitbtcSort.SORT_ASCENDING, 0L, 10);
    System.out.println("Trades, first 10, Size= " + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.BTC_USD, System.currentTimeMillis() - 1000 * 60,
        HitbtcTrade.HitbtcTradesSortField.SORT_BY_TIMESTAMP, HitbtcSort.SORT_DESCENDING, 0L, 100);
    System.out.println("Trades, last minute, Size= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

  private static void raw(HitbtcMarketDataServiceRaw marketDataService) throws IOException {

    List<HitbtcSymbol> symbols = marketDataService.getHitbtcSymbols();
    System.out.println("Market metadata: " + symbols.toString());

    HitbtcTicker ticker = marketDataService.getHitbtcTicker(CurrencyPair.BTC_USD);
    System.out.println("BTC/USD Ticker: " + ticker.toString());

    List<HitbtcTicker> tickers = marketDataService.getHitbtcTickers();
    System.out.println("All Tickers: " + tickers.toString());

    // Get the latest order book data for BTC/USD
    HitbtcOrderBook orderBook = marketDataService.getHitbtcOrderBook(CurrencyPair.BTC_USD);

    System.out.println("Current Order Book size for BTC/USD: " + (orderBook.getAsks().length + orderBook.getBids().length));

    System.out.println("First Ask: " + orderBook.getAsks()[0]);

    System.out.println("First Bid: " + orderBook.getBids()[0]);

    System.out.println(orderBook);

    // Get the latest trade data for BTC/USD
    List<HitbtcTrade> trades = marketDataService.getHitbtcTradesRecent(CurrencyPair.BTC_USD, 1000);
    System.out.println("Trades, recent, Size= " + trades.size());

    trades = marketDataService.getHitbtcTrades(CurrencyPair.BTC_USD, 10,0, HitbtcTrade.HitbtcTradesSortField.SORT_BY_TRADE_ID,
        HitbtcSort.SORT_ASCENDING, 0);
    System.out.println("Trades, first 10, Size= " + trades.size());

    trades = marketDataService.getHitbtcTrades(CurrencyPair.BTC_USD, 1000,System.currentTimeMillis() - 1000 * 60,
        HitbtcTrade.HitbtcTradesSortField.SORT_BY_TIMESTAMP, HitbtcSort.SORT_ASCENDING, 0);
    System.out.println("Trades, last minute, Size= " + trades.size());
    System.out.println(trades.toString());
  }

}