package org.knowm.xchange.cexio;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cexio.service.CexIOAccountService;
import org.knowm.xchange.cexio.service.CexIOMarketDataService;
import org.knowm.xchange.cexio.service.CexIOTradeService;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2014NonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class CexIOExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2014NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new CexIOMarketDataService(this);
    this.accountService = new CexIOAccountService(this);
    this.tradeService = new CexIOTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://cex.io");
    exchangeSpecification.setHost("cex.io");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cex IO");
    exchangeSpecification.setExchangeDescription("Cex.IO is a virtual commodities exchange registered in United Kingdom.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
