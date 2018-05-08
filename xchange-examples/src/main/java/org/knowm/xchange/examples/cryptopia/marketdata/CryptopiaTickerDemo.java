package org.knowm.xchange.examples.cryptopia.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cryptopia.CryptopiaExchange;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTicker;
import org.knowm.xchange.cryptopia.service.CryptopiaMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demonstrate requesting Ticker at Cryptopia. You can access both the raw data from Cryptopia or
 * the XChange generic DTO data format.
 */
public class CryptopiaTickerDemo {

  public static void main(String[] args) throws IOException {
    // Use the factory to get Cryptopia exchange API using default settings
    Exchange cryptopia = ExchangeFactory.INSTANCE.createExchange(CryptopiaExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = cryptopia.getMarketDataService();

    generic(marketDataService);
    raw((CryptopiaMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {
    Ticker ticker = marketDataService.getTicker(CurrencyPair.ETH_BTC);

    System.out.println(ticker.toString());
  }

  private static void raw(CryptopiaMarketDataServiceRaw marketDataService) throws IOException {
    CryptopiaTicker cryptopiaTicker = marketDataService.getCryptopiaTicker(CurrencyPair.ETH_BTC);

    System.out.println(cryptopiaTicker.toString());
  }
}
