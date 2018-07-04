package org.knowm.xchange.examples.coingi.marketdata;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.examples.coingi.CoingiDemoUtils;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class CoingiPublicTradesDemo {
  public static void main(String[] args) throws IOException {
    Exchange coingi = CoingiDemoUtils.createExchange();

    MarketDataService marketDataService = coingi.getMarketDataService();
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_EUR);

    List<Trade> list = trades.getTrades();
    list.forEach(System.out::println);
    System.out.printf("Received the latest %d public trades.\n", list.size());
  }
}
