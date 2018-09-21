package org.knowm.xchange.getbtc;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.getbtc.service.GetbtcAccountService;
import org.knowm.xchange.getbtc.service.GetbtcMarketDataService;
import org.knowm.xchange.getbtc.service.GetbtcTradeService;
import org.knowm.xchange.utils.AuthUtils;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;
/**
 * kevinobamatheus@gmail.com
 * @author kevingates
 *
 */
public class GetbtcExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.accountService = new GetbtcAccountService(this);
    this.marketDataService = new GetbtcMarketDataService(this);
    this.tradeService = new GetbtcTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
        
    exchangeSpecification.setSslUri("https://getbtc.org");
    exchangeSpecification.setHost("getbtc.org");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Getbtc");
    exchangeSpecification.setExchangeDescription("GetBTC is a universal site for deliverable trading.");
    AuthUtils.setApiAndSecretKey(exchangeSpecification, "getbtc");
        
    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    return nonceFactory;
  }
}
