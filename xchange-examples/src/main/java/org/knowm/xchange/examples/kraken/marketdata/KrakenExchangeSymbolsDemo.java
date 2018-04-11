package org.knowm.xchange.examples.kraken.marketdata;

import java.io.IOException;
import java.util.Map.Entry;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssetPair;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssetPairs;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class KrakenExchangeSymbolsDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange =
        ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

    generic(krakenExchange);
    raw(krakenExchange);
  }

  private static void generic(Exchange krakenExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    MarketDataService krakenMarketDataService = krakenExchange.getMarketDataService();
  }

  private static void raw(Exchange krakenExchange) throws IOException {

    // Interested in the public market data feed (no authentication)
    KrakenMarketDataServiceRaw krakenMarketDataService =
        (KrakenMarketDataServiceRaw) krakenExchange.getMarketDataService();

    KrakenAssetPairs krakenAssetPairs = krakenMarketDataService.getKrakenAssetPairs();
    for (Entry<String, KrakenAssetPair> assetPairEntry :
        krakenAssetPairs.getAssetPairMap().entrySet()) {
      System.out.println(assetPairEntry.getKey() + ": " + assetPairEntry.getValue());
    }
  }
}
