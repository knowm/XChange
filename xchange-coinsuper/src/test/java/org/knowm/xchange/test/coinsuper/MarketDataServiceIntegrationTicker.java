package org.knowm.xchange.test.coinsuper;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinsuper.CoinsuperExchange;
import org.knowm.xchange.coinsuper.dto.CoinsuperResponse;
import org.knowm.xchange.coinsuper.dto.marketdata.CoinsuperTicker;
import org.knowm.xchange.coinsuper.service.CoinsuperMarketDataServiceRaw;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class MarketDataServiceIntegrationTicker {
  public static void main(String[] args) {
    try {
      // getOrderbookRaw();
      // getTickerRaw();
      // getTicker
      getTicker();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getTicker() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    MarketDataService marketDataService = coinsuper.getMarketDataService();

    try {
      Ticker coinsuperTicker = marketDataService.getTicker(CurrencyPair.ETC_BTC, 1);
      System.out.println("======get tickers======");

      System.out.println(coinsuperTicker);
      System.out.println("getVolume=" + coinsuperTicker.getVolume());
      System.out.println("getTimestamp=" + coinsuperTicker.getTimestamp());

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getTickerRaw() throws IOException {
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

    CoinsuperResponse<List<CoinsuperTicker>> coinsuperResponse =
        marketDataService.getCoinsuperTicker(CurrencyPair.ETC_BTC);

    System.out.println("======get tickers======");

    System.out.println(coinsuperResponse.getData().getResult().get(0));

    coinsuperResponse
        .getData()
        .getResult()
        .forEach(
            coinsuperTicker -> {
              System.out.println("result.getVolume() = " + coinsuperTicker.getVolume());
            });
  }
}
