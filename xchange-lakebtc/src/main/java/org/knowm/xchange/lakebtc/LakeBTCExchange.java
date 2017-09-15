package org.knowm.xchange.lakebtc;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.lakebtc.service.LakeBTCAccountService;
import org.knowm.xchange.lakebtc.service.LakeBTCMarketDataService;
import org.knowm.xchange.lakebtc.service.LakeBTCTradeService;
import org.knowm.xchange.utils.nonce.CurrentNanosecondTimeIncrementalNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

/**
 * @author kpysniak
 */
public class LakeBTCExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentNanosecondTimeIncrementalNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new LakeBTCMarketDataService(this);
    this.accountService = new LakeBTCAccountService(this);
    this.tradeService = new LakeBTCTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.lakebtc.com/");
    exchangeSpecification.setHost("https://lakebtc.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("LakeBTC");
    exchangeSpecification.setExchangeDescription("LakeBTC is a Bitcoin exchange for USD and CNY.");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
