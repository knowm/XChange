package com.xeiam.xchange.coinbaseex.service.marketdata;

import com.xeiam.xchange.coinbaseex.CoinbaseExExchange;
import com.xeiam.xchange.currency.CurrencyPair;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Radu on 6/17/2015.
 */
public class CoinbaseExAvailableProductsIntegration {

  @Test
  public void testCoinbaseCurrencies() throws IOException {
    CoinbaseExExchange exchange = new CoinbaseExExchange();
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());

    assertThat(exchange.getPollingMarketDataService().getExchangeSymbols()).containsAll(
        Arrays.asList(new CurrencyPair("BTC/USD"), new CurrencyPair("BTC/GBP"), new CurrencyPair("BTC/EUR"))
    );
  }
}
