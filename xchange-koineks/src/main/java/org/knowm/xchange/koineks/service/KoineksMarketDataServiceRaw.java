package org.knowm.xchange.koineks.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.koineks.Koineks;
import org.knowm.xchange.koineks.dto.marketdata.KoineksTicker;
import si.mazi.rescu.RestProxyFactory;

/** @author semihunaldi */
public class KoineksMarketDataServiceRaw extends KoineksBaseService {

  private final Koineks koineks;

  public KoineksMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.koineks =
        RestProxyFactory.createProxy(
            Koineks.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
  }

  public KoineksTicker getKoineksTicker() throws IOException {
    return koineks.getTicker();
  }
}
