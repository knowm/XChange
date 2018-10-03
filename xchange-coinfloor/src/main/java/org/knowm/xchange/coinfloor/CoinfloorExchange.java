package org.knowm.xchange.coinfloor;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinfloor.service.CoinfloorAccountService;
import org.knowm.xchange.coinfloor.service.CoinfloorMarketDataService;
import org.knowm.xchange.coinfloor.service.CoinfloorTradeService;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.SynchronizedValueFactory;

public class CoinfloorExchange extends BaseExchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification specification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    specification.setShouldLoadRemoteMetaData(false);
    specification.setSslUri("https://webapi.coinfloor.co.uk:8090/");
    specification.setExchangeName("Coinfloor");
    specification.setExchangeDescription("Coinfloor exchange");
    return specification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new CoinfloorMarketDataService(this);
    this.accountService = new CoinfloorAccountService(this);
    this.tradeService = new CoinfloorTradeService(this);
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    // there are no suitable API calls to use for remote init
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return null;
  }
}
