package org.knowm.xchange.itbit.v1.service.polling;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.itbit.v1.ItBit;
import org.knowm.xchange.itbit.v1.ItBitAuthenticated;
import org.knowm.xchange.itbit.v1.service.ItBitHmacPostBodyDigest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.polling.BasePollingService;

import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class ItBitBasePollingService extends BaseExchangeService implements BasePollingService {

  protected final String apiKey;
  protected final ItBitAuthenticated itBitAuthenticated;
  protected final ParamsDigest signatureCreator;

  protected final ItBit itBitPublic;

  /**
   * Constructor
   *
   * @param exchange
   */
  public ItBitBasePollingService(Exchange exchange) {

    super(exchange);

    this.itBitAuthenticated = RestProxyFactory.createProxy(ItBitAuthenticated.class,
        (String) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("authHost"));
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = ItBitHmacPostBodyDigest.createInstance(apiKey, exchange.getExchangeSpecification().getSecretKey());

    this.itBitPublic = RestProxyFactory.createProxy(ItBit.class, exchange.getExchangeSpecification().getSslUri());

  }
}
