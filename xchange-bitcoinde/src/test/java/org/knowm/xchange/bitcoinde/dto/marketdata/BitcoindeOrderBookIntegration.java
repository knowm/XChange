package org.knowm.xchange.bitcoinde.dto.marketdata;

import java.io.IOException;

import org.junit.Assume;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoinde.BitcoindeExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.service.marketdata.MarketDataService;

public class BitcoindeOrderBookIntegration {

  @Test
  public void bitcoindeOrderBookTest() throws IOException {

    /*
     * Get the API key from an environmental variable, on *nix run $ export BITCOINDE_API_KEY=myapikey123 to set this variable.
     */
    final String API_KEY = System.getenv("BITCOINDE_API_KEY");
    Assume.assumeFalse(
        "Error: please set the environmental variable BITCOINDE_API_KEY equal to your API key before running this integration test. Try $ export BITCOINDE_API_KEY=myapikey123",
        API_KEY == null || "".equals(API_KEY));

    /* configure the exchange to use our api key */
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoindeExchange.class.getName());
    exchangeSpecification.setApiKey(API_KEY);

    /* create the exchange object */
    Exchange bitcoindeExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    /* create a data service from the exchange */
    MarketDataService marketDataService = bitcoindeExchange.getMarketDataService();

    /* display the first ask of our OrderBook */
    OrderBook orderBook = marketDataService.getOrderBook(CurrencyPair.BTC_EUR);
    System.out.println(orderBook.getAsks().get(0));
  }

}
