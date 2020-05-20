package org.knowm.xchange.coinmate.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinmate.CoinmateStatic;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import si.mazi.rescu.RestProxyFactory;

public class CoinmateMetadataServiceRaw extends CoinmateBaseService {
  private final CoinmateStatic coinmateStatic;

  public CoinmateMetadataServiceRaw(Exchange exchange) {
    super(exchange);

    this.coinmateStatic =
        RestProxyFactory.createProxy(
            CoinmateStatic.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());
  }

  public ExchangeMetaData getMetadata() throws IOException {

    ExchangeMetaData metaData = coinmateStatic.getMetadata();

    return metaData;
  }
}
