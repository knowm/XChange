package org.knowm.xchange.examples.cointrader;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cointrader.CointraderExchange;
import org.knowm.xchange.currency.CurrencyPair;

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
