package com.xeiam.xchange.bitkonan;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitkonan.service.polling.BitKonanMarketDataService;
import com.xeiam.xchange.utils.nonce.LongTimeNonceFactory;

/**
 * @author Piotr Ładyżyński
 */
public class BitKonanExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new LongTimeNonceFactory();

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://bitkonan.com");
    exchangeSpecification.setHost("bitkonan.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("BitKonan");
    exchangeSpecification.setExchangeDescription("BitKonan is a Bitcoin exchange.");

    return exchangeSpecification;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new BitKonanMarketDataService(exchangeSpecification, nonceFactory);
  }

}
