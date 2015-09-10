package com.xeiam.xchange.coinbaseex.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import com.xeiam.xchange.coinbaseex.CoinbaseExExchange;
import com.xeiam.xchange.currency.CurrencyPair;

/**
 * Created by Radu on 6/17/2015.
 */
public class CoinbaseExAvailableProductsIntegration {

  @Test
  public void testCoinbaseCurrencies() throws IOException {
    CoinbaseExExchange exchange = new CoinbaseExExchange();
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());

    assertThat(exchange.getPollingMarketDataService().getExchangeSymbols())
        .containsAll(Arrays.asList(new CurrencyPair("BTC/USD"), new CurrencyPair("BTC/GBP"), new CurrencyPair("BTC/EUR")));
  }
}
