package com.xeiam.xchange.examples.bitcointoyou.marketdata.ltc;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouExchange;
import com.xeiam.xchange.bitcointoyou.dto.marketdata.BitcoinToYouTicker;
import com.xeiam.xchange.bitcointoyou.service.polling.BitcoinToYouMarketDataServiceRaw;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Ticker at BitcoinToYou. You can access both the raw data from BitcoinToYou or the XChange generic DTO data format.
 * 
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get BitcoinToYou exchange API using default settings
    Exchange bitcoinToYou = ExchangeFactory.INSTANCE.createExchange(BitcoinToYouExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = bitcoinToYou.getPollingMarketDataService();

    generic(marketDataService);
    raw((BitcoinToYouMarketDataServiceRaw) marketDataService);
  }

  private static void generic(PollingMarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(new CurrencyPair(Currencies.LTC, Currencies.BRL));

    System.out.println(ticker.toString());
  }

  private static void raw(BitcoinToYouMarketDataServiceRaw marketDataService) throws IOException {

    BitcoinToYouTicker bitcoinToYouTicker = marketDataService.getBitcoinToYouTicker(CurrencyPair.BTC_BRL);

    System.out.println(bitcoinToYouTicker.toString());
  }

}
