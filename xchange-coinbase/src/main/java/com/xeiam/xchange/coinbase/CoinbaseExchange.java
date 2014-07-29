package com.xeiam.xchange.coinbase;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseAccountService;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseMarketDataService;
import com.xeiam.xchange.coinbase.service.polling.CoinbaseTradeService;

/**
 * @author jamespedwards42
 */
public class CoinbaseExchange extends BaseExchange implements Exchange {

  public CoinbaseExchange() {

  }

  public static Exchange newInstance() {

    final Exchange exchange = new CoinbaseExchange();
    exchange.applySpecification(exchange.getDefaultExchangeSpecification());
    return exchange;
  }

  @Override
  public void applySpecification(final ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new CoinbaseMarketDataService(exchangeSpecification);
    this.pollingAccountService = new CoinbaseAccountService(exchangeSpecification);
    this.pollingTradeService = new CoinbaseTradeService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    final ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://coinbase.com");
    exchangeSpecification.setHost("coinbase.com");
    exchangeSpecification.setExchangeName("coinbase");
    exchangeSpecification
        .setExchangeDescription("Founded in June of 2012, Coinbase is a bitcoin wallet and platform where merchants and consumers can transact with the new digital currency bitcoin.");
    return exchangeSpecification;
  }
}
