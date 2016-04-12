package org.knowm.xchange.vaultoro;

import java.math.BigDecimal;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.nonce.CurrentTime250NonceFactory;
import org.knowm.xchange.vaultoro.service.polling.VaultoroAccountService;
import org.knowm.xchange.vaultoro.service.polling.VaultoroMarketDataService;
import org.knowm.xchange.vaultoro.service.polling.VaultoroTradeService;

import si.mazi.rescu.SynchronizedValueFactory;

public class VaultoroExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTime250NonceFactory();
  public static BigDecimal latest;
  public static long latestTimestamp;

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
  }

  @Override
  protected void initServices() {
    this.pollingMarketDataService = new VaultoroMarketDataService(this);
    this.pollingAccountService = new VaultoroAccountService(this);
    this.pollingTradeService = new VaultoroTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.vaultoro.com");
    exchangeSpecification.setExchangeName("Vaultoro");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
