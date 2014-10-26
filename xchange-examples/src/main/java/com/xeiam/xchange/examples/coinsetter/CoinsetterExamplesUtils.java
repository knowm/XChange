package com.xeiam.xchange.examples.coinsetter;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.CoinsetterExchange;

public class CoinsetterExamplesUtils {

  public static Exchange getExchange() {

    Exchange coinsetter = ExchangeFactory.INSTANCE.createExchange(CoinsetterExchange.class.getName());
    ExchangeSpecification exchangeSpecification = coinsetter.getExchangeSpecification();
    exchangeSpecification.setSslUri("https://staging-api.coinsetter.com/v1");
    exchangeSpecification.setHost("staging-api.coinsetter.com");

    return ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }

}
