package org.knowm.xchange.coinmate.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinmate.CoinmateStatic;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

public class CoinmateMetadataServiceRaw extends CoinmateBaseService {
  private final CoinmateStatic coinmateStatic;

  public CoinmateMetadataServiceRaw(Exchange exchange) {
    super(exchange);

    this.coinmateStatic =
        ExchangeRestProxyBuilder.forInterface(
                CoinmateStatic.class, exchange.getExchangeSpecification())
            .build();
  }

  public ExchangeMetaData getMetadata() throws IOException {

    ExchangeMetaData metaData = coinmateStatic.getMetadata();

    return metaData;
  }
}
