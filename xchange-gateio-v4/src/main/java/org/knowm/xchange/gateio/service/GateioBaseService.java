package org.knowm.xchange.gateio.service;

import jakarta.ws.rs.HeaderParam;
import lombok.SneakyThrows;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.gateio.Gateio;
import org.knowm.xchange.gateio.GateioExchange;
import org.knowm.xchange.gateio.GateioV4Authenticated;
import org.knowm.xchange.gateio.config.Config;
import org.knowm.xchange.gateio.config.GateioJacksonObjectMapperFactory;
import org.knowm.xchange.service.BaseResilientExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;

public class GateioBaseService extends BaseResilientExchangeService<GateioExchange> implements BaseService {

  protected final String apiKey;
  protected final Gateio gateio;
  protected final GateioV4Authenticated gateioV4Authenticated;
  protected final ParamsDigest gateioV4ParamsDigest;

  @SneakyThrows
  public GateioBaseService(GateioExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
    gateio =
        ExchangeRestProxyBuilder.forInterface(Gateio.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig -> {
                  clientConfig.setJacksonObjectMapperFactory(
                      new GateioJacksonObjectMapperFactory());
                  clientConfig.addDefaultParam(HeaderParam.class, "X-Gate-Size-Decimal", "1");
                }
            )
            .restProxyFactory(
                Config.getInstance()
                    .getRestProxyFactoryClass()
                    .getDeclaredConstructor()
                    .newInstance())
            .build();
    apiKey = exchange.getExchangeSpecification().getApiKey();

    gateioV4Authenticated =
        ExchangeRestProxyBuilder.forInterface(
                GateioV4Authenticated.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig -> {
                    clientConfig.setJacksonObjectMapperFactory(
                        new GateioJacksonObjectMapperFactory());
                  clientConfig.addDefaultParam(HeaderParam.class, "X-Gate-Size-Decimal", "1");
                })
            .restProxyFactory(
                Config.getInstance()
                    .getRestProxyFactoryClass()
                    .getDeclaredConstructor()
                    .newInstance())
            .build();

    gateioV4ParamsDigest =
        GateioV4Digest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }
}
