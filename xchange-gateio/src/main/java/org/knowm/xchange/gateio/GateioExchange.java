package org.knowm.xchange.gateio;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.gateio.service.GateioAccountService;
import org.knowm.xchange.gateio.service.GateioMarketDataService;
import org.knowm.xchange.gateio.service.GateioMarketDataServiceRaw;
import org.knowm.xchange.gateio.service.GateioTradeService;
import si.mazi.rescu.SynchronizedValueFactory;

public class GateioExchange extends BaseExchange implements Exchange {

  @Override
  protected void initServices() {
    this.marketDataService = new GateioMarketDataService(this);
    this.accountService = new GateioAccountService(this);
    this.tradeService = new GateioTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://data.gate.io");
    exchangeSpecification.setHost("gate.io");
    exchangeSpecification.setExchangeName("Gateio");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {

    throw new ExchangeException("Gate.io does not require a nonce factory.");
  }

  @Override
  public void remoteInit() throws IOException {
    exchangeMetaData =
        GateioAdapters.adaptToExchangeMetaData((GateioMarketDataServiceRaw) marketDataService);
  }
}
