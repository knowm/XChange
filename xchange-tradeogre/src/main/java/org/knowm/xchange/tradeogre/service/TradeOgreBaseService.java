package org.knowm.xchange.tradeogre.service;

import java.util.Base64;

import org.knowm.xchange.client.ClientConfigCustomizer;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.tradeogre.TradeOgreAuthenticated;
import org.knowm.xchange.tradeogre.TradeOgreExchange;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import si.mazi.rescu.ClientConfigUtil;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;

public class TradeOgreBaseService extends BaseExchangeService<TradeOgreExchange>
    implements BaseService {

  protected final TradeOgreAuthenticated tradeOgre;
  protected final String base64UserPwd;

  protected TradeOgreBaseService(TradeOgreExchange exchange) {

    super(exchange);

    String apiKey = exchange.getExchangeSpecification().getApiKey();
    String secretKey = exchange.getExchangeSpecification().getSecretKey();

    base64UserPwd = calculateBase64UserPwd(exchange);

    ClientConfigCustomizer clientConfigCustomizer =
        config -> {
          ClientConfigUtil.addBasicAuthCredentials(config, apiKey, secretKey);
          config.setJacksonObjectMapperFactory(
              new DefaultJacksonObjectMapperFactory() {
                @Override
                public void configureObjectMapper(ObjectMapper objectMapper) {
                  super.configureObjectMapper(objectMapper);
                  objectMapper.configure(
                      DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true);
                }
              });
        };
    tradeOgre =
        ExchangeRestProxyBuilder.forInterface(
                TradeOgreAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(clientConfigCustomizer)
            .build();
  }

  private String calculateBase64UserPwd(TradeOgreExchange exchange) {
    String userPwd =
        exchange.getExchangeSpecification().getApiKey()
            + ":"
            + exchange.getExchangeSpecification().getSecretKey();
    return "Basic " + new String(Base64.getEncoder().encode(userPwd.getBytes()));
  }
}
