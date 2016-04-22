package org.knowm.xchange.examples.bitfinex.marketdata;

import java.io.IOException;
import java.util.Arrays;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexTrade;
import org.knowm.xchange.bitfinex.v1.service.polling.BitfinexMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Order Book at BTC-E
 */
public class TradesDemo {

  public static void main(String[] args) throws Exception {

    // Use the factory to get BTC-E exchange API using default settings
    Exchange bitfinex = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitfinex.getPollingMarketDataService();

    generic(marketDataService);
    raw((BitfinexMarketDataServiceRaw) marketDataService);

  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    // Get the latest trade data for BTC/USD
    Trades trades = marketDataService.getTrades(CurrencyPair.BTC_USD);
    System.out.println("Trades, Size= " + trades.getTrades().size());
    System.out.println(trades.toString());
  }

  private static void raw(BitfinexMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest trade data for BTC/USD
    BitfinexTrade[] trades = marketDataService.getBitfinexTrades("btcusd", System.currentTimeMillis() / 1000 - 120);
    System.out.println("Trades, default. Size= " + trades.length);
    System.out.println(Arrays.toString(trades));
  }
}