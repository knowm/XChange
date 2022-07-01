package org.knowm.xchange.examples.dsx.marketdata;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.dto.DsxOrderBook;
import org.knowm.xchange.dsx.dto.DsxTicker;
import org.knowm.xchange.dsx.service.DsxMarketDataServiceRaw;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.dsx.DsxExampleUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class DsxMarketDataDemo {

  public static void main(String[] args) throws Exception {

    Exchange dsxExchange = DsxExampleUtils.createExchange();

    dsxExchange.remoteInit();
    System.out.println(
        "Market metadata: " + dsxExchange.getExchangeMetaData().getCurrencyPairs().toString());

    MarketDataService marketDataService = dsxExchange.getMarketDataService();

    generic(marketDataService);
    raw((DsxMarketDataServiceRaw) marketDataService);
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

  private static void raw(DsxMarketDataServiceRaw marketDataService) throws IOException {

    DsxTicker ticker = marketDataService.getDsxTicker(CurrencyPair.BTC_USD);
    System.out.println("BTC/USD Ticker: " + ticker.toString());

    Map<String, DsxTicker> tickers = marketDataService.getDsxTickers();
    System.out.println("All Tickers: " + tickers.toString());

    // Get the latest order book data for BTC/USD
    DsxOrderBook orderBook = marketDataService.getDsxOrderBook(CurrencyPair.BTC_USD);

    System.out.println(
        "Current Order Book size for BTC/USD: "
            + (orderBook.getAsks().length + orderBook.getBids().length));

    System.out.println(orderBook);
  }
}
