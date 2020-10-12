package org.knowm.xchange.test.coinsuper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinsuper.CoinsuperExchange;
import org.knowm.xchange.coinsuper.dto.CoinsuperResponse;
import org.knowm.xchange.coinsuper.dto.marketdata.CoinsuperPair;
import org.knowm.xchange.coinsuper.service.CoinsuperMarketDataServiceRaw;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class MarketDataServiceIntegrationPairs {
  public static void main(String[] args) {
    try {
      getSymbolListRaw();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getSymbolListRaw() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    MarketDataService marketDataService = coinsuper.getMarketDataService();
    try {
      raw((CoinsuperMarketDataServiceRaw) marketDataService);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void raw(CoinsuperMarketDataServiceRaw marketDataService) throws IOException {
    Map<String, String> parameters = new HashMap<String, String>();

    CoinsuperResponse<List<CoinsuperPair>> CoinsuperPairs =
        marketDataService.getSymbolList(parameters);

    System.out.println(CoinsuperPairs.getData().getResult());
  }
}
