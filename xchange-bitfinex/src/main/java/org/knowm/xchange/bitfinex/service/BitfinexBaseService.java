package org.knowm.xchange.bitfinex.service;

import org.knowm.xchange.bitfinex.BitfinexExchange;
import org.knowm.xchange.bitfinex.config.BitfinexJacksonObjectMapperFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexAuthenticated;
import org.knowm.xchange.bitfinex.v1.BitfinexDigest;
import org.knowm.xchange.bitfinex.v2.BitfinexHmacSignature;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.service.BaseResilientExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class BitfinexBaseService extends BaseResilientExchangeService<BitfinexExchange>
    implements BaseService {

  protected final String apiKey;
  protected final BitfinexAuthenticated bitfinex;
  protected final ParamsDigest signatureCreator;
  protected final ParamsDigest payloadCreator;

  protected final org.knowm.xchange.bitfinex.v2.BitfinexAuthenticated bitfinexV2;
  protected final BitfinexHmacSignature signatureV2;

  public BitfinexBaseService(BitfinexExchange exchange, ResilienceRegistries resilienceRegistries) {

    super(exchange, resilienceRegistries);

    bitfinex =
        ExchangeRestProxyBuilder.forInterface(
                BitfinexAuthenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BitfinexJacksonObjectMapperFactory()))
            .build();
    apiKey = exchange.getExchangeSpecification().getApiKey();
    signatureCreator =
        BitfinexDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    payloadCreator = new BitfinexPayloadDigest();

    bitfinexV2 =
        ExchangeRestProxyBuilder.forInterface(
                org.knowm.xchange.bitfinex.v2.BitfinexAuthenticated.class,
                exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BitfinexJacksonObjectMapperFactory()))
            .build();
    signatureV2 =
        BitfinexHmacSignature.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
