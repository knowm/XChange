package com.xeiam.xchange.examples.btctrade.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.btctrade.BTCTradeExchange;
import com.xeiam.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import com.xeiam.xchange.btctrade.service.polling.BTCTradeMarketDataServiceRaw;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

/**
 * Demonstrate requesting ticker at BTCTrade.
 */
public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTC-E exchange API using default settings.
    Exchange btcTrade = ExchangeFactory.INSTANCE.createExchange(BTCTradeExchange.class.getName());
    generic(btcTrade);
    raw(btcTrade);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public polling market data feed (no authentication).
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to CNY.
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_CNY);
    System.out.println(ticker);
  }

  private static void raw(Exchange exchange) {

    // Interested in the public polling market data feed (no authentication).
    BTCTradeMarketDataServiceRaw marketDataService = (BTCTradeMarketDataServiceRaw) exchange.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to CNY.
    BTCTradeTicker ticker = marketDataService.getBTCTradeTicker();
    System.out.println("High: " + ticker.getHigh());
    System.out.println("Low:  " + ticker.getLow());
    System.out.println("Buy:  " + ticker.getBuy());
    System.out.println("Sell: " + ticker.getSell());
    System.out.println("Last: " + ticker.getLast());
    System.out.println("Vol:  " + ticker.getVol());
  }

}
