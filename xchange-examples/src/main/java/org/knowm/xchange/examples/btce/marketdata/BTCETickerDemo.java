package org.knowm.xchange.examples.btce.marketdata;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btce.v3.BTCEExchange;
import org.knowm.xchange.btce.v3.dto.marketdata.BTCETickerWrapper;
import org.knowm.xchange.btce.v3.service.BTCEMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.utils.CertHelper;

/**
 * Demonstrate requesting Order Book at BTC-E
 */
public class BTCETickerDemo {

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    // Use the factory to get BTC-E exchange API using default settings
    Exchange btce = ExchangeFactory.INSTANCE.createExchange(BTCEExchange.class.getName());
    generic(btce);
    raw(btce);
  }

  private static void generic(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = exchange.getMarketDataService();

    // Get the latest ticker data showing BTC to CAD
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());

    System.out.println(ticker.toString());
  }

  private static void raw(Exchange exchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    BTCEMarketDataServiceRaw marketDataService = (BTCEMarketDataServiceRaw) exchange.getMarketDataService();

    // Get the latest ticker data showing BTC to USD
    BTCETickerWrapper ticker = marketDataService.getBTCETicker("btc_usd");
    System.out.println(ticker.toString());
  }

}
