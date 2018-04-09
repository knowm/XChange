package org.knowm.xchange.examples.quoine.marketdata;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.quoine.QuoineExchange;
import org.knowm.xchange.quoine.dto.marketdata.QuoineProduct;
import org.knowm.xchange.quoine.service.QuoineMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

/**
 * Demonstrate requesting Ticker at Quoine. You can access both the raw data from Quoine or the
 * XChange generic DTO data format.
 */
public class TickerDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Quoine exchange API using default settings
    Exchange quoine = ExchangeFactory.INSTANCE.createExchange(QuoineExchange.class.getName());

    // Interested in the public market data feed (no authentication)
    MarketDataService marketDataService = quoine.getMarketDataService();

    generic(marketDataService);
    raw((QuoineMarketDataServiceRaw) marketDataService);
  }

  private static void generic(MarketDataService marketDataService) throws IOException {

    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);

    System.out.println(ticker.toString());
  }

  private static void raw(QuoineMarketDataServiceRaw marketDataService) throws IOException {

    QuoineProduct quoineProduct = marketDataService.getQuoineProduct("BTCUSD");

    System.out.println(quoineProduct.toString());
  }
}
