package org.knowm.xchange.vaultoro;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.vaultoro.service.VaultoroAccountService;
import org.knowm.xchange.vaultoro.service.VaultoroMarketDataService;
import org.knowm.xchange.vaultoro.service.VaultoroTradeService;
import si.mazi.rescu.SynchronizedValueFactory;

public class VaultoroExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory =
      () -> getNonceFactory().createValue() / 250L;

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);
  }

  @Override
  protected void initServices() {
    this.marketDataService = new VaultoroMarketDataService(this);
    this.accountService = new VaultoroAccountService(this);
    this.tradeService = new VaultoroTradeService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.vaultoro.com");
    exchangeSpecification.setExchangeName("Vaultoro");

    return exchangeSpecification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {

    // TODO Implement this.
    // List<Blah>  currencies = ((VaultoroMarketDataServiceRaw) marketDataService).getBlah();
    // other endpoints?
    // hard-coded meta data from json file not available at an endpoint?
    // TODO take all the info gathered above and create a `ExchangeMetaData` object via a new method
    // in `*Adapters` class
    // exchangeMetaData = *Adapters.adaptToExchangeMetaData(blah, blah);

    super.remoteInit();
  }
}
