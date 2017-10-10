package org.knowm.xchange.binance;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.service.BinanceMarketDataService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class BinanceExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.binance.com");
    exchangeSpecification.setHost("www.binance.com");
    exchangeSpecification.setExchangeName("Binance");
    exchangeSpecification.setExchangeDescription("Binance - Start the 2.0 Age of Cryptocurrency Exchange");
    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new BinanceMarketDataService(this);
    this.accountService = null;
    this.tradeService = null;
  }

}
