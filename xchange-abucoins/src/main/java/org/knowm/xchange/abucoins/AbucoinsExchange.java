package org.knowm.xchange.abucoins;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.abucoins.service.AbucoinsAccountService;
import org.knowm.xchange.abucoins.service.AbucoinsMarketDataService;
import org.knowm.xchange.abucoins.service.AbucoinsTradeService;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2014NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class AbucoinsExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2014NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new AbucoinsMarketDataService(this);
    this.accountService = new AbucoinsAccountService(this);
    this.tradeService = new AbucoinsTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.abucoins.com");
    exchangeSpecification.setHost("api.abucoins.com");
    exchangeSpecification.setPort(443);
    exchangeSpecification.setExchangeName("Abucoins");
    exchangeSpecification.setExchangeDescription("Abucoins is a crypto currency exchange based in Poland.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
