package org.knowm.xchange.examples.bitfinex.marketdata;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.BitfinexExchange;
import org.knowm.xchange.bitfinex.service.BitfinexMarketDataServiceRaw;
import org.knowm.xchange.bitfinex.v2.dto.marketdata.BitfinexCandle;
import org.knowm.xchange.service.marketdata.MarketDataService;

/** @author cyrus13 */
public class CandlesDemo {

  public static void main(String[] args) throws Exception {

    // Use the factory to get Bitfinex exchange API using default settings
    Exchange bitfinex = ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class);

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = bitfinex.getMarketDataService();

    generic(bitfinex);
    raw((BitfinexMarketDataServiceRaw) marketDataService);
  }

  private static void generic(Exchange bitfinex) {}

  private static void raw(BitfinexMarketDataServiceRaw marketDataService) throws IOException {
    List<BitfinexCandle> candleList =
        marketDataService.getFundingHistoricCandles("15m", "fEUR", 2, 10);
    System.out.println(candleList);
  }
}
