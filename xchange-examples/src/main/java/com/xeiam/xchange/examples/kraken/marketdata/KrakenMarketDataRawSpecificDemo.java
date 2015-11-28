package com.xeiam.xchange.examples.kraken.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.kraken.KrakenExchange;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssets;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenServerTime;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenSpreads;
import com.xeiam.xchange.kraken.service.polling.KrakenMarketDataServiceRaw;

public class KrakenMarketDataRawSpecificDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Kraken exchange API using default settings
    Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    KrakenMarketDataServiceRaw krakenMarketDataService = (KrakenMarketDataServiceRaw) krakenExchange.getPollingMarketDataService();

    KrakenServerTime serverTime = krakenMarketDataService.getServerTime();
    System.out.println(serverTime);

    KrakenAssets assets = krakenMarketDataService.getKrakenAssets();
    System.out.println(assets);

    KrakenSpreads spreads = krakenMarketDataService.getKrakenSpreads(Currency.BTC, Currency.USD);
    System.out.println(spreads);
  }
}
