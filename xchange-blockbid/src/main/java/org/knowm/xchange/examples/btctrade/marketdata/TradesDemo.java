package org.knowm.xchange.examples.btctrade.marketdata;

import java.io.IOException;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btctrade.BTCTradeExchange;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeTrade;
import org.knowm.xchange.btctrade.service.BTCTradeMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting trades at BTCTrade. */
public class TradesDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTCTrade exchange API using the default settings.
    Exchange btcTrade = ExchangeFactory.INSTANCE.createExchange(BTCTradeExchange.class.getName());
    generic(btcTrade);
    raw(btcTrade);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication).
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Get the latest 500 trade data for BTC/CNY.
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_CNY);
    System.out.println(trades);
    System.out.println(
        "Trades count: " + trades.getTrades().size() + ", LastID: " + trades.getlastID());

    // Get 500 trades which tid > 0
    trades = marketDataService.getTrades(CurrencyPair.BTC_CNY, 0);
    System.out.println(trades);
    System.out.println(
        "Trades count: " + trades.getTrades().size() + ", LastID: " + trades.getlastID());
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication).
    BTCTradeMarketDataServiceRaw marketDataService =
        (BTCTradeMarketDataServiceRaw) exchange.getMarketDataService();

    // Get the latest 500 trade data for BTC/CNY.
    BTCTradeTrade[] trades = marketDataService.getBTCTradeTrades();
    System.out.println("Trades count: " + trades.length);
    for (BTCTradeTrade trade : trades) {
      System.out.println(
          trade.getTid()
              + "\t"
              + new Date(trade.getDate() * 1000)
              + "\t"
              + trade.getType()
              + "\t"
              + trade.getPrice()
              + "\t"
              + trade.getAmount());
    }

    // Get 500 trades which tid > 0
    trades = marketDataService.getBTCTradeTrades(0);
    System.out.println("Trades count: " + trades.length);
    for (BTCTradeTrade trade : trades) {
      System.out.println(
          trade.getTid()
              + "\t"
              + new Date(trade.getDate() * 1000)
              + "\t"
              + trade.getType()
              + "\t"
              + trade.getPrice()
              + "\t"
              + trade.getAmount());
    }
  }
}
