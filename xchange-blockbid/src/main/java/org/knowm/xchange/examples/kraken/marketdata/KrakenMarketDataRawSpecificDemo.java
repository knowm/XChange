package org.knowm.xchange.examples.kraken.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.kraken.dto.marketdata.KrakenAssets;
import org.knowm.xchange.kraken.dto.marketdata.KrakenServerTime;
import org.knowm.xchange.kraken.dto.marketdata.KrakenSpreads;
import org.knowm.xchange.kraken.service.KrakenMarketDataServiceRaw;

public class KrakenMarketDataRawSpecificDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange =
        ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    KrakenMarketDataServiceRaw krakenMarketDataService =
        (KrakenMarketDataServiceRaw) krakenExchange.getMarketDataService();

    KrakenServerTime serverTime = krakenMarketDataService.getServerTime();
    System.out.println(serverTime);

    KrakenAssets assets = krakenMarketDataService.getKrakenAssets();
    System.out.println(assets);

    KrakenSpreads spreads = krakenMarketDataService.getKrakenSpreads(Currency.BTC, Currency.USD);
    System.out.println(spreads);
  }
}
