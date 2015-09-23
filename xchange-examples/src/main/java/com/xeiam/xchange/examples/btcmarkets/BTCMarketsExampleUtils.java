package com.xeiam.xchange.examples.btcmarkets;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcmarkets.BTCMarketsExchange;
import com.xeiam.xchange.currency.CurrencyPair;

public class BTCMarketsExampleUtils {

  private BTCMarketsExampleUtils() { }

  public static Exchange createTestExchange() {
    Exchange btcMarketsExchange = ExchangeFactory.INSTANCE.createExchange(BTCMarketsExchange.class.getName());
    ExchangeSpecification spec = btcMarketsExchange.getExchangeSpecification();
    spec.getExchangeSpecificParameters().put(BTCMarketsExchange.CURRENCY_PAIR, CurrencyPair.BTC_AUD);

    // Set your actual credentials here for the demos to work.
    spec.setApiKey("<Put your API key here.>");
    spec.setSecretKey("<Put your secret key here.>");

    btcMarketsExchange.applySpecification(spec);
    return btcMarketsExchange;
  }
}
