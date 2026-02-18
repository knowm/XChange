package org.knowm.xchange.coinmate.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.coinmate.Coinmate;
import org.knowm.xchange.coinmate.dto.metadata.CoinmateTradingPairs;
import org.knowm.xchange.dto.meta.ExchangeMetaData;

public class CoinmateMetadataServiceRaw extends CoinmateBaseService {
  private final Coinmate coinmate;

  public CoinmateMetadataServiceRaw(Exchange exchange) {
    super(exchange);
    this.coinmate =
        ExchangeRestProxyBuilder.forInterface(Coinmate.class, exchange.getExchangeSpecification())
            .build();
  }

  public ExchangeMetaData getMetadata() throws IOException {
    ExchangeMetaData metaData = coinmate.getMetadata();

    return metaData;
  }

  public CoinmateTradingPairs getCoinmateTradingPairs() throws IOException {
    CoinmateTradingPairs tradingPairs = coinmate.getTradingPairs();

    throwExceptionIfError(tradingPairs);

    return tradingPairs;
  }
}
