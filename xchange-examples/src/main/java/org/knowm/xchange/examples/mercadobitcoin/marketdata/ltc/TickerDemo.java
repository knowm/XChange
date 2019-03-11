package org.knowm.xchange.examples.mercadobitcoin.marketdata.ltc;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.mercadobitcoin.MercadoBitcoinExchange;
import org.knowm.xchange.mercadobitcoin.dto.marketdata.MercadoBitcoinTicker;
import org.knowm.xchange.mercadobitcoin.service.MercadoBitcoinMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demonstrate requesting Ticker at Mercado Bitcoin. You can access both the raw data from Mercado
 * Bitcoin or the XChange generic DTO data format.
 *
 * @author Copied from Bitstamp and adapted by Felipe Micaroni Lalli
 */
public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Mercado Bitcoin exchange API using default settings
    Exchange mercadoBitcoin =
        ExchangeFactory.INSTANCE.createExchange(MercadoBitcoinExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = mercadoBitcoin.getMarketDataService();

    generic(marketDataService);
    raw((MercadoBitcoinMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(new CurrencyPair(Currency.LTC, Currency.BRL));

    System.out.println(ticker.toString());
  }

  private static void raw(MercadoBitcoinMarketDataServiceRaw marketDataService) throws IOException {

    MercadoBitcoinTicker mercadoBitcoinTicker =
        marketDataService.getMercadoBitcoinTicker(new CurrencyPair(Currency.LTC, Currency.BRL));

    System.out.println(mercadoBitcoinTicker.toString());
  }
}
