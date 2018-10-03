package org.knowm.xchange.koinim.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.koinim.Koinim;
import org.knowm.xchange.koinim.dto.marketdata.KoinimTicker;
import si.mazi.rescu.RestProxyFactory;

/** @author ahmet.oz */
public class KoinimMarketDataServiceRaw extends KoinimBaseService {

  private final Koinim koinim;

  public KoinimMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.koinim =
        RestProxyFactory.createProxy(
            Koinim.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public KoinimTicker getKoinimTicker() throws IOException {
    return koinim.getTicker();
  }
}
