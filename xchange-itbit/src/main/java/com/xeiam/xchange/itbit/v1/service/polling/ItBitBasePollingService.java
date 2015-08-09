package com.xeiam.xchange.itbit.v1.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.itbit.v1.ItBit;
import com.xeiam.xchange.itbit.v1.ItBitAuthenticated;
import com.xeiam.xchange.itbit.v1.service.ItBitHmacPostBodyDigest;
import com.xeiam.xchange.service.BaseExchangeService;
import com.xeiam.xchange.service.polling.BasePollingService;

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
