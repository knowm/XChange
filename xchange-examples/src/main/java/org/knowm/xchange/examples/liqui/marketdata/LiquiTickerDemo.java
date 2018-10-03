package org.knowm.xchange.examples.liqui.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.examples.liqui.LiquiExampleUtil;
import org.knowm.xchange.liqui.dto.marketdata.LiquiTicker;
import org.knowm.xchange.liqui.service.LiquiMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class LiquiTickerDemo {

  public static final void main(final String[] args) throws IOException {
    final Exchange exchange = LiquiExampleUtil.createTestExchange();

    generic(exchange);
    raw(exchange);
  }

  private static void generic(final Exchange liquiExchange) throws IOException {

    final MarketDataService marketDataService = liquiExchange.getMarketDataService();

    final Ticker ticker = marketDataService.getTicker(CurrencyPair.LTC_BTC);

    System.out.println("Ticker: " + ticker.toString());
    System.out.println("Currency: " + Currency.LTC);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());
  }

  private static void raw(final Exchange liquiExchange) throws IOException {

    final LiquiMarketDataServiceRaw liquiMarketDataServiceRaw =
        (LiquiMarketDataServiceRaw) liquiExchange.getMarketDataService();

    final LiquiTicker ticker = liquiMarketDataServiceRaw.getTicker(CurrencyPair.LTC_BTC);

    System.out.println("Ticker: " + ticker.toString());
    System.out.println("Currency: " + Currency.LTC);
    System.out.println("Last: " + ticker.getLast());
    System.out.println("Volume: " + ticker.getVol().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());
  }
}
