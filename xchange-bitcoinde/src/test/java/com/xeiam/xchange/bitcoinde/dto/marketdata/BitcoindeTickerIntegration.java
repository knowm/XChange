package com.xeiam.xchange.bitcoinde.dto.marketdata;

import java.io.IOException;

import org.junit.Test;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcoinde.BitcoindeExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.service.polling.marketdata.PollingMarketDataService;

public class BitcoindeTickerIntegration {

  @Test
  public void bitcoindeTickerTest() throws IOException {

    /*
     * Get the API key from an environmental variable,
     * on *nix run $ export BITCOINDE_API_KEY=myapikey123
     * to set this variable.
     */
    final String API_KEY = System.getenv("BITCOINDE_API_KEY");
    if (API_KEY == null || API_KEY == "") { // if the environmental variable isn't set
	    System.err.println("Error: please set the environmental variable BITCOINDE_API_KEY equal to your API key before running this integration test. Try $ export BITCOINDE_API_KEY=myapikey123");
	    System.exit(1);
    }

    /* configure the exchange to use our api key */
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(BitcoindeExchange.class.getName());
    exchangeSpecification.setApiKey(API_KEY);

    /* create the exchange object */
    Exchange bitcoindeExchange = ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);

    /* create a data service from the exchange */
    PollingMarketDataService marketDataService = bitcoindeExchange.getPollingMarketDataService();

    /* display our ticker data */
    Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_EUR);
    System.out.println(ticker.toString());
  }

}
