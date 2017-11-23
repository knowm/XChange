package org.knowm.xchange.examples.btcchina.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author timmolter Demonstrate requesting Trades at BTC China given a since as a timestamp
 */
public class BTCChinaTradesDemo2 {

  // Use the factory to get the VirtEx exchange API using default settings
  static Exchange btcchina = ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName());

  // Interested in the public market data feed (no authentication)
  static MarketDataService marketDataService = btcchina.getMarketDataService();

  public static void main(String[] args) throws IOException {

    generic();
  }

  public static void generic() throws IOException {

    // Get the latest trade data for BTC/CNY
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_CNY);

    System.out.println(trades);
    System.out.println("NumTrades=" + trades.getTrades().size());

    trades = marketDataService.getTrades(CurrencyPair.BTC_CNY, trades.getTrades().get(trades.getTrades().size() - 3).getTimestamp().getTime() / 1000,
        20, "time");

    System.out.println(trades);
    System.out.println("NumTrades=" + trades.getTrades().size());
  }
}
