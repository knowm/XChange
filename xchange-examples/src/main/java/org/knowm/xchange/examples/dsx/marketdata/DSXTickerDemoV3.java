package org.knowm.xchange.examples.dsx.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dsx.DSXExchangeV3;
import org.knowm.xchange.dsx.dto.marketdata.DSXTickerWrapper;
import org.knowm.xchange.dsx.service.DSXMarketDataServiceV3;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.utils.CertHelper;

/** @author Mikhail Wall */
public class DSXTickerDemoV3 {

  public static void main(String[] args) throws Exception {

    CertHelper.trustAllCerts();

    Exchange dsx = ExchangeFactory.INSTANCE.createExchange(DSXExchangeV3.class);
    generic(dsx);
    raw(dsx);
  }

  private static void generic(Exchange exchange) throws IOException {

    MarketDataService marketDataService = exchange.getMarketDataService();

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD, "LIVE");

    System.out.println("Last: " + ticker.getLast().toString());
    System.out.println("Volume: " + ticker.getVolume().toString());
    System.out.println("High: " + ticker.getHigh().toString());
    System.out.println("Low: " + ticker.getLow().toString());

    System.out.println(ticker.toString());
  }

  private static void raw(Exchange exchange) throws IOException {

    DSXMarketDataServiceV3 marketDataService =
        (DSXMarketDataServiceV3) exchange.getMarketDataService();

    DSXTickerWrapper ticker = marketDataService.getDSXTicker("btcusd", "LIVE");
    System.out.println(ticker.toString());
  }
}
