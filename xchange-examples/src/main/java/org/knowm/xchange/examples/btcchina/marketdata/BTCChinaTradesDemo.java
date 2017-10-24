package org.knowm.xchange.examples.btcchina.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import org.knowm.xchange.btcchina.service.rest.BTCChinaMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author ObsessiveOrange Demonstrate requesting Trades at BTC China
 */
public class BTCChinaTradesDemo {

  // Use the factory to get the VirtEx exchange API using default settings
  static Exchange btcchina = ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName());

  // Interested in the public market data feed (no authentication)
  static MarketDataService marketDataService = btcchina.getMarketDataService();

  public static void main(String[] args) throws IOException {

    generic();
    raw();
  }

  public static void generic() throws IOException {

    // Get the latest trade data for BTC/CNY
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_CNY);

    System.out.println(trades);
    System.out.println("NumTrades=" + trades.getTrades().size());

    // Get the latest trade data for BTC/CNY, limit 10
    trades = marketDataService.getTrades(CurrencyPair.BTC_CNY, null, 10);
    System.out.println(trades);
    System.out.println("NumTrades=" + trades.getTrades().size());

    // Get the offset trade data for BTC/CNY
    trades = marketDataService.getTrades(CurrencyPair.BTC_CNY, 6097616);

    System.out.println(trades);
    System.out.println("NumTrades=" + trades.getTrades().size());

    System.out.println("LastId=" + trades.getlastID());

    // Get the offset trade data for BTC/CNY, limit 10
    trades = marketDataService.getTrades(CurrencyPair.BTC_CNY, 6097616, 10);

    System.out.println(trades);
    System.out.println("NumTrades=" + trades.getTrades().size());

    System.out.println("LastId=" + trades.getlastID());
  }

  public static void raw() throws IOException {

    // Get the latest trade data for BTC/CNY
    BTCChinaTrade[] trades = ((BTCChinaMarketDataServiceRaw) marketDataService).getBTCChinaHistoryData(BTCChinaExchange.DEFAULT_MARKET);

    for (BTCChinaTrade trade : trades) {
      System.out.println(trade.toString());
    }
    System.out.println("NumTrades=" + trades.length);

    // Get the offset trade data for BTC/CNY
    trades = ((BTCChinaMarketDataServiceRaw) marketDataService).getBTCChinaHistoryData(BTCChinaExchange.DEFAULT_MARKET, 122235L);

    for (BTCChinaTrade trade : trades) {
      System.out.println(trade.toString());
    }
    System.out.println("NumTrades=" + trades.length);

  }
}
