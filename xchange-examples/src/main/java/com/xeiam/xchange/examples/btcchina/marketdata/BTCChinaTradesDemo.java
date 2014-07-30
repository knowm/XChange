package com.xeiam.xchange.examples.btcchina.marketdata;

import java.io.IOException;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.btcchina.BTCChinaExchange;
import com.xeiam.xchange.btcchina.dto.marketdata.BTCChinaTrade;
import com.xeiam.xchange.btcchina.service.polling.BTCChinaMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * @author ObsessiveOrange
 *         Demonstrate requesting Trades at BTC China
 */
public class BTCChinaTradesDemo {

  // Use the factory to get the VirtEx exchange API using default settings
  static Exchange btcchina = ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName());

  // Interested in the public polling market data feed (no authentication)
  static PollingMarketDataService marketDataService = btcchina.getPollingMarketDataService();

  public static void main(String[] args) throws IOException {

    generic();
    raw();
  }

  public static void generic() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

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

  public static void raw() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    // Get the latest trade data for BTC/CNY
    List<BTCChinaTrade> trades = ((BTCChinaMarketDataServiceRaw) marketDataService).getBTCChinaTrades("ltcbtc");

    for (BTCChinaTrade trade : trades) {
      System.out.println(trade.toString());
    }
    System.out.println("NumTrades=" + trades.size());

    // Get the offset trade data for BTC/CNY
    trades = ((BTCChinaMarketDataServiceRaw) marketDataService).getBTCChinaTrades("ltcbtc", 122235L);

    for (BTCChinaTrade trade : trades) {
      System.out.println(trade.toString());
    }
    System.out.println("NumTrades=" + trades.size());

  }
}
