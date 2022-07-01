package org.knowm.xchange.cryptowatch.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.client.ClientConfigCustomizer;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.cryptowatch.Cryptowatch;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;

public class CryptowatchBaseService extends BaseExchangeService implements BaseService {

  protected Cryptowatch cryptowatch;

  public CryptowatchBaseService(Exchange exchange) {
    super(exchange);
    ClientConfigCustomizer clientConfigCustomizer = config -> config.setIgnoreHttpErrorCodes(true);
    cryptowatch =
        ExchangeRestProxyBuilder.forInterface(
                Cryptowatch.class, exchange.getExchangeSpecification())
            .clientConfigCustomizer(clientConfigCustomizer)
            .build();
  }
}
