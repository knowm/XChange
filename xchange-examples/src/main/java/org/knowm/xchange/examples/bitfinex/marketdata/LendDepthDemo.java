package org.knowm.xchange.examples.bitfinex.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.bitfinex.v1.dto.marketdata.BitfinexLendDepth;
import org.knowm.xchange.bitfinex.v1.service.BitfinexMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class LendDepthDemo {

  public static void main(String[] args) throws Exception {

    // Use the factory to get BFX exchange API using default settings
    Exchange bfx = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = bfx.getMarketDataService();

    raw((BitfinexMarketDataServiceRaw) marketDataService);
  }

  private static void raw(BitfinexMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for USD swaps
    BitfinexLendDepth bitfinexDepth = marketDataService.getBitfinexLendBook("USD", 50, 50);

    System.out.println(
        "Current Order Book size for USD: "
            + (bitfinexDepth.getAsks().length + bitfinexDepth.getBids().length));

    System.out.println("First Ask: " + bitfinexDepth.getAsks()[0].toString());

    System.out.println("First Bid: " + bitfinexDepth.getBids()[0].toString());

    System.out.println(bitfinexDepth.toString());
  }
}
