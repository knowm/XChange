package org.knowm.xchange.bitget.service;

import org.knowm.xchange.bitget.Bitget;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.config.BitgetJacksonObjectMapperFactory;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class BitgetBaseService extends BaseExchangeService<BitgetExchange> implements BaseService {

  protected final String apiKey;
  protected final Bitget bitget;

  public BitgetBaseService(BitgetExchange exchange) {
    super(exchange);
    bitget = ExchangeRestProxyBuilder
        .forInterface(Bitget.class, exchange.getExchangeSpecification())
        .clientConfigCustomizer(clientConfig -> clientConfig.setJacksonObjectMapperFactory(new BitgetJacksonObjectMapperFactory()))
        .build();
    apiKey = exchange.getExchangeSpecification().getApiKey();

  }
}
