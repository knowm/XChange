package org.knowm.xchange.koinim.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.koinim.Koinim;
import org.knowm.xchange.koinim.dto.marketdata.KoinimTicker;

/** @author ahmet.oz */
public class KoinimMarketDataServiceRaw extends KoinimBaseService {

  private final Koinim koinim;

  public KoinimMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.koinim =
        ExchangeRestProxyBuilder.forInterface(Koinim.class, exchange.getExchangeSpecification())
            .build();
  }

  public KoinimTicker getKoinimTicker() throws IOException {
    return koinim.getTicker();
  }
}
