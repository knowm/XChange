package org.knowm.xchange.test.exx;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.exx.EXXExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.marketdata.params.Params;

/**
 * kevinobamatheus@gmail.com
 *
 * @author kevingates
 */
public class MarketDataServiceIntegration {
  public static void main(String[] args) {
    try {
      String category = "getOrderbook";
      switch (category) {
        case "getTickers":
          getTickers();
          break;
        case "getTrades":
          getTrades();
          break;
        case "getTicker":
          getTicker();
          break;
        case "getOrderbook":
          getOrderbook();
          break;
        default:
          getOrderbook();
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getOrderbook() throws IOException {
    Exchange coinsuper = getExchange();

    MarketDataService marketDataService = coinsuper.getMarketDataService();

    try {
      OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC);

      System.out.println(
          "Current Order Book size for ETH_USD: "
              + (orderBook.getAsks().size() + orderBook.getBids().size()));

      System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
      System.out.println(
          "Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

      System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
      System.out.println(
          "Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

      System.out.println(orderBook);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getTicker() throws IOException {
    Exchange exchange = getExchange();
    MarketDataService marketDataService = exchange.getMarketDataService();

    try {
      Ticker coinsuperTicker = marketDataService.getTicker(CurrencyPair.ETH_BTC);
      System.out.println("======get ticker======");

      System.out.println(coinsuperTicker);
      System.out.println("getVolume=" + coinsuperTicker.getVolume());
      System.out.println("getTimestamp=" + coinsuperTicker.getTimestamp());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getTickers() throws IOException {
    Exchange exchange = getExchange();

    MarketDataService marketDataService = exchange.getMarketDataService();
    try {
      Params params = null;
      List<Ticker> tickers = marketDataService.getTickers(params);
      System.out.println("======get tickers======");
      System.out.println(tickers);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getTrades() throws IOException {
    Exchange exchange = getExchange();
    MarketDataService marketDataService = exchange.getMarketDataService();
    try {
      Trades trades = marketDataService.getTrades(CurrencyPair.ETH_BTC);
      System.out.println("======get Trades======");
      System.out.println(trades.getTrades());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Exchange getExchange() throws IOException {
    Exchange exx = ExchangeFactory.INSTANCE.createExchange(EXXExchange.class);

    return exx;
  }
}
