package org.knowm.xchange.poloniex.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.poloniex.Poloniex;
import org.knowm.xchange.poloniex.PoloniexAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;

/**
 * @author Zach Holmes
 */

public class PoloniexBaseService extends BaseExchangeService implements BaseService {

  protected final String apiKey;
  protected final PoloniexAuthenticated poloniexAuthenticated;
  protected final ParamsDigest signatureCreator;

  protected final Poloniex poloniex;

  /**
   * Constructor
   *
   * @param exchange
   */
  public PoloniexBaseService(Exchange exchange) {

    super(exchange);
    // Fix for empty string array mapping exception
    ClientConfig rescuConfig = new ClientConfig();
    rescuConfig.setJacksonObjectMapperFactory(new DefaultJacksonObjectMapperFactory() {
      @Override
      public void configureObjectMapper(ObjectMapper objectMapper) {
        super.configureObjectMapper(objectMapper);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
      }
    });
    
    // allow HTTP read timeout to be altered per exchange
    int customHttpReadTimeout = exchange.getExchangeSpecification().getHttpReadTimeout();
    if (customHttpReadTimeout > 0) {
      rescuConfig.setHttpReadTimeout(customHttpReadTimeout);
    }
    
    this.poloniexAuthenticated = RestProxyFactory.createProxy(PoloniexAuthenticated.class, exchange.getExchangeSpecification().getSslUri(), rescuConfig);
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = PoloniexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.poloniex = RestProxyFactory.createProxy(Poloniex.class, exchange.getExchangeSpecification().getSslUri(), rescuConfig);
  }

}
