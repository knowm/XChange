package com.xeiam.xchange.examples.cointrader;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cointrader.CointraderExchange;
import com.xeiam.xchange.currency.CurrencyPair;

public class CointraderExampleUtils {

  private CointraderExampleUtils() {
  }

  public static Exchange createTestExchange() {
    Exchange cointraderExchange = ExchangeFactory.INSTANCE.createExchange(CointraderExchange.class.getName());
    ExchangeSpecification spec = cointraderExchange.getExchangeSpecification();
    spec.getExchangeSpecificParameters().put(CointraderExchange.CURRENCY_PAIR, CurrencyPair.BTC_USD);

    // Set your API credentials here.
    spec.setApiKey("your public api key");
    spec.setSecretKey("your private api key");

    cointraderExchange.applySpecification(spec);
    return cointraderExchange;
  }
}
