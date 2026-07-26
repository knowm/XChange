package org.knowm.xchange.cryptocom;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.cryptocom.dto.marketdata.CryptoComInstrument;
import org.knowm.xchange.cryptocom.service.CryptoComAccountService;
import org.knowm.xchange.cryptocom.service.CryptoComMarketDataService;
import org.knowm.xchange.cryptocom.service.CryptoComMarketDataServiceRaw;
import org.knowm.xchange.cryptocom.service.CryptoComTradeService;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.AuthUtils;
import si.mazi.rescu.Interceptor;

public class CryptoComExchange extends BaseExchange implements Exchange {

  private static final String PRODUCTION_URL = "https://api.crypto.com";
  private static final String SANDBOX_URL = "https://uat-api.3ona.co";

  protected CryptoCom cryptoCom;
  private final AtomicLong requestIdCounter = new AtomicLong(System.currentTimeMillis());
  private final ResilienceRegistries resilienceRegistries = new ResilienceRegistries();

  public CryptoCom getCryptoCom() {
    return cryptoCom;
  }

  /** Crypto.com requires a strictly increasing request id on every signed call. */
  public long nextRequestId() {
    return requestIdCounter.incrementAndGet();
  }

  @Override
  protected void initServices() {
    this.marketDataService = new CryptoComMarketDataService(this, getResilienceRegistries());
    this.tradeService = new CryptoComTradeService(this, getResilienceRegistries());
    this.accountService = new CryptoComAccountService(this, getResilienceRegistries());
  }

  @Override
  public ResilienceRegistries getResilienceRegistries() {
    return resilienceRegistries;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
    spec.setSslUri(PRODUCTION_URL);
    spec.setHost("api.crypto.com");
    spec.setPort(443);
    spec.setExchangeName("Crypto.com");
    spec.setExchangeDescription("Crypto.com Exchange.");
    spec.setExchangeSpecificParametersItem(USE_SANDBOX, false);
    AuthUtils.setApiAndSecretKey(spec, "cryptocom");
    return spec;
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {
    concludeHostParams(exchangeSpecification);

    Interceptor errorInterceptor = new CryptoComErrorInterceptor();
    this.cryptoCom =
        ExchangeRestProxyBuilder.forInterface(CryptoCom.class, exchangeSpecification)
            .customInterceptor(errorInterceptor)
            .build();

    super.applySpecification(exchangeSpecification);
  }

  protected void concludeHostParams(ExchangeSpecification exchangeSpecification) {
    if (Boolean.TRUE.equals(exchangeSpecification.getExchangeSpecificParametersItem(USE_SANDBOX))) {
      exchangeSpecification.setSslUri(SANDBOX_URL);
    } else {
      exchangeSpecification.setSslUri(PRODUCTION_URL);
    }
  }

  @Override
  public void remoteInit() {
    try {
      CryptoComMarketDataServiceRaw marketDataServiceRaw =
          (CryptoComMarketDataServiceRaw) marketDataService;
      List<CryptoComInstrument> instruments = marketDataServiceRaw.getCryptoComInstruments();
      exchangeMetaData = CryptoComAdapters.adaptExchangeMetaData(instruments);
    } catch (IOException e) {
      throw new ExchangeException("Failed to initialize Crypto.com exchange metadata", e);
    }
  }
}
