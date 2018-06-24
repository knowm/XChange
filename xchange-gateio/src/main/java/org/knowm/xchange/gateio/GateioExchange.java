package org.knowm.xchange.gateio;

import java.io.IOException;
import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.gateio.dto.marketdata.GateioMarketInfoWrapper.GateioMarketInfo;
import org.knowm.xchange.gateio.service.GateioAccountService;
import org.knowm.xchange.gateio.service.GateioMarketDataService;
import org.knowm.xchange.gateio.service.GateioMarketDataServiceRaw;
import org.knowm.xchange.gateio.service.GateioTradeService;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class GateioExchange extends BaseExchange implements Exchange {

  private SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongIncrementalTime2013NonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new GateioMarketDataService(this);
    this.accountService = new GateioAccountService(this);
    this.tradeService = new GateioTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://data.gate.io");
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

    Map<CurrencyPair, GateioMarketInfo> currencyPair2BTERMarketInfoMap =
        ((GateioMarketDataServiceRaw) marketDataService).getBTERMarketInfo();
    exchangeMetaData = GateioAdapters.adaptToExchangeMetaData(currencyPair2BTERMarketInfoMap);
  }
}
