package org.knowm.xchange.paribu.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.paribu.Paribu;
import org.knowm.xchange.paribu.dto.marketdata.ParibuTicker;

/** Created by semihunaldi on 27/11/2017 */
public class ParibuMarketDataServiceRaw extends ParibuBaseService {

  private final Paribu paribu;

  public ParibuMarketDataServiceRaw(Exchange exchange) {

    super(exchange);
    this.paribu =
        ExchangeRestProxyBuilder.forInterface(Paribu.class, exchange.getExchangeSpecification())
            .build();
  }

  public ParibuTicker getParibuTicker() throws IOException {
    return paribu.getTicker();
  }
}
