package org.knowm.xchange.examples.btcchina.marketdata;

import java.io.IOException;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btcchina.BTCChinaExchange;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTicker;
import org.knowm.xchange.btcchina.dto.marketdata.BTCChinaTickerObject;
import org.knowm.xchange.btcchina.service.rest.BTCChinaMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * @author ObsessiveOrange Demonstrate requesting Ticker at BTC China
 */
public class BTCChinaTickerDemo {

  // Use the factory to get the BTC China exchange API using default settings
  static Exchange btcchina = ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName());

  // Interested in the public market data feed (no authentication)
  static MarketDataService marketDataService = btcchina.getMarketDataService();

  public static void main(String[] args) throws IOException {

    generic();
    raw();
  }

  public static void generic() throws IOException {

    // Get the latest ticker data showing BTC to CNY
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_LTC);

    System.out.println("Date: " + ticker.getTimestamp());

    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());

  }

  public static void raw() throws IOException {

    BTCChinaMarketDataServiceRaw marketDataServiceRaw = ((BTCChinaMarketDataServiceRaw) marketDataService);

    // Get the latest ticker data showing BTC to CNY
    BTCChinaTicker ticker = marketDataServiceRaw.getBTCChinaTicker("ltccny");

    System.out.println("Date: " + ticker.getTicker().getDate());

    System.out.println("Last: " + ticker.getTicker().getLast().toString());
    System.out.println("Volume: " + ticker.getTicker().getVol().toString());
    System.out.println("High: " + ticker.getTicker().getHigh().toString());
    System.out.println("Low: " + ticker.getTicker().getLow().toString());

    System.out.println("vwap: " + ticker.getTicker().getVwap());
    System.out.println("prev_close: " + ticker.getTicker().getPrevClose());
    System.out.println("open: " + ticker.getTicker().getOpen());

    Map<String, BTCChinaTickerObject> tickers = marketDataServiceRaw.getBTCChinaTickers();
    System.out.println(tickers);

  }
}
