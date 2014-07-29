package com.xeiam.xchange.examples.bitfinex.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitfinex.v1.BitfinexExchange;
import com.xeiam.xchange.bitfinex.v1.dto.marketdata.BitfinexLendDepth;
import com.xeiam.xchange.bitfinex.v1.service.polling.BitfinexMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

public class LendDepthDemo {
  public static void main(String[] args) throws Exception {

    // Use the factory to get BTC-E exchange API using default settings
    Exchange bfx = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bfx.getPollingMarketDataService();
    
    raw((BitfinexMarketDataServiceRaw) marketDataService);
  }
  
  private static void raw(BitfinexMarketDataServiceRaw marketDataService) throws IOException {

    // Get the latest order book data for USD swaps
    BitfinexLendDepth bitfinexDepth = marketDataService.getBitfinexLendBook("USD", 50, 50);

    System.out.println("Current Order Book size for BTC / USD: " + (bitfinexDepth.getAsks().length + bitfinexDepth.getBids().length));

    System.out.println("First Ask: " + bitfinexDepth.getAsks()[0].toString());

    System.out.println("First Bid: " + bitfinexDepth.getBids()[0].toString());

    System.out.println(bitfinexDepth.toString());
  }
}
