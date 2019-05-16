package org.knowm.xchange.examples.btctrade.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btctrade.BTCTradeExchange;
import org.knowm.xchange.btctrade.dto.marketdata.BTCTradeTicker;
import org.knowm.xchange.btctrade.service.BTCTradeMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting ticker at BTCTrade. */
public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BTC-E exchange API using default settings.
    Exchange btcTrade = ExchangeFactory.INSTANCE.createExchange(BTCTradeExchange.class.getName());
    generic(btcTrade);
    raw(btcTrade);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication).
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Get the latest ticker data showing BTC to CNY.
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_CNY);
    System.out.println(ticker);
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication).
    BTCTradeMarketDataServiceRaw marketDataService =
        (BTCTradeMarketDataServiceRaw) exchange.getMarketDataService();

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
