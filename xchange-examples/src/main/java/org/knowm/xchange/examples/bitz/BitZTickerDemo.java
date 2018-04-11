package org.knowm.xchange.examples.bitz;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.xchange.bitz.BitZExchange;
import org.xchange.bitz.BitZUtils;
import org.xchange.bitz.service.BitZMarketDataServiceRaw;

/**
 * Demonstrate requesting Ticker at Bit-Z. You can access both the raw data from Bit-Z or the
 * XChange generic DTO data format.
 */
public class BitZTickerDemo {

  public static void main(String[] args) throws IOException {

    // Create Default BitZ Instance
    Exchange bitZ = ExchangeFactory.INSTANCE.createExchange(BitZExchange.class.getName());

    // Get The Public Market Data Service
    MarketDataService marketDataService = bitZ.getMarketDataService();
    BitZMarketDataServiceRaw rawMarketDataService = (BitZMarketDataServiceRaw) marketDataService;

    // Currency Pair To Get Ticker Of
    CurrencyPair pair = CurrencyPair.LTC_BTC;

    // Print The Generic and Raw Ticker
    System.out.println(marketDataService.getTicker(pair));
    System.out.println(rawMarketDataService.getBitZTicker(BitZUtils.toPairString(pair)));
  }
}
