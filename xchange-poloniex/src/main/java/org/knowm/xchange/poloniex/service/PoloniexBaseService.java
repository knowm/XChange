package org.knowm.xchange.poloniex.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ClientConfigCustomizer;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.poloniex.Poloniex;
import org.knowm.xchange.poloniex.PoloniexAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;

/** @author Zach Holmes */
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

    // TODO should this be fixed/added in rescu itself?
    // Fix for empty string array mapping exception
    ClientConfigCustomizer clientConfigCustomizer =
        rescuConfig ->
            rescuConfig.setJacksonObjectMapperFactory(
                new DefaultJacksonObjectMapperFactory() {
                  @Override
                  public void configureObjectMapper(ObjectMapper objectMapper) {
                    super.configureObjectMapper(objectMapper);
                    objectMapper.configure(
                        DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
                  }
                });

    this.poloniexAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                PoloniexAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(clientConfigCustomizer)
            .build();
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator =
        PoloniexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.poloniex =
        ExchangeRestProxyBuilder.forInterface(Poloniex.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(clientConfigCustomizer)
            .build();
  }
}
