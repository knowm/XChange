package org.knowm.xchange.examples.binance.marketdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.marketdata.BinanceTicker24h;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.examples.binance.BinanceDemoUtils;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BinanceMarketDataDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = BinanceDemoUtils.createExchange();

    /* create a data service from the exchange */
    MarketDataService marketDataService = exchange.getMarketDataService();

    generic(exchange, marketDataService);
    raw((BinanceExchange) exchange, (BinanceMarketDataService) marketDataService);
    // rawAll((BinanceExchange) exchange, (BinanceMarketDataService) marketDataService);
  }

  public static void generic(Exchange exchange, MarketDataService marketDataService)
      throws IOException {}

  public static void raw(BinanceExchange exchange, BinanceMarketDataService marketDataService)
      throws IOException {

    List<BinanceTicker24h> tickers = new ArrayList<>();
    for (Instrument cp : exchange.getExchangeMetaData().getInstruments().keySet()) {
      if (cp.getCounter() == Currency.USDT) {
        tickers.add(marketDataService.ticker24hAllProducts(cp));
      }
    }

    tickers.sort((t1, t2) -> t2.getPriceChangePercent().compareTo(t1.getPriceChangePercent()));

    tickers.forEach(
        t ->
            System.out.println(
                t.getCurrencyPair() + " => " + String.format("%+.2f%%", t.getPriceChangePercent())));
    System.out.println("raw out end");
  }

  public static void rawAll(BinanceExchange exchange, BinanceMarketDataService marketDataService)
      throws IOException {

    List<BinanceTicker24h> tickers = new ArrayList<>(marketDataService.ticker24hAllProducts(false));
    tickers.sort((t1, t2) -> t2.getPriceChangePercent().compareTo(t1.getPriceChangePercent()));

    tickers.forEach(
        t ->
            System.out.println(
                t.getCurrencyPair() + " => " + String.format("%+.2f%%", t.getLastPrice())));
  }
}
