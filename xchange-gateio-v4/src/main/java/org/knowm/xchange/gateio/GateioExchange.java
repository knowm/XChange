package org.knowm.xchange.gateio;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gateio.service.GateioAccountService;
import org.knowm.xchange.gateio.service.GateioMarketDataService;
import org.knowm.xchange.gateio.service.GateioTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class GateioExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory =
      new CurrentTimeIncrementalNonceFactory(TimeUnit.SECONDS);


  @Override
  protected void initServices() {
    this.marketDataService = new GateioMarketDataService(this);
    this.accountService = new GateioAccountService(this);
    this.tradeService = new GateioTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.gateio.ws");
    exchangeSpecification.setHost("gate.io");
    exchangeSpecification.setExchangeName("Gateio");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {
//    exchangeMetaData =
//        GateioAdapters.adaptToExchangeMetaData((GateioMarketDataServiceRaw) marketDataService);
  }
}
