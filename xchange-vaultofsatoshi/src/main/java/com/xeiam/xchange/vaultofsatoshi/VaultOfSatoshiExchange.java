package com.xeiam.xchange.vaultofsatoshi;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.vaultofsatoshi.service.polling.VaultOfSatoshiMarketDataService;

/**
 * <p>
 * Exchange implementation to provide the following to applications:
 * </p>
 * <ul>
 * <li>A wrapper for the VaultOfSatoshi exchange API</li>
 * </ul>
 */
public class VaultOfSatoshiExchange extends BaseExchange implements Exchange {

  /**
   * Default constructor for ExchangeFactory
   */
  public VaultOfSatoshiExchange() {

  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
    this.pollingMarketDataService = new VaultOfSatoshiMarketDataService(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.vaultofsatoshi.com");
    exchangeSpecification.setHost("api.vaultofsatoshi.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("VaultOfSatoshi");
    exchangeSpecification.setExchangeDescription("Vault of Satoshi is a reputable 100% Canadian owned and operated virtual currency trading platform serving a Global market.");

    return exchangeSpecification;
  }
}
