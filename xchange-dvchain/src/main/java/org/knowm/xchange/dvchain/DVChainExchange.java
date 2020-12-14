package org.knowm.xchange.dvchain;

import java.util.concurrent.TimeUnit;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dvchain.service.DVChainMarketDataService;
import org.knowm.xchange.dvchain.service.DVChainTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class DVChainExchange extends BaseExchange {
  private final SynchronizedValueFactory<Long> nonceFactory =
      new CurrentTimeIncrementalNonceFactory(TimeUnit.SECONDS);

  /** Adjust host parameters depending on exchange specific parameters */
  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {

    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
      if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Sandbox").equals(true)) {

        exchangeSpecification.setSslUri("https://sandbox.trade.dvchain.co");
        exchangeSpecification.setHost("sandbox.trade.dvchain.co");
      }
    }
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://trade.dvchain.co");
    exchangeSpecification.setHost("trade.dvchain.co");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("DVChain");
    exchangeSpecification.setExchangeDescription(
        "DVChain is an OTC provider for a variety of cryptocurrencies.");

    exchangeSpecification.setExchangeSpecificParametersItem("Use_Sandbox", false);

    return exchangeSpecification;
  }

  @Override
  protected void initServices() {

    concludeHostParams(exchangeSpecification);
    DVChainMarketDataService md = new DVChainMarketDataService(this);

    this.marketDataService = md;
    this.tradeService = new DVChainTradeService(md, this);
  }

  @Override
  public void applySpecification(ExchangeSpecification exchangeSpecification) {

    super.applySpecification(exchangeSpecification);

    concludeHostParams(exchangeSpecification);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
