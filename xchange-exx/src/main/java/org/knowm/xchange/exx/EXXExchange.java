package org.knowm.xchange.exx;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exx.service.EXXAccountService;
import org.knowm.xchange.exx.service.EXXMarketDataService;
import org.knowm.xchange.exx.service.EXXTradeService;
import org.knowm.xchange.utils.AuthUtils;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

/**
 * kevinobamatheus@gmail.com
 *
 * @author kevingates
 */
public class EXXExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.accountService = new EXXAccountService(this);
    this.marketDataService = new EXXMarketDataService(this);
    this.tradeService = new EXXTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());

    exchangeSpecification.setSslUri("https://api.exx.com");
    exchangeSpecification.setHost("api.exx.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("EXX");
    exchangeSpecification.setExchangeDescription("Exx is a world leading digital asset exchange.");
    AuthUtils.setApiAndSecretKey(exchangeSpecification, "exx");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
