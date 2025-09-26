package org.knowm.xchange.bitget.service;

import org.knowm.xchange.bitget.*;
import org.knowm.xchange.bitget.config.BitgetJacksonObjectMapperFactory;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class BitgetFuturesBaseService extends BaseExchangeService<BitgetFuturesExchange>
    implements BaseService {

  protected final BitgetFutures bitgetFutures;

  public BitgetFuturesBaseService(BitgetFuturesExchange exchange) {
    super(exchange);
    bitgetFutures =
        ExchangeRestProxyBuilder.forInterface(
                BitgetFutures.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(
                clientConfig ->
                    clientConfig.setJacksonObjectMapperFactory(
                        new BitgetJacksonObjectMapperFactory()))
            .build();
  }
}
