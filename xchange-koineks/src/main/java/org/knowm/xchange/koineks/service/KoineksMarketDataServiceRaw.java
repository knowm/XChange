package org.knowm.xchange.koineks.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.koineks.Koineks;
import org.knowm.xchange.koineks.dto.marketdata.KoineksTicker;

/** @author semihunaldi */
public class KoineksMarketDataServiceRaw extends KoineksBaseService {

  private final Koineks koineks;

  public KoineksMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.koineks =
        ExchangeRestProxyBuilder.forInterface(Koineks.class, exchange.getExchangeSpecification())
            .build();
  }

  public KoineksTicker getKoineksTicker() throws IOException {
    return koineks.getTicker();
  }
}
