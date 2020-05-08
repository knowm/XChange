package org.knowm.xchange.livecoin;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.livecoin.dto.LivecoinException;
import org.knowm.xchange.livecoin.dto.marketdata.LivecoinRestriction;
import org.knowm.xchange.livecoin.service.LivecoinAccountService;
import org.knowm.xchange.livecoin.service.LivecoinMarketDataService;
import org.knowm.xchange.livecoin.service.LivecoinMarketDataServiceRaw;
import org.knowm.xchange.livecoin.service.LivecoinTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class LivecoinExchange extends BaseExchange implements Exchange {

  private static ResilienceRegistries RESILIENCE_REGISTRIES;

  private SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();
  private Livecoin livecoin;

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ResilienceRegistries getResilienceRegistries() {
    if (RESILIENCE_REGISTRIES == null) {
      RESILIENCE_REGISTRIES = LivecoinResilience.createRegistries();
    }
    return RESILIENCE_REGISTRIES;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.livecoin.net");
    exchangeSpecification.setHost("api.livecoin.net");
    exchangeSpecification.setExchangeName("Livecoin");
    exchangeSpecification.setExchangeDescription(
        "Livecoin - A convenient way to buy and sell Bitcoin");
    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.livecoin =
        ExchangeRestProxyBuilder.forInterface(Livecoin.class, getExchangeSpecification()).build();
    this.marketDataService =
        new LivecoinMarketDataService(this, livecoin, getResilienceRegistries());
    this.accountService = new LivecoinAccountService(this, livecoin, getResilienceRegistries());
    this.tradeService = new LivecoinTradeService(this, livecoin, getResilienceRegistries());
  }

  @Override
  public void remoteInit() throws IOException {
    try {
      List<LivecoinRestriction> products =
          ((LivecoinMarketDataServiceRaw) marketDataService).getRestrictions();
      exchangeMetaData = LivecoinAdapters.adaptToExchangeMetaData(exchangeMetaData, products);
    } catch (LivecoinException e) {
      throw LivecoinErrorAdapter.adapt(e);
    }
  }
}
