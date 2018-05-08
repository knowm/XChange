package org.known.xchange.acx;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2014NonceFactory;
import org.known.xchange.acx.service.account.AcxAccountService;
import org.known.xchange.acx.service.marketdata.AcxMarketDataService;
import org.known.xchange.acx.service.trade.AcxTradeService;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class AcxExchange extends BaseExchange implements Exchange {
  private final SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongIncrementalTime2014NonceFactory();

  @Override
  protected void initServices() {
    ExchangeSpecification spec = getExchangeSpecification();
    AcxApi api = RestProxyFactory.createProxy(AcxApi.class, spec.getSslUri());
    AcxMapper mapper = new AcxMapper();
    this.marketDataService = new AcxMarketDataService(api, mapper);
    if (spec.getApiKey() != null && spec.getSecretKey() != null) {
      AcxSignatureCreator signatureCreator = new AcxSignatureCreator(spec.getSecretKey());
      this.accountService = new AcxAccountService(api, mapper, signatureCreator, spec.getApiKey());
      this.tradeService = new AcxTradeService(api, mapper, signatureCreator, spec.getApiKey());
    }
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(this.getClass().getCanonicalName());
    spec.setSslUri("https://acx.io/api/v2/");
    spec.setHost("acx.io");
    spec.setExchangeName("ACX");
    spec.setExchangeDescription("The largest liquidity pool and orderbook of Bitcoin in Australia");
    return spec;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
