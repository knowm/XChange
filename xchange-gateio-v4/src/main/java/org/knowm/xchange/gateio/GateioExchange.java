package org.knowm.xchange.gateio;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.gateio.service.GateioAccountService;
import org.knowm.xchange.gateio.service.GateioMarketDataService;
import org.knowm.xchange.gateio.service.GateioTradeService;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GateioExchange extends BaseExchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeIncrementalNonceFactory(TimeUnit.SECONDS);


  @Override
  protected void initServices() {
    marketDataService = new GateioMarketDataService(this);
    accountService = new GateioAccountService(this);
    tradeService = new GateioTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification specification = new ExchangeSpecification(this.getClass());
    specification.setSslUri("https://api.gateio.ws");
    specification.setHost("gate.io");
    specification.setExchangeName("Gateio");

    return specification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }


  @Override
  public void remoteInit() throws IOException {
    Map<Instrument, InstrumentMetaData> instruments = ((GateioMarketDataService) marketDataService).getMetaDataByInstrument();

    exchangeMetaData = new ExchangeMetaData(instruments, null, null, null, null);
  }
}
