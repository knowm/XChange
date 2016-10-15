package org.knowm.xchange.examples.gdax;

import java.io.IOException;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProductTicker;
import org.knowm.xchange.gdax.service.GDAXMarketDataServiceRaw;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

public class GDAXTickerDemo {

  public static void main(String[] args) throws IOException {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(GDAXExchange.class.getName());
    PollingMarketDataService marketDataService = exchange.getPollingMarketDataService();

    generic(marketDataService);
    raw((GDAXMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());
  }

  private static void raw(GDAXMarketDataServiceRaw marketDataService) throws IOException {

    GDAXProductTicker bitstampTicker = marketDataService.getCoinbaseExProductTicker(CurrencyPair.BTC_USD);

    System.out.println(bitstampTicker.toString());
  }

}
