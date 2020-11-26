package org.knowm.xchange.test.coinsuper;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinsuper.CoinsuperExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class MarketDataServiceIntegrationOrderbook {
  public static void main(String[] args) {
    try {
      // getOrderbookRaw();
      getOrderbook();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void getOrderbook() throws IOException {
    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";

    Exchange coinsuper = ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);

    ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
    exchangeSpecification.setApiKey(apiKey);
    exchangeSpecification.setSecretKey(secretKey);
    coinsuper.applySpecification(exchangeSpecification);

    MarketDataService marketDataService = coinsuper.getMarketDataService();

    try {
      // Get the latest order book data for BTC/USD
      // OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.XRP_BTC, -1);
      // OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC, -1);
      OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.ETH_BTC);

      System.out.println(
          "Current Order Book size for XRP_BTC: "
              + (orderBook.getAsks().size() + orderBook.getBids().size()));

      System.out.println("First Ask: " + orderBook.getAsks().get(0).toString());
      System.out.println(
          "Last Ask: " + orderBook.getAsks().get(orderBook.getAsks().size() - 1).toString());

      System.out.println("First Bid: " + orderBook.getBids().get(0).toString());
      System.out.println(
          "Last Bid: " + orderBook.getBids().get(orderBook.getBids().size() - 1).toString());

      System.out.println(orderBook);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //	private static void getOrderbookRaw() throws IOException {
  //	    String apiKey = "00af0b38-11fb-4aab-bf19-45edd44a4adc";
  //	    String secretKey = "fa3f0510-155f-4567-a3b3-3f386080efa3";
  //
  //		Exchange coinsuper =
  // ExchangeFactory.INSTANCE.createExchange(CoinsuperExchange.class);
  //
  //        ExchangeSpecification exchangeSpecification = coinsuper.getExchangeSpecification();
  //        exchangeSpecification.setApiKey(apiKey);
  //        exchangeSpecification.setSecretKey(secretKey);
  //        coinsuper.applySpecification(exchangeSpecification);
  //
  //		MarketDataService marketDataService = coinsuper.getMarketDataService();
  //		try {
  //			raw((CoinsuperMarketDataServiceRaw) marketDataService);
  //		} catch (IOException e) {
  //			e.printStackTrace();
  //		}
  //	}

  //	private static void raw(CoinsuperMarketDataServiceRaw marketDataService) throws IOException {
  //
  //		CoinsuperResponse<CoinsuperOrderbook> data =
  // marketDataService.getCoinsuperOrderBooks(CurrencyPair.ETC_BTC,3);
  //
  //	    //System.out.println(data.getData().getResult());
  //	    //System.out.println(data.getData().getResult().getAsks());
  //	}

}
