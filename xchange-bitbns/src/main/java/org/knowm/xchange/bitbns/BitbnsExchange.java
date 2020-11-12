package org.knowm.xchange.bitbns;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitbns.service.BitbnsMarketDataService;
import org.knowm.xchange.bitbns.service.BitbnsTradeService;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitbnsExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://api.bitbns.com/api/trade");
    exchangeSpecification.setHost("api.bitbns.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("bitbns");
    exchangeSpecification.setExchangeDescription("Bitbns Exchange custom Exchange");
    return exchangeSpecification;
  }

  @Override
  protected void initServices() {
    this.marketDataService = new BitbnsMarketDataService(this);
    this.tradeService = new BitbnsTradeService(this);
  }

  //	  @Override
  //	  public void applySpecification(ExchangeSpecification exchangeSpecification) {
  //
  //	    super.applySpecification(exchangeSpecification);
  //
  //	    concludeHostParams(exchangeSpecification);
  //	  }
  //
  //	  /** Adjust host parameters depending on exchange specific parameters */
  //	  private static void concludeHostParams(ExchangeSpecification exchangeSpecification) {
  //
  //	    if (exchangeSpecification.getExchangeSpecificParameters() != null) {
  //	      if (exchangeSpecification.getExchangeSpecificParametersItem("Use_Sandbox").equals(true))
  // {
  //	        exchangeSpecification.setSslUri("https://api.bitbns.com/api/trade");
  //	        exchangeSpecification.setHost("api.bitbns.com");
  //	      }
  //	    }
  //	  }

}
