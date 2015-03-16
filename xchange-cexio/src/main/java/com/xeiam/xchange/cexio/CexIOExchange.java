package com.xeiam.xchange.cexio;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.cexio.service.polling.CexIOAccountService;
import com.xeiam.xchange.cexio.service.polling.CexIOMarketDataService;
import com.xeiam.xchange.cexio.service.polling.CexIOTradeService;
import com.xeiam.xchange.utils.nonce.AtomicLongIncrementalTime2014NonceFactory;

/**
 * @Author brox
 */
public class CexIOExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new AtomicLongIncrementalTime2014NonceFactory();

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    this.pollingMarketDataService = new CexIOMarketDataService(this);
    this.pollingAccountService = new CexIOAccountService(this);
    this.pollingTradeService = new CexIOTradeService(this);
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
