package org.knowm.xchange.yobit.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import org.knowm.xchange.yobit.YoBit;
import org.knowm.xchange.yobit.YoBitDigest;

public class YoBitBaseService<T extends YoBit> extends BaseExchangeService implements BaseService {
  protected final T service;
  protected final YoBitDigest signatureCreator;

  protected YoBitBaseService(Class<T> type, Exchange exchange) {
    super(exchange);

    this.signatureCreator =
        YoBitDigest.createInstance(
            exchange.getExchangeSpecification().getSecretKey(),
            exchange.getExchangeSpecification().getApiKey());
    this.service =
        ExchangeRestProxyBuilder.forInterface(type, exchange.getExchangeSpecification()).build();
  }
}
