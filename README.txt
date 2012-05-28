XChange
=======
XChange is a library providing a simple and consistent API for interacting with a diverse set of financial security exchanges, including support for Bitcoin. The primary  development platform is Java, but developers are encouraged to port this code to their own language. Ideally, these ports can be fed back into the overall XChange project so that everyone can benefit from improvements in one central location.

In short, we invite pull requests.

More info
=========
Project Site: http://xeiam.com/xchange.jsp
Example Code: http://xeiam.com/xchange_examplecode.jsp
Change Log: http://xeiam.com/xchange_changelog.jsp
Java Docs: http://xeiam.com/xchange/javadoc/index.html

Wiki
====
Home: https://github.com/timmolter/XChange/wiki
Design Notes: https://github.com/timmolter/XChange/wiki/Design-Notes
Milestones: https://github.com/timmolter/XChange/wiki/Milestones
Exchange Support: https://github.com/timmolter/XChange/wiki/Exchange-support
Maven Integration: https://github.com/timmolter/XChange/wiki/Maven-Integration

Build instructions
========================
Maven Build: https://github.com/timmolter/XChange/wiki/Maven-Integration

Getting Started
===============

Maven
-----
Maven Integration: https://github.com/timmolter/XChange/wiki/Maven-Integration

Non-Maven
---------
Download Jars: http://xeiam.com/xchange.jsp
Jar Dependencies:

Example Code (More at: http://xeiam.com/xchange_examplecode.jsp)
===========
package com.xeiam.xchange.examples.mtgox.v1.polling;

import com.xeiam.xchange.Currencies;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;

/**
 * Test requesting polling Ticker at MtGox
 */
public class TickerDemo {

  private static PollingMarketDataService marketDataService;

  public static void main(String[] args) {

    // Use the factory to get the version 1 MtGox exchange API using default settings
    Exchange mtGox = ExchangeFactory.INSTANCE.createExchange("com.xeiam.xchange.mtgox.v1.MtGoxExchange");

    // Interested in the public polling market data feed (no authentication)
    marketDataService = mtGox.getPollingMarketDataService();

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker(Currencies.BTC, Currencies.USD);
    double value = ticker.getLast().getAmount().doubleValue();
    String currency = ticker.getLast().getCurrencyUnit().toString();
    System.out.println("Last: " + currency + "-" + value);

    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Bid: " + ticker.getBid().toString());
    System.out.println("Ask: " + ticker.getAsk().toString());

    // Get the latest ticker data showing BTC to EUR
    ticker = marketDataService.getTicker(Currencies.BTC, Currencies.EUR);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Bid: " + ticker.getBid().toString());
    System.out.println("Ask: " + ticker.getAsk().toString());

    // Get the latest ticker data showing BTC to GBP
    ticker = marketDataService.getTicker(Currencies.BTC, Currencies.GBP);
    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Bid: " + ticker.getBid().toString());
    System.out.println("Ask: " + ticker.getAsk().toString());

  }

}

