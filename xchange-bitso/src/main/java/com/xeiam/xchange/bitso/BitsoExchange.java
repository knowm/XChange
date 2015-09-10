package com.xeiam.xchange.bitso;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitso.service.polling.BitsoAccountService;
import com.xeiam.xchange.bitso.service.polling.BitsoMarketDataService;
import com.xeiam.xchange.bitso.service.polling.BitsoTradeService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author Matija Mazi, Piotr Ładyżyński
 */
public class BitsoExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new BitsoMarketDataService(this);
    this.pollingAccountService = new BitsoAccountService(this);
    this.pollingTradeService = new BitsoTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.bitso.com");
    exchangeSpecification.setHost("bitso.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bitso");
    exchangeSpecification.setExchangeDescription("A new hub for trade and remittance of Bitcoin in Mexico.");
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
