package org.knowm.xchange.examples.bitfinex.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTicker;
import org.knowm.xchange.bitfinex.v1.service.BitfinexMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** Demonstrate requesting Order Book at Bitfinex */
public class TickerDemo {

  public static void main(String[] args) throws Exception {

    // Use the factory to get Bitfinex exchange API using default settings
    Exchange bitfinex = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = bitfinex.getMarketDataService();

    generic(marketDataService);
    raw((BitfinexMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    // Get the latest ticker data showing BTC to USD
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());
  }

  private static void raw(BitfinexMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest ticker data showing BTC to USD
    BitfinexTicker ticker = marketDataService.getBitfinexTicker("btcusd");

    System.out.println(ticker.toString());
  }
}
