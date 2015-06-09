package com.xeiam.xchange.ripple;

import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.BaseExchange;
import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.ripple.service.polling.RippleAccountService;
import com.xeiam.xchange.ripple.service.polling.RippleMarketDataService;
import com.xeiam.xchange.ripple.service.polling.RippleTradeService;
import com.xeiam.xchange.utils.nonce.CurrentTimeNonceFactory;

public class RippleExchange extends BaseExchange implements Exchange {

  public static final String TRUST_API_RIPPLE_COM = "trust.api.ripple.com";

  public static final String REST_API_RIPPLE_LABS = "https://api.ripple.com/";

  public static final String REST_API_LOCALHOST_PLAIN_TEXT = "http://localhost:5990/";

  public static final String DATA_BASE_COUNTERPARTY = "baseCounterparty";
  public static final String DATA_COUNTER_COUNTERPARTY = "counterCounterparty";

  private static final String README = "https://github.com/timmolter/XChange/tree/develop/xchange-ripple";

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  public void applySpecification(final ExchangeSpecification specification) {
    super.applySpecification(specification);

    if (specification.getSecretKey() != null && specification.getSecretKey().length() > 0 && specification.getSslUri().equals(REST_API_RIPPLE_LABS)
        && Boolean.parseBoolean(specification.getParameter(TRUST_API_RIPPLE_COM).toString()) == false) {
      throw new IllegalStateException(String.format("server %s has not been trusted - see %s for details", REST_API_RIPPLE_LABS, README));
    }

    this.pollingMarketDataService = new RippleMarketDataService(this);
    this.pollingTradeService = new RippleTradeService(this);
    this.pollingAccountService = new RippleAccountService(this);
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    final ExchangeSpecification specification = new ExchangeSpecification(this.getClass().getCanonicalName());
    specification.setSslUri(REST_API_RIPPLE_LABS);
    specification.setExchangeName("Ripple");
    specification.setExchangeDescription("Ripple is a payment system, currency exchange and remittance network");

    // By default only use https://api.ripple.com/ for queries that do not require authentication, i.e. do not send secret key to Ripple labs servers. 
    specification.setExchangeSpecificParametersItem(TRUST_API_RIPPLE_COM, false);

    return specification;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }
}
