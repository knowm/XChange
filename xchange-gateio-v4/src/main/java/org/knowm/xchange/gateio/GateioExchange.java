package org.knowm.xchange.gateio;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
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

public class GateioExchange extends BaseExchange {

  private final SynchronizedValueFactory<Long> nonceFactory =
      new CurrentTimeIncrementalNonceFactory(TimeUnit.SECONDS);

  private static final String V4_REST_URL = "https://api.gateio.ws";
  private static final String SANDBOX_V4_REST_URL = "https://fx-api-testnet.gateio.ws";
  @Override
  protected void initServices() {
    marketDataService = new GateioMarketDataService(this);
    accountService = new GateioAccountService(this);
    tradeService = new GateioTradeService(this);
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification){
    if (isSandbox(exchangeSpecification)) {
      exchangeSpecification.setSslUri(SANDBOX_V4_REST_URL);
    }
    super.applySpecification(exchangeSpecification);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification specification = new ExchangeSpecification(this.getClass());
    specification.setSslUri(V4_REST_URL);
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
    Map<Instrument, InstrumentMetaData> instruments =
        ((GateioMarketDataService) marketDataService).getMetaDataByInstrument();

    exchangeMetaData = new ExchangeMetaData(instruments, null, null, null, null);
  }

  protected boolean isSandbox(ExchangeSpecification exchangeSpecification){
    return Boolean.TRUE.equals(
        exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX));
  }
}
