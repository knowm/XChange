package com.xeiam.xchange.examples.coinsetter;

import static com.xeiam.xchange.coinsetter.CoinsetterExchange.SESSION_IP_ADDRESS_KEY;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinsetter.CoinsetterExchange;

public class CoinsetterExamplesUtils {

  public static Exchange getExchange() {

    return getExchange(null, null, null);
  }

  public static Exchange getExchange(String username, String password, String ipAddress) {

    Exchange coinsetter = ExchangeFactory.INSTANCE.createExchange(CoinsetterExchange.class.getName());
    ExchangeSpecification exchangeSpecification = coinsetter.getExchangeSpecification();
    exchangeSpecification.setSslUri("https://staging-api.coinsetter.com/v1");
    exchangeSpecification.setHost("staging-api.coinsetter.com");

    if (username != null) {
      exchangeSpecification.setUserName(username);
    }
    if (password != null) {
      exchangeSpecification.setPassword(password);
    }
    if (ipAddress != null) {
      exchangeSpecification.setExchangeSpecificParametersItem(SESSION_IP_ADDRESS_KEY, ipAddress);
    }

    return ExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
  }

}
