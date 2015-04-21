package com.xeiam.xchange.examples.quoine.marketdata;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.quoine.QuoineExchange;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineProduct;
import com.xeiam.xchange.quoine.service.polling.QuoineMarketDataServiceRaw;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

/**
 * Demonstrate requesting Ticker at Quoine. You can access both the raw data from Quoine or the XChange generic DTO data format.
 */
public class QuoineProductsDemo {

  public static void main(String[] args) throws IOException {

    // Use the factory to get Quoine exchange API using default settings
    Exchange quoine = ExchangeFactory.INSTANCE.createExchange(QuoineExchange.class.getName());

    // Interested in the public polling market data feed (no authentication)
    PollingMarketDataService marketDataService = quoine.getPollingMarketDataService();

    raw((QuoineMarketDataServiceRaw) marketDataService);
  }

  private static void raw(QuoineMarketDataServiceRaw marketDataService) throws IOException {

    QuoineProduct[] quoineProducts = marketDataService.getQuoineProducts();
    for (QuoineProduct quoineProduct : quoineProducts) {
      System.out.println(quoineProduct.toString());
    }

  }

}
